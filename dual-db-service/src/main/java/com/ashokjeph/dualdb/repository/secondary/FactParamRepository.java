package com.ashokjeph.dualdb.repository.secondary;

import com.ashokjeph.dualdb.config.entity.secondary.FactParam;
import com.ashokjeph.dualdb.config.entity.secondary.VendorBuyerPlatformId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;
import java.util.List;

@RepositoryRestResource(exported=false,collectionResourceRel = "FactParam", path = "factparam")
public interface FactParamRepository extends JpaRepository<FactParam, Long> {
    public FactParam findById(long id);
    public List<FactParam> findByIdIn(Collection<Long> ids);
    
    @Query(" SELECT f FROM FactParam f WHERE f.vendorBuyerPlatformId.vendorId = ?1 ")
//    public List<FactParam> findByMerchantId(String merchantId);
    public FactParam findTopByVendorBuyerPlatformsId(VendorBuyerPlatformId vendorBuyerPlatformId);
//    public Page<FactParam> findAllByMerchantBuyerPlatformsId_BuyerPlatformsId(Long buyerPlatformId, Pageable pageable);
    @Query("select c from FactParam c where (c.advanceLimitcurrency = :#{#cp.advanceLimitcurrency} or :#{#cp.advanceLimitcurrency} is null) And "
			 + " (c.isActive = :#{#cp.isActive} or :#{#cp.isActive} is null) And "
			 + " (c.paymentCycle = :#{#cp.paymentCycle} or :#{#cp.paymentCycle} is null) And "
			 + " (c.isActive = :#{#cp.isActive} or :#{#cp.isActive} is null) And "
			 + " (c.vendorBuyerPlatformId.vendorId = :#{#cp.vendorBuyerPlatformId.vendorId} or :#{#cp.vendorBuyerPlatformId.vendorId} is null) And "
			 + " (c.vendorBuyerPlatformId.buyerPlatformId = :#{#cp.vendorBuyerPlatformId.buyerPlatformId} or :#{#cp.vendorBuyerPlatformId.buyerPlatformId} is null) " )
	Page<FactParam> findAsParam(@Param("cp") FactParam cp,Pageable pageable );
}



