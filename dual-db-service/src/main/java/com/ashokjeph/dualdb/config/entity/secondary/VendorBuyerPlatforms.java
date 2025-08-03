package com.ashokjeph.dualdb.config.entity.secondary;

import com.ashokjeph.dualdb.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VendorBuyerPlatforms implements Serializable {
	@EmbeddedId
    @EqualsAndHashCode.Include
    VendorBuyerPlatformId vendorBuyerPlatformId;

    @ManyToOne
    @JoinColumn(name = "merchant_id", insertable = false, updatable = false)
    @JsonIgnoreProperties({"buyerPlatforms", "company", "bank"})
    @ToString.Exclude
    Merchant merchant;

    @ManyToOne
    @JoinColumn(name = "buyer_platforms_id", insertable = false, updatable = false)
    BuyerPlatform buyerPlatform;

    ProfileStatus status;
    Boolean active;
    
	@Transient
	Long factParamId;
	
	public VendorBuyerPlatforms(VendorBuyerPlatformId vendorBuyerPlatformId, Merchant merchant, BuyerPlatform buyerPlatform, ProfileStatus status, Boolean active) {
		this.vendorBuyerPlatformId = vendorBuyerPlatformId;
		this.merchant = merchant;
		this.buyerPlatform = buyerPlatform;
		this.status = status;
		this.active = active;
	}

}
