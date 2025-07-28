package com.ashokjeph.dualdb.invoices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ashokjeph.dualdb.entity.secontary.Invoices;
import com.ashokjeph.dualdb.entity.secontary.Merchant;
import com.ashokjeph.dualdb.exception.ApiError;
import com.ashokjeph.dualdb.exception.NotFoundException;
import com.ashokjeph.dualdb.invoices.factory.AddInvoiceFromVendorRpaFactory;
import com.ashokjeph.dualdb.invoices.factory.AddInvoiceFromVendorRpaFactoryService;
import com.ashokjeph.dualdb.invoices.factory.ManualInvoiceFactory;
import com.ashokjeph.dualdb.invoices.factory.ManualInvoiceFactoryService;
import com.ashokjeph.dualdb.invoices.model.AddInvoicesRequestModel;
import com.ashokjeph.dualdb.invoices.model.ManualInvoiceRequestModel;
import com.ashokjeph.dualdb.invoices_webhook.factory.GetInvoicesListFactory;
import com.ashokjeph.dualdb.invoices_webhook.factory.GetInvoicesListFactoryService;
import com.ashokjeph.dualdb.invoices_webhook.model.GetInvoiceListRequestModel;
import com.ashokjeph.dualdb.repositories.secondary_repository.*;

import java.util.Calendar;
import java.util.List;

@Service
public class InvoicesService implements AddInvoiceFromVendorRpaFactoryService, GetInvoicesListFactoryService, ManualInvoiceFactoryService {

    @Autowired
    private InvoicesRepository invoicesRepository;

    @Autowired
    private VendorRpaInvoiceRepository vendorRpaInvoiceRepository;

    @Autowired
    private InvoicesFileStoreRepository invoicesFileStoreRepository;

    @Autowired
    private FactParamRepository factParamRepository;

    @Autowired
    private MerchantRepository getMerchantRepository;

    @Override
    public InvoicesRepository getInvoicesRepository() {
        return invoicesRepository;
    }

    @Override
    public VendorRpaInvoiceRepository getVendorRpaInvoiceRepository() {
        return vendorRpaInvoiceRepository;
    }

    @Override
    public InvoicesFileStoreRepository getInvoicesFileStoreRepository() {
        return invoicesFileStoreRepository;
    }

    @Override
    public FactParamRepository getFactParamRepository() {
        return factParamRepository;
    }

    public ResponseEntity<?> addInvoices(AddInvoicesRequestModel requestModel){

        AddInvoiceFromVendorRpaFactory saveInvoiceFactory = new AddInvoiceFromVendorRpaFactory(this);
//        Invoices invoices = saveInvoiceFactory.addInvoices(requestModel);
//        return ApiError.getApiResponse(true, "Vendor invoice has been successfully added.", invoices, HttpStatus.OK);
        return saveInvoiceFactory.addInvoices(requestModel);
    }

    public ResponseEntity<?> getInvoicesList(GetInvoiceListRequestModel requestModel) {

        GetInvoicesListFactory invoicesListFactory = new GetInvoicesListFactory(this);
        return invoicesListFactory.getInvoicesList(requestModel);
    }

    public ResponseEntity<?> addManualInvoices(ManualInvoiceRequestModel requestModel) {
        try {
            Merchant merchant = getMerchantRepository.findById(requestModel.getMerchantId())
                    .orElseThrow(() -> new NotFoundException("Merchant not found: " + requestModel.getMerchantId()));

            String userId = merchant.getUser().getId();

            ManualInvoiceFactory saveInvoiceFactory = new ManualInvoiceFactory(this);

            Invoices invoice = saveInvoiceFactory.addManualInvoices(requestModel, userId);

            Calendar cal = Calendar.getInstance();
            cal.setTime(invoice.getInvoiceCreationDate());

            List<String> existingInvoiceNumbers =
                    invoicesRepository.getUniqueInvoiceNumbers(cal.get(Calendar.YEAR),
                            invoice.getMerchantId(), invoice.getBuyerPlatformId());

            boolean isDuplicate = existingInvoiceNumbers.stream()
                    .anyMatch(inv -> inv.equalsIgnoreCase(invoice.getInvoiceNumber()));

            if (isDuplicate) {
                return ApiError.getApiResponse(false, "Duplicate Invoice Number", invoice, HttpStatus.BAD_REQUEST);
            }

            invoicesRepository.save(invoice);
            return ApiError.getApiResponse(true, "Invoices Saved Successfully", invoice, HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return ApiError.getApiResponse(false, "Data integrity violation", e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ApiError.getApiExceptionResponseEntity(e, "/invoice/addManualInvoices");

        }
    }
}
