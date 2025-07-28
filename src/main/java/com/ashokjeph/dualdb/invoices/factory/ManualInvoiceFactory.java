package com.ashokjeph.dualdb.invoices.factory;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.ashokjeph.dualdb.entity.secontary.Invoices;
import com.ashokjeph.dualdb.invoices.model.ManualInvoiceRequestModel;

@Slf4j
public class ManualInvoiceFactory {

    private ManualInvoiceFactoryService factoryService;
    public ManualInvoiceFactory(ManualInvoiceFactoryService factoryService){
        this.factoryService = factoryService;
    }

    public Invoices addManualInvoices(ManualInvoiceRequestModel request, String userId) {
        Invoices invoice = new Invoices();

        invoice.setMerchantId(userId); // override from find invoice DB
        invoice.setBuyerPlatformId(Long.parseLong(request.getBuyerPlatformId()));
        invoice.setInvoiceNumber(request.getInvoiceNumber());
        invoice.setInvoiceStatus(request.getInvoiceStatus());
        invoice.setSource(request.getSource());
        invoice.setActualPaidAmount(request.getActualPaidAmount());
        invoice.setInvoiceAmount(request.getInvoiceAmount());
        invoice.setDeductedAmount(request.getDeductedAmount());
        invoice.setAvailableAmount(request.getAvailableAmount());
        invoice.setFactored(request.getFactored());
        invoice.setAnyDeductions(request.getAnyDeductions());
        invoice.setSendMail(request.isSendMail());
        invoice.setInvoiceType(request.getType());
        invoice.setCurrency(request.getCurrency());

        // New fields
        invoice.setPayeeCode(request.getPayeeCode());
        invoice.setPaymentsStatus(request.getPaymentsStatus());
        invoice.setPaymentNumber(request.getPaymentNumber());
        invoice.setPoNumber(request.getPoNumber());
        invoice.setRemarks(request.getRemarks());
        invoice.setRemainingAmount(request.getRemainingAmount());
        invoice.setDiscountToken(request.getDiscountToken());
        invoice.setTradRequestId(request.getTradRequestId());
        invoice.setPaymentsRemarks(request.getPaymentsRemarks());
        invoice.setInvoicePdf(request.getInvoicePdf());
        invoice.setFeeAmount(request.getFeeAmount());
        invoice.setVatAmount(request.getVatAmount());
        invoice.setRejectReason(request.getRejectReason());
        invoice.setRejectCount(request.getRejectCount() != null ? request.getRejectCount() : 0);
        invoice.setAdvanceAmount(request.getAdvanceAmount());
        invoice.setNetPayable(request.getNetPayable());

        // Dates (handle nulls gracefully)
        invoice.setInvoiceDate(parseDate(request.getInvoiceDate()));
        invoice.setDueDate(parseDate(request.getDueDate()));
        invoice.setInvoiceCreationDate(new Date());
        invoice.setApprovedDate(parseDate(request.getApprovedDate()));
        invoice.setArrivalDate(parseDate(request.getArrivalDate()));
        invoice.setPrevDueDate(parseDate(request.getPrevDueDate()));
        invoice.setSettlementDate(parseDate(request.getSettlementDate()));

        return invoice;
    }

    private Date parseDate(String dateStr) {
        try {
            if (dateStr != null && !dateStr.isBlank()) {
                return Date.from(LocalDate.parse(dateStr).atStartOfDay(ZoneId.systemDefault()).toInstant());
            }
        } catch (Exception e) {
            log.warn("Invalid date format: {}", dateStr);
        }
        return null;
    }
}
