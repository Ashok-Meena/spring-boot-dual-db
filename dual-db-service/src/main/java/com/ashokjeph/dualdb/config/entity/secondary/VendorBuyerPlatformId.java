package com.ashokjeph.dualdb.config.entity.secondary;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorBuyerPlatformId implements Serializable {

    @Column(name = "vendor_id", length = 50)
    public String vendorId;

    @Column(name = "buyer_platform_id")
    public Long buyerPlatformId;
}
