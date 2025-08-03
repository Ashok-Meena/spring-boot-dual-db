package com.ashokjeph.dualdb.config.entity.secondary;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Slf4j
@EqualsAndHashCode(onlyExplicitlyIncluded = true) 
public class Invoices implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
 
    Long buyerPlatformId;
    String merchantId;
    String userId;
    String marketPlace;
    Date  invoiceDate;  
   
    Date  dueDate;
    String invoiceStatus;
    String source;
    BigDecimal actualPaidAmount;
    String payeeCode;    
    Date  invoiceCreationDate;
    String invoiceNumber;
    BigDecimal invoiceAmount;
    BigDecimal deductedAmount;
    String paymentsStatus;
    String paymentNumber ;
    String poNumber ;
    String Currency ;
    @JsonProperty("type")
    String invoiceType ;
    String remarks ;
    BigDecimal remainingAmount;
    String discountToken ;
    Date approvedDate;
    Date arrivalDate;
    String tradRequestId;        
    BigDecimal availableAmount;
    String paymentsRemarks;
    String invoicePdf;        
    String factored;
    String anyDeductions;
    BigDecimal feeAmount;
    BigDecimal vatAmount;
    Date  prevDueDate;
    Date settlementDate;
    BigDecimal advanceAmount;
    BigDecimal netPayable;
    
    String invUUID;

    boolean isSendMail; 
    String verifyStatus;
    String rejectReason;
    int rejectCount;
    
    //Adding amount_recovered and Fee Recovered at invoice level in invoices table
    BigDecimal amountRecovered;
    BigDecimal feeRecovered;
    BigDecimal feeRebate;
    BigDecimal calculatedFee;
    BigDecimal receivedAmountByBuyer;
    
    String prevStatus;
    BigDecimal reserve;
    BigDecimal feeWithVat;
    BigDecimal buyerPaidAmount;
    Long buyerRemittanceId;

    @Transient
    String page;
    @Transient
    String size;
    @Transient
    String sort;
    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date fromDate;
    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date toDate;

    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date fromDueDate;
    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date toDueDate;

    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date fromApprovedDate;
    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date toApprovedDate;

    @Transient
    List<String> invoiceStatusList;
    @Transient
    List<String> statusExclude;

    @Transient
    Date afterDueDate;
    @Transient
    String message;
    @Transient
    String merchantName;
    @Transient
    List<InvoiceApproval> invoiceApprovalStatus;

    public Invoices(Map<String, Object> invoicesEntry ) {
        merchantId = (String) invoicesEntry.get("merchantId");
        buyerPlatformId = (Long) invoicesEntry.get("buyerPlatformId");
        userId = (String) invoicesEntry.get("userId");
        marketPlace = (String) invoicesEntry.get("marketPlace");
        invoiceDate =  (Date) invoicesEntry.get("invoiceDate");
        dueDate =  (Date ) invoicesEntry.get("dueDate");
        settlementDate = (Date ) invoicesEntry.get("settlementDate");
        invoiceStatus = (String) invoicesEntry.get("invoiceStatus");
        source = (String) invoicesEntry.get("source");
        actualPaidAmount = BigDecimal.valueOf( Double.valueOf(String.valueOf(invoicesEntry.get("actualPaidAmount"))) );                
        payeeCode = (String) invoicesEntry.get("payeeCode");
        invoiceCreationDate =  (Date) invoicesEntry.get("invoiceCreationDate");
        invoiceNumber = (String) invoicesEntry.get("invoiceNumber");
        invoiceAmount = BigDecimal.valueOf( Double.valueOf(String.valueOf(invoicesEntry.get("invoiceAmount"))) );                
        deductedAmount = BigDecimal.valueOf( Double.valueOf(String.valueOf(invoicesEntry.get("deductedAmount"))) );                
        paymentsStatus = (String) invoicesEntry.get("paymentsStatus");
        paymentNumber = (String) invoicesEntry.get("paymentNumber");
        poNumber = (String) invoicesEntry.get("poNumber");
        Currency = (String) invoicesEntry.get("Currency");
        invoiceType = (String) invoicesEntry.get("invoiceType");        
        remarks = (String) invoicesEntry.get("remarks");
        remainingAmount = BigDecimal.valueOf( Double.valueOf(String.valueOf(invoicesEntry.get("remainingAmount"))) );
        discountToken = (String) invoicesEntry.get("discountToken");
        approvedDate =  (Date) invoicesEntry.get("approvedDate");
        arrivalDate =  (Date) invoicesEntry.get("arrivalDate");
        tradRequestId = (String) invoicesEntry.get("tradRequestId");
        availableAmount = BigDecimal.valueOf( Double.valueOf(String.valueOf(invoicesEntry.get("availableAmount"))) );
        paymentsRemarks = (String) invoicesEntry.get("paymentsRemarks");
        invoicePdf = (String) invoicesEntry.get("invoicePdf");
        factored = (String) invoicesEntry.get("factored");
        anyDeductions = (String) invoicesEntry.get("anyDeductions");
        feeAmount = BigDecimal.valueOf( Double.valueOf(String.valueOf(invoicesEntry.get("feeAmount"))) );
        vatAmount = BigDecimal.valueOf( Double.valueOf(String.valueOf(invoicesEntry.get("vatAmount"))) );
        prevDueDate =  (Date ) invoicesEntry.get("prevDueDate");
        rejectReason = (String) invoicesEntry.get("rejectReason");
        rejectCount = (int) invoicesEntry.get("rejectCount");
        netPayable = BigDecimal.valueOf( Double.valueOf(String.valueOf(invoicesEntry.get("netPayable"))) );
        advanceAmount = BigDecimal.valueOf( Double.valueOf(String.valueOf(invoicesEntry.get("advanceAmount"))) );
    }

    public void update(Map<String, Object> invoicesEntry ) {        
        merchantId = (String) ( invoicesEntry.get("merchantId") == null? merchantId : invoicesEntry.get("merchantId"));        
        merchantId = (String) (invoicesEntry.get("merchantId") == null? merchantId : invoicesEntry.get("merchantId"));        
        buyerPlatformId = (Long)( invoicesEntry.get("buyerPlatformId") == null? buyerPlatformId : invoicesEntry.get("buyerPlatformId"));        
        userId = (String) (invoicesEntry.get("userId") == null? userId : invoicesEntry.get("userId"));        
        marketPlace = (String) (invoicesEntry.get("marketPlace") == null? marketPlace : invoicesEntry.get("marketPlace"));                
        invoiceStatus = (String) (invoicesEntry.get("invoiceStatus") == null? invoiceStatus : invoicesEntry.get("invoiceStatus"));        
        source = (String) (invoicesEntry.get("source") == null? source : invoicesEntry.get("source"));                
        payeeCode = (String) (invoicesEntry.get("payeeCode") == null? payeeCode : invoicesEntry.get("payeeCode"));                
        invoiceNumber = (String) (invoicesEntry.get("invoiceNumber") == null? invoiceNumber : invoicesEntry.get("invoiceNumber"));                
        paymentsStatus = (String) (invoicesEntry.get("paymentsStatus") == null? paymentsStatus : invoicesEntry.get("paymentsStatus"));        
        paymentNumber = (String) (invoicesEntry.get("paymentNumber") == null? paymentNumber : invoicesEntry.get("paymentNumber"));        
        poNumber = (String) (invoicesEntry.get("poNumber") == null? poNumber : invoicesEntry.get("poNumber"));        
        Currency = (String) (invoicesEntry.get("Currency") == null? Currency : invoicesEntry.get("Currency"));        
        invoiceType = (String) (invoicesEntry.get("invoiceType") == null? invoiceType : invoicesEntry.get("invoiceType"));                
        remarks = (String) (invoicesEntry.get("remarks") == null? remarks : invoicesEntry.get("remarks"));                
        discountToken = (String) (invoicesEntry.get("discountToken") == null? discountToken : invoicesEntry.get("discountToken"));                
        tradRequestId = (String) (invoicesEntry.get("tradRequestId") == null? tradRequestId : invoicesEntry.get("tradRequestId"));                
        paymentsRemarks = (String) (invoicesEntry.get("paymentsRemarks") == null? paymentsRemarks : invoicesEntry.get("paymentsRemarks"));        
        invoicePdf = (String) (invoicesEntry.get("invoicePdf") == null? invoicePdf : invoicesEntry.get("invoicePdf"));        
        factored = (String) (invoicesEntry.get("factored") == null? factored : invoicesEntry.get("factored"));        
        anyDeductions = (String) (invoicesEntry.get("anyDeductions") == null? anyDeductions : invoicesEntry.get("anyDeductions"));        
        rejectReason = (String) (invoicesEntry.get("rejectReason") == null? rejectReason : invoicesEntry.get("rejectReason"));        
        rejectCount = (int)( invoicesEntry.get("rejectCount") == null? rejectCount : invoicesEntry.get("rejectCount"));
        
         actualPaidAmount = (BigDecimal) (invoicesEntry.get("actualPaidAmount")==null?actualPaidAmount:invoicesEntry.get("actualPaidAmount"));
         invoiceAmount = (BigDecimal) (invoicesEntry.get("invoiceAmount")==null?invoiceAmount:invoicesEntry.get("invoiceAmount"));
         deductedAmount = (BigDecimal) (invoicesEntry.get("deductedAmount")==null?deductedAmount:invoicesEntry.get("deductedAmount"));
         remainingAmount = (BigDecimal) (invoicesEntry.get("remainingAmount")==null?remainingAmount:invoicesEntry.get("remainingAmount"));
         availableAmount = (BigDecimal) (invoicesEntry.get("availableAmount")==null?availableAmount:invoicesEntry.get("availableAmount"));        
         feeAmount = BigDecimal.valueOf( Double.valueOf(String.valueOf(invoicesEntry.get("feeAmount"))) );
         vatAmount = BigDecimal.valueOf( Double.valueOf(String.valueOf(invoicesEntry.get("vatAmount"))) );
         netPayable = BigDecimal.valueOf( Double.valueOf(String.valueOf(invoicesEntry.get("netPayable"))) );
         advanceAmount = BigDecimal.valueOf( Double.valueOf(String.valueOf(invoicesEntry.get("advanceAmount"))) );
         /*actualPaidAmount = BigDecimal.valueOf( Double.valueOf(String.valueOf(invoicesEntry.get("actualPaidAmount"))) );                
        invoiceAmount = BigDecimal.valueOf( Double.valueOf(String.valueOf(invoicesEntry.get("invoiceAmount"))) );                
        deductedAmount = BigDecimal.valueOf( Double.valueOf(String.valueOf(invoicesEntry.get("deductedAmount"))) );                
        remainingAmount = BigDecimal.valueOf( Double.valueOf(String.valueOf(invoicesEntry.get("remainingAmount"))) );
        availableAmount = BigDecimal.valueOf( Double.valueOf(String.valueOf(invoicesEntry.get("availableAmount"))) );
        */
        invoiceDate =  Calendar.getInstance().getTime(); //(Date) invoicesEntry.get("invoiceDate");        
        dueDate =  (Date ) invoicesEntry.get("dueDate");
        settlementDate = (Date ) invoicesEntry.get("settlementDate");
        invoiceCreationDate =  Calendar.getInstance().getTime(); //(Date) invoicesEntry.get("invoiceCreationDate");
        approvedDate =  Calendar.getInstance().getTime(); //(Date) invoicesEntry.get("approvedDate");
        arrivalDate =  Calendar.getInstance().getTime(); //(Date) invoicesEntry.get("arrivalDate");
        prevDueDate =  (Date ) invoicesEntry.get("prevDueDate");
    }
    
    
