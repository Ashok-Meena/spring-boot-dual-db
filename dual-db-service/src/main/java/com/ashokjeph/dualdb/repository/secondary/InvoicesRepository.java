package com.ashokjeph.dualdb.repository.secondary;

//import ai.funder.server.beans.iInvoicesUnpaid;
import com.ashokjeph.dualdb.config.entity.secondary.Invoices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported=false,collectionResourceRel = "invoices", path = "invoices")
public interface InvoicesRepository extends JpaRepository<Invoices, Long> {
	//Invoices findById(long id);

	Invoices findByMerchantIdAndId(String merchantId, Long id);

	List<Invoices> findByMerchantId(String merchantId);

	List<Invoices> findByMerchantIdAndBuyerPlatformId(String merchantId, Long buyerPlatformId);

	Page<Invoices> findByBuyerPlatformId(Long buyerPlatformId, Pageable pageable);

//	@Query("Select INV.id as id, INV.invoiceAmount as invoiceAmount, " + " INV.userId as userId, "
//			+ " INV.merchantId as merchantId, " + " INV.buyerPlatformId as buyerPlatformsId, "
//			+ " INV.marketPlace as  marketPlace ," + " INV.invoiceDate as invoiceDate ," + " INV.dueDate as dueDate,"
//			+ " INV.invoiceStatus as invoiceStatus," + " INV.source as source,"
//			+ " INV.actualPaidAmount as actualPaidAmount," + " INV.payeeCode as payeeCode,"
//			+ " INV.invoiceCreationDate as invoiceCreationDate," + " INV.invoiceNumber as invoiceNumber,"
//			+ " INV.invoiceAmount as invoiceAmount," + " INV.deductedAmount as deductedAmount,"
//			+ " INV.paymentsStatus as paymentsStatus ," + " INV.paymentNumber as paymentNumber,"
//			+ " INV.poNumber  as poNumber ," + " INV.Currency as currency," + " INV.invoiceType as type,"
//			+ " INV.remarks as remarks," + " INV.remainingAmount as remainingAmount ,"
//			+ " INV.discountToken as discountToken," + " INV.approvedDate as approvedDate,"
//			+ " INV.arrivalDate as arrivalDate," + " INV.tradRequestId as tradRequestId,"
//			+ " INV.availableAmount as availableAmount," + " INV.paymentsRemarks as paymentsRemarks,"
//			+ " INV.invoicePdf as invoicePdf," + " INV.factored as factored," + " INV.anyDeductions as anyDeductions,"
//			+ " (INV.availableAmount * (CASE WHEN INV.invoiceStatus = 'Submitted' THEN F.unapprovedAdvacePercent ELSE F.advancePercent END) )/100 as advanceAmount, "
//			+ " (datediff(INV.dueDate, now())*(( (CASE WHEN INV.invoiceStatus = 'Submitted' THEN F.unapprovedDailyFeePercent ELSE F.dailyFactFeePercent END)/100) * ((INV.availableAmount *  ((CASE WHEN INV.invoiceStatus = 'Submitted' THEN F.unapprovedAdvacePercent ELSE F.advancePercent END) /100))))) + "
//			+ " (datediff(INV.dueDate, now())*(( (CASE WHEN INV.invoiceStatus = 'Submitted' THEN F.unapprovedDailyFeePercent ELSE F.dailyFactFeePercent END)/100) * ((INV.availableAmount *  ((CASE WHEN INV.invoiceStatus = 'Submitted' THEN F.unapprovedAdvacePercent ELSE F.advancePercent END) /100))))) * ifnull(F.vatPercent,0)/100 as feeAmount,  "
//			+ " (datediff(INV.dueDate, now())*(( (CASE WHEN INV.invoiceStatus = 'Submitted' THEN F.unapprovedDailyFeePercent ELSE F.dailyFactFeePercent END)/100) * ((INV.availableAmount *  ((CASE WHEN INV.invoiceStatus = 'Submitted' THEN F.unapprovedAdvacePercent ELSE F.advancePercent END) /100))))) * ifnull(F.vatPercent,0)/100 as vatAmount,  "
//
//			+ " ((INV.availableAmount * ((CASE WHEN INV.invoiceStatus = 'Submitted' THEN F.unapprovedAdvacePercent ELSE F.advancePercent END) )/100 ) - "
//			+ "  ((datediff(INV.dueDate, now())*(( (CASE WHEN INV.invoiceStatus = 'Submitted' THEN F.unapprovedDailyFeePercent ELSE F.dailyFactFeePercent END)/100) * ((INV.availableAmount *  ((CASE WHEN INV.invoiceStatus = 'Submitted' THEN F.unapprovedAdvacePercent ELSE F.advancePercent END) /100))))) + "
//			+ "   (datediff(INV.dueDate, now())*(( (CASE WHEN INV.invoiceStatus = 'Submitted' THEN F.unapprovedDailyFeePercent ELSE F.dailyFactFeePercent END)/100) * ((INV.availableAmount *  ((CASE WHEN INV.invoiceStatus = 'Submitted' THEN F.unapprovedAdvacePercent ELSE F.advancePercent END) /100))))) * ifnull(F.vatPercent,0)/100) ) as invoicePayableAmount  "
//
//			+ " FROM Invoices INV " + " join FactParam F on F.merchantBuyerPlatformsId.merchantId=INV.merchantId And F.merchantBuyerPlatformsId.buyerPlatformsId=INV.buyerPlatformId  "
//			+ " WHERE (INV.dueDate >  now() ) "
//			+ " 	AND (IFNULL(INV.actualPaidAmount,0) = 0)   "
//			+ " 	AND (INV.factored='No' or INV.factored='false') "
//			+ " 	AND (INV.invoiceType = 'Normal') "
//			+ "     AND (INV.invoiceStatus NOT IN ('Under Review','Rejected','Paid')) "
//			+ "     AND (IFNULL(INV.tradRequestId,'') = '') "
//			+ "     AND (F.isActive = 1 And F.merchantBuyerPlatformsId.merchantId = ?1 And F.merchantBuyerPlatformsId.buyerPlatformsId = ?2) "
//	)
//	List<iInvoicesUnpaid> getInvoicesUnpaid(String merchantId, Long buyerPlatformsId);

