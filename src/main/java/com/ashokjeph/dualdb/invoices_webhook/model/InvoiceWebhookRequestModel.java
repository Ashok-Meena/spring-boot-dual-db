package com.ashokjeph.dualdb.invoices_webhook.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InvoiceWebhookRequestModel {

    @NotNull(message = "buyerPlatformId is required")
    private Long buyerPlatformId;

    @NotBlank(message = "vendorId is required")
    private String vendorId;

    @NotBlank(message = "invoiceNumber is required")
    private String invoiceNumber;

    @NotNull(message = "invoiceAmount is required")
    private BigDecimal invoiceAmount;

    @NotBlank(message = "invoiceCurrency is required")
    private String invoiceCurrency;

    @NotNull(message = "invoiceCreationDt is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime invoiceCreationDt;

    @NotNull(message = "invoiceDueDt is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime invoiceDueDt;

    @NotBlank(message = "poNumber is required")
    private String poNumber;

    @NotBlank(message = "invoiceStatus is required")
    private String invoiceStatus;

    private String payeeCode = "";

    @NotBlank(message = "invoiceFileType is required")
    private String invoiceFileType;  // Expected values: "file" or "url"

    @NotNull(message = "invoiceFileValue is required")
    private MultipartFile invoiceFileValue;

    @NotBlank(message = "country is required")
    private String country;
}
