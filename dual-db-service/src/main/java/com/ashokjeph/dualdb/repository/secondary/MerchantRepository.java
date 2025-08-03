package com.ashokjeph.dualdb.repository.secondary;

import com.ashokjeph.dualdb.config.entity.secondary.Merchant;
import com.ashokjeph.dualdb.entity.primary.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;

@RepositoryRestResource(exported=false,collectionResourceRel = "merchant", path = "merchant")
public interface MerchantRepository extends JpaRepository<Merchant, String> {
    Merchant findByDisplayId(String displayId);
    Merchant findByUser_Id(String id);
    Merchant findByUserId(String id);
    @Query("SELECT M FROM  Merchant M "
    	 + "WHERE (:#{#fromDate} is null OR (date(M.user.creationTimestamp) between date(:#{#fromDate}) and date(:#{#toDate})) ) And "
    	 + "      (M.accountStatus IN :#{#accountStatus} OR COALESCE(:#{#accountStatus}) is null) And "
    	 + "      (M.user.role IN :#{#userRoles} Or COALESCE(:#{#userRoles}) is null) And "
         + "      (M.user.phoneCountry = :#{#phoneCountry} Or COALESCE(:#{#phoneCountry}) is null) And "
		 + "      (M.leadOwner = :#{#leadOwner} Or COALESCE(:#{#leadOwner}) is null) And "
		 + "      (M.id = :#{#merchantId} Or COALESCE(:#{#merchantId}) is null) And "
		 + "      (M.id in (SELECT bp.merchant.id FROM VendorBuyerPlatforms bp WHERE bp.buyerPlatform.id IN :#{#buyerPlatformIds} And bp.active = true) Or COALESCE(:#{#buyerPlatformIds}) is null)")
    Page<Merchant> findAsParam(@Param("merchantId") String merchantId, @Param("fromDate") Date fromDate, @Param("toDate")Date toDate, @Param("accountStatus") List<String> accountStatus,
							   @Param("userRoles") List<Role> userRoles, @Param("leadOwner") String leadOwner, @Param("phoneCountry") String phoneCountry,
							   @Param("buyerPlatformIds") List<Long> buyerPlatformIds, Pageable page);

//	@Query("select c from Merchant c where (c.user = :#{#cp.user} or :#{#cp.user} is null) And "
//			 + " (c.company = :#{#cp.company} or :#{#cp.company} is null) And "
//			 + " (c.bank = :#{#cp.bank} or :#{#cp.bank} is null) And "
//			 + " (c.documentStatus = :#{#cp.documentStatus} or :#{#cp.documentStatus} is null) And "
//			 + " (c.displayId = :#{#cp.displayId} or :#{#cp.displayId} is null) And "
//			 + " (c.accountStatus = :#{#cp.accountStatus} or :#{#cp.accountStatus} is null)  " )
//	Page<Merchant> findAsParam2(@Param("cp") Merchant cp,Pageable pageable );

/*
	@Query(value="select mbp.merchant_id as merchantId, mbp.buyer_platforms_id as buyerPlatformsId from merchant_buyer_platforms mbp" +
			"where mbp.buyer_platforms_id in (select bp.id from buyer_platform bp where (bp.type_description = ?1 or ?1 IS NULL) And (bp.id = ?2 or ?2 IS NULL)) And " +
			"      mbp.merchant_id in (select m.id from merchant m where (m.account_status = ?3 or ?3 IS NULL) And (m.id = ?4 or ?4 IS NULL))", nativeQuery = true)
	Page<MerchantBuyerPlatformsId> getAllMerchants(String bpTypeDesc,Long buyerPlatformId,String accountStatus,String merchantId,Pageable pageable );
*/
//@Query("select new ai.funder.server.beans.MerchantBuyerPlatformsId(mbp.merchant.id, mbp.buyerPlatform.id) " +
//		"from MerchantBuyerPlatforms mbp " +
//		"where (:bpTypeDesc is null or mbp.buyerPlatform.typeDescription = :bpTypeDesc) and " +
//		"	   (:buyerPlatformId is null or mbp.buyerPlatform.id = :buyerPlatformId) and 	" +
//		"	   (:accountStatus is null or mbp.merchant.accountStatus = :accountStatus) and  	" +
//		"	   (:merchantId is null or mbp.merchant.id = :merchantId)")
//Page<MerchantBuyerPlatformsId> getAllMerchants(
//		@Param("bpTypeDesc") String bpTypeDesc,
//		@Param("buyerPlatformId") Long buyerPlatformId,
//		@Param("accountStatus") String accountStatus,
//		@Param("merchantId") String merchantId,
//		Pageable pageable
//);


}
