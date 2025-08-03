package com.ashokjeph.dualdb.config.entity.secondary;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class AmazonVCInvoices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @CreationTimestamp
    Date creationDate;

    @Embedded
    VendorBuyerPlatformId merchantBuyerPlatformsId;
    String marketPlace;
    Date invoiceDate;
    Date paymentDueDate;
    String invoiceStatus;
    BigDecimal actualPaidAmount;
    String actualPaidCurrancy;
    String payee;
    Date invoiceCreationDate;
    String invoiceNumber;
    BigDecimal invoiceAmount;
    String invoiceCurrancy;
    Boolean anyDeduction;
    String invoicePDFPath;
    String poNumber;
    Boolean invoiceAdded;

    @Transient
    String page;
    @Transient
    String size;
    @Transient
    String sort;
    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date fromDate;
    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date toDate;
}
