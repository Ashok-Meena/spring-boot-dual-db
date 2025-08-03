package com.ashokjeph.dualdb.repository.secondary;

import com.ashokjeph.dualdb.config.entity.secondary.AmazonVCInvoices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported=false,collectionResourceRel = "AmazonVCInvoices", path = "AmazonVCInvoices")
public interface AmazonVCInvoiceRepository extends JpaRepository<AmazonVCInvoices, Long> {

	AmazonVCInvoices findByInvoiceNumberAndPayee(String invoiceNumber, String payee);
	
//	@Query("select c from AmazonVCInvoices c where (c.id = :#{#cp.id} or :#{#cp.id} is null or :#{#cp.id} ='') And "
//			+ " (c.creationDate = :#{#cp.creationDate} or :#{#cp.creationDate} is null) And "
//			+ " (c.marketPlace = :#{#cp.marketPlace} or :#{#cp.marketPlace} is null) And "
//			+ " (c.invoiceDate = :#{#cp.invoiceDate} or :#{#cp.invoiceDate} is null) And "
//			+ " (c.paymentDueDate = :#{#cp.paymentDueDate} or :#{#cp.paymentDueDate} is null) And "
//			+ " (c.invoiceStatus = :#{#cp.invoiceStatus} or :#{#cp.invoiceStatus} is null) And "
//			+ " (c.payee = :#{#cp.payee} or :#{#cp.payee} is null) And "
//			+ " (:#{#cp.merchantBuyerPlatformsId} is null Or c.merchantBuyerPlatformsId.merchantId = :#{#cp.merchantBuyerPlatformsId.merchantId} or :#{#cp.merchantBuyerPlatformsId.merchantId} is null ) And "
//			+ " (:#{#cp.merchantBuyerPlatformsId} is null Or c.merchantBuyerPlatformsId.buyerPlatformsId = :#{#cp.merchantBuyerPlatformsId.buyerPlatformsId} or :#{#cp.merchantBuyerPlatformsId.buyerPlatformsId} is null) And "
//			+ " (cast(c.invoiceCreationDate as date) >= cast(:#{#cp.fromDate} as date) or :#{#cp.fromDate} is null) And "
//			+ " (cast(c.invoiceCreationDate as date) <= cast(:#{#cp.toDate} as date) or :#{#cp.toDate} is null) And "
//			+ " (c.invoiceCreationDate = :#{#cp.invoiceCreationDate} or :#{#cp.invoiceCreationDate} is null) And "
//			+ " (c.invoiceNumber = :#{#cp.invoiceNumber} or :#{#cp.invoiceNumber} is null) And "
//			+ " (c.poNumber = :#{#cp.poNumber} or :#{#cp.poNumber} is null) " )
//	Page<AmazonVCInvoices> findAsParam(@Param("cp") AmazonVCInvoices cp, Pageable pageable );

}
