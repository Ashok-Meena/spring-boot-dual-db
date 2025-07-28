package com.ashokjeph.dualdb.invoices.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ManualInvoiceRequestModel {
    @NotBlank
    private String invoiceNumber;

    @NotBlank
    private String merchantId;

    @NotBlank
    private String buyerPlatformId;

    @NotBlank
    private String invoiceDate; // e.g. "2025-07-15"

    @NotBlank
    private String dueDate;

    private String invoiceStatus;
    private String source;
    private BigDecimal actualPaidAmount;
    private String invoiceCreationDate;
    private BigDecimal invoiceAmount;
    private BigDecimal deductedAmount;
    private BigDecimal availableAmount;
    private String factored;
    private String anyDeductions;
    private boolean sendMail;
    private String type;
    private String currency;

    // Additional fields
    private String payeeCode;
    private String paymentsStatus;
    private String paymentNumber;
    private String poNumber;
    private String remarks;
    private BigDecimal remainingAmount;
    private String discountToken;
    private String approvedDate;
    private String arrivalDate;
    private String tradRequestId;
    private String paymentsRemarks;
    private String invoicePdf;
    private String prevDueDate;
    private String settlementDate;
    private BigDecimal feeAmount;
    private BigDecimal vatAmount;
    private String rejectReason;
    private Integer rejectCount;
    private BigDecimal advanceAmount;
    private BigDecimal netPayable;
}