	public List<Invoices> findByIdIn(Collection<Long> ids);

//	@Query("Select INV.id as id, INV.invoiceAmount as invoiceAmount, " + " INV.userId as userId, "
//			+ " INV.merchantId as merchantId, " + " INV.buyerPlatformId as buyerPlatformsId, "
//			+ " INV.marketPlace as  marketPlace ," + " INV.invoiceDate as invoiceDate ," + " INV.dueDate as dueDate,"
//			+ " INV.invoiceStatus as invoiceStatus," + " INV.source as source,"
//			+ " INV.actualPaidAmount as actualPaidAmount," + " INV.payeeCode as payeeCode,"
//			+ " INV.invoiceCreationDate as invoiceCreationDate," + " INV.invoiceNumber as invoiceNumber,"
//			+ " INV.invoiceAmount as invoiceAmount," + " INV.deductedAmount as deductedAmount,"
//			+ " INV.paymentsStatus as paymentsStatus ," + " INV.paymentNumber as paymentNumber,"
//			+ " INV.poNumber  as poNumber ," + " INV.Currency as currency," + " INV.invoiceType as type,"
//			+ " INV.remarks as remarks," + " INV.remainingAmount as remainingAmount ,"
//			+ " INV.discountToken as discountToken," + " INV.approvedDate as approvedDate,"
//			+ " INV.arrivalDate as arrivalDate," + " INV.tradRequestId as tradRequestId,"
//			+ " INV.availableAmount as availableAmount," + " INV.paymentsRemarks as paymentsRemarks,"
//			+ " INV.invoicePdf as invoicePdf," + " INV.factored as factored," + " INV.anyDeductions as anyDeductions,"
//			+ " (INV.availableAmount * F.advancePercent )/100 as advanceAmount, "
//			+ " (datediff(INV.dueDate, now())*(( (CASE WHEN INV.invoiceStatus = 'Submitted' THEN F.unapprovedDailyFeePercent ELSE F.dailyFactFeePercent END)/100) * ((INV.availableAmount *  (F.advancePercent /100))))) + "
//			+ " (datediff(INV.dueDate, now())*(( (CASE WHEN INV.invoiceStatus = 'Submitted' THEN F.unapprovedDailyFeePercent ELSE F.dailyFactFeePercent END)/100) * ((INV.availableAmount *  (F.advancePercent /100))))) * ifnull(F.vatPercent,0.00)/100 as feeAmount,  "
//			+ " (datediff(INV.dueDate, now())*(( (CASE WHEN INV.invoiceStatus = 'Submitted' THEN F.unapprovedDailyFeePercent ELSE F.dailyFactFeePercent END)/100) * ((INV.availableAmount *  (F.advancePercent /100))))) * ifnull(F.vatPercent,0.00)/100 as vatAmount,  "
//
//			+ " ((INV.availableAmount * ((CASE WHEN INV.invoiceStatus = 'Submitted' THEN F.unapprovedAdvacePercent ELSE F.advancePercent END) )/100 ) - "
//			+ "  ((datediff(INV.dueDate, now())*(( (CASE WHEN INV.invoiceStatus = 'Submitted' THEN F.unapprovedDailyFeePercent ELSE F.dailyFactFeePercent END)/100) * ((INV.availableAmount *  ((CASE WHEN INV.invoiceStatus = 'Submitted' THEN F.unapprovedAdvacePercent ELSE F.advancePercent END) /100))))) + "
//			+ "   (datediff(INV.dueDate, now())*(( (CASE WHEN INV.invoiceStatus = 'Submitted' THEN F.unapprovedDailyFeePercent ELSE F.dailyFactFeePercent END)/100) * ((INV.availableAmount *  ((CASE WHEN INV.invoiceStatus = 'Submitted' THEN F.unapprovedAdvacePercent ELSE F.advancePercent END) /100))))) * ifnull(F.vatPercent,0)/100) ) as invoicePayableAmount  "
//
//			+ " from Invoices INV " + " join FactParam F on F.merchantBuyerPlatformsId.merchantId=INV.merchantId "
//			+ " and F.merchantBuyerPlatformsId.buyerPlatformsId=INV.buyerPlatformId  " + " WHERE   "
//			+ " INV.dueDate >  now() " + " AND (INV.actualPaidAmount = null  or INV.actualPaidAmount = 0)   "
//			+ " AND (INV.factored='No' or INV.factored='false') " + " AND INV.invoiceType = 'Normal' "
//			+ " AND  F.isActive = 1 and F.merchantBuyerPlatformsId.merchantId = ?1 and F.merchantBuyerPlatformsId.buyerPlatformsId = ?2 "
//			+ " and (date(INV.dueDate) between ?3 and ?4)")
//	List<iInvoicesUnpaid> getInvoicesUnpaidDateWise(String merchantId, Long buyerPlatformsId, Date fromDate,
//			Date toDate);

