package com.ashokjeph.dualdb.invoices_webhook.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GetInvoiceListResponseModel {

    private Long id;
    private String invoiceNumber;
    private BigDecimal invoiceAmount;
    private String invoiceCurrency;
    private LocalDateTime invoiceDate;
    private String invoiceStatus;

}
