package com.ashokjeph.dualdb.config.entity.secondary;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vendor_rpa_invoices")
@Getter
@Setter
@NoArgsConstructor
public class VendorRpaInvoicesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime creationDate;

    @Embedded
    private VendorBuyerPlatformId vendorBuyerPlatformId;
    private String invoiceNumber;
    private BigDecimal invoiceAmount;
    private String invoiceCurrency;
    private LocalDateTime invoiceCreationDate;
    private LocalDateTime invoiceDueDate;
    private String poNumber;
    private String invoiceStatus;
    private String payeeCode;
    private String country;
    private String invoiceFilePath;
    private Boolean invoiceAdded;

}