	@Query(" SELECT INV FROM Invoices INV "
			+ " WHERE (date(INV.invoiceCreationDate) between date(?1) and date(?2)) and INV.merchantId=?3 and INV.buyerPlatformId=?4")
	public Page<Invoices> getFilterByCreationdate(Object fromDate, Object toDate, String merchantId,long buyerPlatformsId,Pageable pageable);

	public Invoices findByInvUUID(String invUUID);

	public Page<Invoices> findAllByInvoiceStatus(String invoiceStatus, Pageable pageable);
	public Page<Invoices> findAllByInvoiceStatusNot(String invoiceStatus, Pageable pageable);

	public Page<Invoices> findAllByInvoiceNumberLike(String invoiceNumber, Pageable pageable);
	
    @Query("select c from Invoices c where (c.id = :#{#cp.id} or :#{#cp.id} is null) And "
    		 + " (c.buyerPlatformId = :#{#cp.buyerPlatformId} or :#{#cp.buyerPlatformId} is null) And "
			 + " (c.merchantId = :#{#cp.merchantId} or :#{#cp.merchantId} is null) And "
			 + " (c.userId = :#{#cp.userId} or :#{#cp.userId} is null) And "
			 + " (c.marketPlace = :#{#cp.marketPlace} or :#{#cp.marketPlace} is null) And "
			 + " (c.invoiceDate = :#{#cp.invoiceDate} or :#{#cp.invoiceDate} is null) And "
			 + " (DATE(c.invoiceDate) >= DATE(:#{#cp.fromDate}) Or (COALESCE(:#{#cp.fromDate}) is null) ) And "
			 + " (DATE(c.invoiceDate) <= DATE(:#{#cp.toDate}) Or (COALESCE(:#{#cp.toDate}) is null) ) And "

			+ " (DATE(c.dueDate) >= DATE(:#{#cp.fromDueDate}) Or (COALESCE(:#{#cp.fromDueDate}) is null) ) And "
			+ " (DATE(c.dueDate) <= DATE(:#{#cp.toDueDate}) Or (COALESCE(:#{#cp.toDueDate}) is null) ) And "

			+ " (DATE(c.approvedDate) >= DATE(:#{#cp.fromApprovedDate}) Or (COALESCE(:#{#cp.fromApprovedDate}) is null) ) And "
			+ " (DATE(c.approvedDate) <= DATE(:#{#cp.toApprovedDate}) Or (COALESCE(:#{#cp.toApprovedDate}) is null) ) And "

			 + " (c.dueDate = :#{#cp.dueDate} or :#{#cp.dueDate} is null) And "
			 + " (c.dueDate >= :#{#cp.afterDueDate} or :#{#cp.afterDueDate} is null) And "
			 + " (c.invoiceStatus = :#{#cp.invoiceStatus} or :#{#cp.invoiceStatus} is null) And "
			 + " (COALESCE(:#{#cp.invoiceStatusList}) is null Or c.invoiceStatus IN :#{#cp.invoiceStatusList}) And "
			 + " (COALESCE(:#{#cp.statusExclude}) is null Or c.invoiceStatus NOT IN :#{#cp.statusExclude}) And "
			 + " (c.source = :#{#cp.source} or :#{#cp.source} is null) And "
			 + " (c.payeeCode = :#{#cp.payeeCode} or :#{#cp.payeeCode} is null) And "
			 + " (c.invoiceCreationDate = :#{#cp.invoiceCreationDate} or :#{#cp.invoiceCreationDate} is null) And "
			 + " (c.invoiceNumber = :#{#cp.invoiceNumber} or :#{#cp.invoiceNumber} is null) And "
			 + " (c.paymentsStatus = :#{#cp.paymentsStatus} or :#{#cp.paymentsStatus} is null) And "
			 + " (c.paymentNumber = :#{#cp.paymentNumber} or :#{#cp.paymentNumber} is null) And "
			 + " (c.poNumber = :#{#cp.poNumber} or :#{#cp.poNumber} is null) And "
			 + " (c.Currency = :#{#cp.Currency} or :#{#cp.Currency} is null) And "
			 + " (c.invoiceType = :#{#cp.invoiceType} or :#{#cp.invoiceType} is null) And "
			 + " (c.remarks = :#{#cp.remarks} or :#{#cp.remarks} is null) And "
			 + " (c.approvedDate = :#{#cp.approvedDate} or :#{#cp.approvedDate} is null) And "
			 + " (c.arrivalDate = :#{#cp.arrivalDate} or :#{#cp.arrivalDate} is null) And "
			 + " (c.factored = :#{#cp.factored} or :#{#cp.factored} is null) And "
			 + " (c.tradRequestId = :#{#cp.tradRequestId} or :#{#cp.tradRequestId} is null) And "
			 + " (COALESCE(c.actualPaidAmount,0) = :#{#cp.actualPaidAmount} or :#{#cp.actualPaidAmount} is null) And "
			 + " (c.verifyStatus = :#{#cp.verifyStatus} or :#{#cp.verifyStatus} is null) And "
			 + " (c.buyerPaidAmount > :#{#cp.buyerPaidAmount} or :#{#cp.buyerPaidAmount} is null) " )
	Page<Invoices> findAsParam(@Param("cp") Invoices cp,Pageable pageable );
    public Optional<Invoices> findByMerchantIdAndBuyerPlatformIdAndInvoiceNumber(String merchantId, Long buyerPlatformId, String invoiceNo);
    
    public List<Invoices> findAllByDueDateLessThanAndInvoiceStatusNotInAndTradRequestIdIsNotNull(Date dueDate, List<String> status);
	public Optional<Invoices> findTopByInvoiceNumber(String invoiceNumber);
	Page<Invoices> findAllByMerchantIdAndBuyerPlatformId(String merchantId, Long buyerPlatformId, Pageable pageable);

	@Query(value="select distinct invoice_number from invoices where (Year(invoice_date) = ?1 Or ?1 is null) And merchant_id = ?2 And buyer_platform_id = ?3",nativeQuery = true)
	List<String> getUniqueInvoiceNumbers(Integer year, String merchantId, Long buyerplatformId);
}
