package com.ashokjeph.dualdb.invoices.factory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.ashokjeph.dualdb.entity.secontary.FactParam;
import com.ashokjeph.dualdb.entity.secontary.InvoiceFile;
import com.ashokjeph.dualdb.entity.secontary.Invoices;
import com.ashokjeph.dualdb.entity.secontary.VendorRpaInvoicesEntity;
import com.ashokjeph.dualdb.exception.ApiError;
import com.ashokjeph.dualdb.exception.VendorInvoiceNotFoundException;
import com.ashokjeph.dualdb.invoices.model.AddInvoicesRequestModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class AddInvoiceFromVendorRpaFactory {

    private final AddInvoiceFromVendorRpaFactoryService factoryService;

    public AddInvoiceFromVendorRpaFactory(AddInvoiceFromVendorRpaFactoryService factoryService){
        this.factoryService = factoryService;
    }

    @Transactional
    public ResponseEntity<?> addInvoices(AddInvoicesRequestModel requestModel) {
        ResponseEntity<?> responseEntity = null;

        // Retrieve the VendorRpaInvoicesEntity by ID
        VendorRpaInvoicesEntity rpaInvoicesEntity = factoryService.getVendorRpaInvoiceRepository()
                .findById(requestModel.getVendorRpaInvoices_Id())
                .orElseThrow(() -> new VendorInvoiceNotFoundException("Vendor invoice not found", HttpStatus.NOT_FOUND));

        FactParam factParam = factoryService.getFactParamRepository().findTopByVendorBuyerPlatformsId(rpaInvoicesEntity.getVendorBuyerPlatformId());
        if(factParam == null){
            responseEntity = ApiError.getApiResponse(false, "Fact-param is not available", null, HttpStatus.OK);
        }

        // Check if the invoice already exists
        Optional<Invoices> existingInvoiceOpt = factoryService.getInvoicesRepository()
                .findByMerchantIdAndBuyerPlatformIdAndInvoiceNumber(
                        rpaInvoicesEntity.getVendorBuyerPlatformId().getVendorId(),
                        rpaInvoicesEntity.getVendorBuyerPlatformId().getBuyerPlatformId(),
                        rpaInvoicesEntity.getInvoiceNumber()
                );

        Invoices invoice = existingInvoiceOpt.orElseGet(Invoices::new);

        boolean skipUpdate = "Yes".equalsIgnoreCase(invoice.getFactored());

        if (!skipUpdate) {
            // Save invoice file if not already present
            InvoiceFile invoiceFile = factoryService.getInvoicesFileStoreRepository()
                    .findByInvoiceFileName(rpaInvoicesEntity.getInvoiceNumber() + ".pdf")
                    .orElseGet(InvoiceFile::new);

            invoiceFile.setInvoiceFileName(rpaInvoicesEntity.getInvoiceNumber() + ".pdf");
            invoiceFile.setBlobFilePath(rpaInvoicesEntity.getInvoiceFilePath());
            invoiceFile.setInvoiceFileType("application/pdf");
            factoryService.getInvoicesFileStoreRepository().save(invoiceFile);

            invoice.setInvoicePdf(invoiceFile.getId());

            Integer invoicesDueDateGap = Optional.ofNullable(factParam)
                    .map(FactParam::getUnapprovedInvoiceDueDateGap)
                    .orElse(5);

            LocalDateTime dueDateLdt = rpaInvoicesEntity.getInvoiceDueDate();
            Date dueDate = Date.from(dueDateLdt.atZone(ZoneId.systemDefault()).toInstant());

            if ("Submitted".equalsIgnoreCase(rpaInvoicesEntity.getInvoiceStatus())) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(dueDate);
                cal.add(Calendar.DATE, invoicesDueDateGap);
                invoice.setDueDate(dueDate);
            } else {
                invoice.setDueDate(dueDate);
            }

            invoice.setMerchantId(rpaInvoicesEntity.getVendorBuyerPlatformId().getVendorId());
            invoice.setBuyerPlatformId(rpaInvoicesEntity.getVendorBuyerPlatformId().getBuyerPlatformId());
            invoice.setInvoiceNumber(rpaInvoicesEntity.getInvoiceNumber());

            LocalDateTime localDateTime = rpaInvoicesEntity.getInvoiceCreationDate();
            Date invoiceDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            invoice.setInvoiceDate(invoiceDate);

            invoice.setCurrency(rpaInvoicesEntity.getInvoiceCurrency());
            invoice.setInvoiceAmount(rpaInvoicesEntity.getInvoiceAmount());
            invoice.setInvoiceStatus(
                    "Queued for payment".equalsIgnoreCase(rpaInvoicesEntity.getInvoiceStatus()) ?
                            "Approved for payment" :
                            rpaInvoicesEntity.getInvoiceStatus()
            );
            invoice.setMarketPlace(rpaInvoicesEntity.getCountry());
            invoice.setPayeeCode(rpaInvoicesEntity.getPayeeCode());
            invoice.setPoNumber(rpaInvoicesEntity.getPoNumber());
            invoice.setSource("Vendor Central");
            invoice.setUserId(rpaInvoicesEntity.getVendorBuyerPlatformId().getVendorId());
            invoice.setActualPaidAmount(
                    Optional.ofNullable(rpaInvoicesEntity.getInvoiceAmount()).orElse(BigDecimal.ZERO));
            invoice.setInvoiceType("Normal");
            invoice.setAvailableAmount(invoice.getInvoiceAmount());
            invoice.setFactored(Optional.ofNullable(invoice.getFactored()).orElse("No"));

            factoryService.getInvoicesRepository().save(invoice);
            rpaInvoicesEntity.setInvoiceAdded(true);
            factoryService.getVendorRpaInvoiceRepository().save(rpaInvoicesEntity);
        }

        return Objects.requireNonNullElseGet(
                responseEntity,
                () -> ApiError.getApiResponse(
                        true,
                        skipUpdate ? "Invoice already factored. Skipped insert/update." : "Vendor invoice has been successfully added.",
                        invoice,
                        HttpStatus.OK
                )
        );
    }
}