//   public void  updateJson(Invoices inv)
//    {
//	if ( inv != null && inv.getId() > 0  && inv.getId()== this.getId()) 
//        {	
//            this.merchantId = (String)(inv.getMerchantId() == null ? this.merchantId: inv.getMerchantId());
//            this.buyerPlatformId = (Long) (inv.getBuyerPlatformId() == null ? this.buyerPlatformId : inv.getBuyerPlatformId());
//            this.userId = (String) (inv.getUserId() == null ? this.userId : inv.getUserId());  
//            this.marketPlace =(String) (inv.getMarketPlace() == null ? this.marketPlace : inv.getMarketPlace());
//            this.invoiceStatus = (String) (inv.getInvoiceStatus() == null? this.invoiceStatus : inv.getInvoiceStatus()); 
//            this.source = (String) (inv.getSource() == null? this.source : inv.getSource()); 
//            this.payeeCode = (String) (inv.getPayeeCode() == null? this.payeeCode : inv.getPayeeCode());
//            this.invoiceNumber = (String) (inv.getInvoiceNumber() == null? this.invoiceNumber : inv.getInvoiceNumber());
//            this.paymentsStatus = (String) (inv.getPaymentsStatus() == null? this.paymentsStatus : inv.getPaymentsStatus());
//            this.paymentNumber = (String) (inv.getPaymentNumber() == null? this.paymentNumber : inv.getPaymentNumber());
//            this.poNumber = (String) (inv.getPoNumber() == null? this.poNumber : inv.getPoNumber());
//            this.Currency = (String) (inv.getCurrency() == null? this.Currency : inv.getCurrency());
//            this.invoiceType = (String) (inv.getInvoiceType() == null? this.invoiceType : inv.getInvoiceType());
//            this.remarks = (String) (inv.getRemarks() == null? this.remarks : inv.getRemarks());                
//            this.discountToken = (String) (inv.getDiscountToken() == null? this.discountToken : inv.getDiscountToken());
//            this.tradRequestId = (String) (inv.getTradRequestId() == null ? this.tradRequestId : inv.getTradRequestId());
//            this.paymentsRemarks = (String) (inv.getPaymentsRemarks() == null ? this.paymentsRemarks : inv.getPaymentsRemarks());
//            this.invoicePdf = (String) (inv.getInvoicePdf() == null ? this.invoicePdf : inv.getInvoicePdf());
//            this.factored = (String) (inv.getFactored() == null ? this.factored : inv.getFactored());
//            this.anyDeductions = (String) (inv.getAnyDeductions() == null ? this.anyDeductions : inv.getAnyDeductions());
//            this.actualPaidAmount = (BigDecimal) (inv.getActualPaidAmount() == null ? this.actualPaidAmount : inv.getActualPaidAmount());
//            this.invoiceAmount = (BigDecimal) (inv.getInvoiceAmount() == null ? this.invoiceAmount : inv.getInvoiceAmount());
//            this.deductedAmount = (BigDecimal) (inv.getDeductedAmount() == null ? this.deductedAmount : inv.getDeductedAmount());
//            this.remainingAmount = (BigDecimal) (inv.getRemainingAmount() == null ? this.remainingAmount : inv.getRemainingAmount());
//            this.availableAmount = (BigDecimal) (inv.getAvailableAmount() == null ? this.availableAmount : inv.getAvailableAmount());
//            this.feeAmount = (BigDecimal) (inv.getFeeAmount() == null ? this.feeAmount : inv.getFeeAmount());
//            this.vatAmount = (BigDecimal) (inv.getVatAmount() == null ? this.vatAmount : inv.getVatAmount());
//            this.invoiceDate = (Date) (inv.getInvoiceDate() == null ? this.invoiceDate : inv.getInvoiceDate());
//            this.dueDate = (Date) (inv.getDueDate() == null ? this.dueDate : inv.getDueDate());
//            this.settlementDate = (Date) (inv.getSettlementDate() == null ? this.settlementDate : inv.getSettlementDate());
//            this.invoiceCreationDate = (Date) (inv.getInvoiceCreationDate() == null ? this.invoiceCreationDate : inv.getInvoiceCreationDate());
//            this.approvedDate = (Date) (inv.getApprovedDate() == null ? this.approvedDate : inv.getApprovedDate());
//            this.arrivalDate = (Date) (inv.getArrivalDate() == null? this.arrivalDate : inv.getArrivalDate());                
//            this.prevDueDate = (Date) (inv.getPrevDueDate() == null ? this.prevDueDate : inv.getPrevDueDate());
//            this.rejectReason = (String) (inv.getRejectReason() == null ? this.rejectReason : inv.getRejectReason());
//            this.rejectCount = (int) (inv.getRejectCount() == 0 ? this.rejectCount : inv.getRejectCount());
//            
//            this.netPayable = (BigDecimal) (inv.getNetPayable() == null ? this.netPayable : inv.getNetPayable());
//            this.advanceAmount = (BigDecimal) (inv.getAdvanceAmount() == null ? this.advanceAmount : inv.getAdvanceAmount());
//        }        
//    }
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Invoices))
      return false;
    Invoices invoices = (Invoices) o;
    return Objects.equals(this.id, invoices.id) && 
            Objects.equals(this.merchantId, invoices.merchantId)&& 
            Objects.equals(this.actualPaidAmount, invoices.actualPaidAmount);
  }
  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.merchantId, this.actualPaidAmount);
  }

  @Override
  public String toString() {
    return "Invoices {" + "Id=" + this.id +
            ", merchantId='" + this.merchantId +
            '\'' + ", amount='" + this.actualPaidAmount + '\'' +
            '\'' + ", invoicePdf='" + this.invoicePdf + '\'' +
            '}';
  }
}



