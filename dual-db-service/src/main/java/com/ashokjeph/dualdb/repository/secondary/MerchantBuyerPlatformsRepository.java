package com.ashokjeph.dualdb.repository.secondary;

import com.ashokjeph.dualdb.config.entity.secondary.VendorBuyerPlatforms;
import com.ashokjeph.dualdb.config.entity.secondary.VendorBuyerPlatformId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported=false,collectionResourceRel = "MerchantBuyerPlatforms", path = "MerchantBuyerPlatforms")

public interface MerchantBuyerPlatformsRepository extends JpaRepository<VendorBuyerPlatforms, VendorBuyerPlatformId> {
//    List<MerchantBuyerPlatforms> findByBuyerPlatform_identifierAndStatus(String amazon, ProfileStatus verified);

//    List<MerchantBuyerPlatforms> findByStatusAndBuyerPlatform_TypeDescriptionIn(ProfileStatus verified,List<String> typeDescription);

//    @Query("SELECT M FROM MerchantBuyerPlatforms M WHERE M.status = ?2 And M.buyerPlatform.isActive = true And (M.buyerPlatform.identifier = ?1 OR (SELECT COUNT(*) FROM BuyerPlatform B WHERE B.id = M.buyerPlatform.linkedWith And B.identifier = ?1 And B.isActive = true) > 0) ")
//    List<MerchantBuyerPlatforms> getBuyerPlatformForCron(String amazon, ProfileStatus verified);
    
//    List<MerchantBuyerPlatforms> findByBuyerPlatform_TypeDescription(String typeDescription);
//    List<MerchantBuyerPlatforms> findAllByStatusAndBuyerPlatform_IsActive(ProfileStatus verified, Boolean isStatus);
//    List<MerchantBuyerPlatforms> findAllByStatusAndBuyerPlatform_IsActiveAndMerchant_IdAndBuyerPlatform_Id (ProfileStatus verified, Boolean isStatus,String merchantId,Long buyerPlatformId);

//    List<MerchantBuyerPlatforms> findAllByActiveAndMerchant_IdAndBuyerPlatform_IdIn (boolean active, String merchantId, List<Long> bpList);

//    List<MerchantBuyerPlatforms> findByMerchant(Merchant merchant);
}
