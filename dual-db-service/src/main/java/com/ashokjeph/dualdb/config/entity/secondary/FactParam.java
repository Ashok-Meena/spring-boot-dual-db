package com.ashokjeph.dualdb.config.entity.secondary;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class FactParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 50)
    private String factParamOrderNum;    
    @Column(precision = 19, scale = 4)   
    private BigDecimal dailyFactFeePercent;
    @Column(precision = 19, scale = 4)   
    private BigDecimal unapprovedDailyFeePercent;
    @Column(precision = 19, scale = 4)   
    private BigDecimal underUtilizationFeePercent;
    @Column(precision = 19, scale = 4)   
    private BigDecimal underUtilizationPercentThreshold;
    @Column(precision = 19, scale = 4)   
    private BigDecimal dailyOverdueFeePercent;
    @Column(precision = 19, scale = 4)   
    private BigDecimal unapprovedAdvacePercent;
    @Column(columnDefinition="Decimal(19,4) default '5.00'")   
    private BigDecimal vatPercent;

    private BigDecimal advancePercent; //95 percent 
    private BigDecimal advailabelBalance;
    
    private BigDecimal advanceLimit;  // Advance Limit in amounts 
    private String advanceLimitcurrency;
    private Long isActive;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updationAt;
    private String paymentCycle;
    private Boolean isRestricted = false;
    private Integer unapprovedInvoiceDueDateGap;
    private Boolean isRebate = false;
    private Integer minFeeDays;
    private Boolean isInvoiceVerifyOtpRequired = true;
    private Integer cashSweepPercentage;
    
    @Embedded
    @EqualsAndHashCode.Include
    VendorBuyerPlatformId vendorBuyerPlatformId;
    
    @Transient
    String page;
    @Transient
    String size;
    @Transient
    String sort;

    public FactParam(Map<String, Object> factParamEntry ) {
        //merchantId = (String) factParamEntry.get("merchantId");
        factParamOrderNum = (String) factParamEntry.get("factParamOrderNum");
        advanceLimitcurrency = (String) factParamEntry.get("advanceLimitcurrency");
        dailyFactFeePercent = BigDecimal.valueOf( Double.valueOf(String.valueOf(factParamEntry.get("dailyFactFeePercent"))) );        
        unapprovedDailyFeePercent = BigDecimal.valueOf( Double.valueOf(String.valueOf(factParamEntry.get("unapprovedDailyFeePercent"))) );        
        underUtilizationFeePercent = BigDecimal.valueOf( Double.valueOf(String.valueOf(factParamEntry.get("underUtilizationFeePercent"))) );        
        underUtilizationPercentThreshold = BigDecimal.valueOf( Double.valueOf(String.valueOf(factParamEntry.get("underUtilizationPercentThreshold"))) );        
        dailyOverdueFeePercent = BigDecimal.valueOf( Double.valueOf(String.valueOf(factParamEntry.get("dailyOverdueFeePercent"))) );
        unapprovedAdvacePercent = BigDecimal.valueOf( Double.valueOf(String.valueOf(factParamEntry.get("unapprovedAdvacePercent"))) );
        vatPercent = BigDecimal.valueOf( Double.valueOf(String.valueOf(factParamEntry.get("vatPercent"))) );
        advanceLimit = BigDecimal.valueOf( Double.valueOf(String.valueOf(factParamEntry.get("advanceLimit"))) );        
        advancePercent= BigDecimal.valueOf( Double.valueOf(String.valueOf(factParamEntry.get("advancePercent"))) ); 
        advailabelBalance= BigDecimal.valueOf( Double.valueOf(String.valueOf(factParamEntry.get("advailabelBalance"))) ); 
        createdAt = (Date) factParamEntry.get("createdAt");
        updationAt = (Date) factParamEntry.get("updationAt");
        paymentCycle=(String)factParamEntry.get("paymentCycle");
        vendorBuyerPlatformId = (VendorBuyerPlatformId)factParamEntry.get("merchantBuyerPlatformsId");
        isRestricted = (Boolean)factParamEntry.get("isRestricted");
        isRebate = (Boolean)factParamEntry.get("isRebate");
        isInvoiceVerifyOtpRequired = (Boolean)factParamEntry.get("isInvoiceVerifyOtpRequired");
        unapprovedInvoiceDueDateGap = (Integer)factParamEntry.get("unapprovedInvoiceDueDateGap");
        minFeeDays = (Integer)factParamEntry.get("minFeeDays");
        cashSweepPercentage = (Integer)factParamEntry.get("cashSweepPercentage");
    }
    
    public void update(Map<String, Object> factParamEntry) {        
        //merchantId = (String) ( factParamEntry.get("merchantId") == null? merchantId : factParamEntry.get("merchantId") );
        factParamOrderNum = (String) factParamEntry.get("factParamOrderNum");
        dailyFactFeePercent = BigDecimal.valueOf( Double.valueOf(String.valueOf((
                        factParamEntry.get("dailyFactFeePercent") == null ? dailyFactFeePercent: factParamEntry.get("dailyFactFeePercent")
                        ))) );                
        unapprovedDailyFeePercent = BigDecimal.valueOf( Double.valueOf(String.valueOf((
                factParamEntry.get("unapprovedDailyFeePercent") == null ? unapprovedDailyFeePercent: factParamEntry.get("unapprovedDailyFeePercent")
                ))) );                
        underUtilizationFeePercent = BigDecimal.valueOf( Double.valueOf(String.valueOf((
                        factParamEntry.get("underUtilizationFeePercent") == null ? underUtilizationFeePercent: factParamEntry.get("underUtilizationFeePercent")
                        ))) );                      
         underUtilizationPercentThreshold = BigDecimal.valueOf( Double.valueOf(String.valueOf((
                        factParamEntry.get("underUtilizationPercentThreshold") == null ? underUtilizationPercentThreshold: factParamEntry.get("underUtilizationPercentThreshold")
                        ))) );                
         dailyOverdueFeePercent = BigDecimal.valueOf( Double.valueOf(String.valueOf((
                        factParamEntry.get("dailyOverdueFeePercent") == null ? dailyOverdueFeePercent: factParamEntry.get("dailyOverdueFeePercent")
                        ))) );      
         unapprovedAdvacePercent = BigDecimal.valueOf( Double.valueOf(String.valueOf((
                 factParamEntry.get("unapprovedAdvacePercent") == null ? unapprovedAdvacePercent: factParamEntry.get("unapprovedAdvacePercent")
                 ))) );    
         vatPercent = BigDecimal.valueOf( Double.valueOf(String.valueOf((
                 factParamEntry.get("vatPercent") == null ? vatPercent: factParamEntry.get("vatPercent")
                 ))) );    

         advanceLimit = BigDecimal.valueOf( Double.valueOf(String.valueOf((
                        factParamEntry.get("advanceLimit") == null ? advanceLimit: factParamEntry.get("advanceLimit")
                        ))) );                       
        advancePercent = BigDecimal.valueOf( Double.valueOf(String.valueOf((
                        factParamEntry.get("advancePercent") == null ? advancePercent: factParamEntry.get("advancePercent")
                        ))) );                       
        advailabelBalance = BigDecimal.valueOf( Double.valueOf(String.valueOf((
                        factParamEntry.get("advailabelBalance") == null ? advailabelBalance: factParamEntry.get("advailabelBalance")
                        ))) );                       
          
        createdAt = (Date) (factParamEntry.get("createdAt") == null? createdAt : factParamEntry.get("createdAt"));
        updationAt = (Date) (factParamEntry.get("updationAt") == null? updationAt : factParamEntry.get("updationAt"));
        advanceLimitcurrency=(String) factParamEntry.get("advanceLimitcurrency");
        isRestricted = (Boolean)factParamEntry.get("isRestricted");
        isRebate = (Boolean)factParamEntry.get("isRebate");
        isInvoiceVerifyOtpRequired = (Boolean)factParamEntry.get("isInvoiceVerifyOtpRequired");
        unapprovedInvoiceDueDateGap = (Integer)factParamEntry.get("unapprovedInvoiceDueDateGap");
        minFeeDays = (Integer)factParamEntry.get("minFeeDays");
        cashSweepPercentage = (Integer)factParamEntry.get("cashSweepPercentage");
    }
    
//  public void updatejson(FactParam factParamEntry) {
//      
//      if (factParamEntry != null) {
//        
//        this.factParamOrderNum = (String) (factParamEntry.getFactParamOrderNum() == null? this.factParamOrderNum : factParamEntry.getFactParamOrderNum());
//        this.dailyFactFeePercent = (BigDecimal) (factParamEntry.getDailyFactFeePercent() == null ? this.dailyFactFeePercent: factParamEntry.getDailyFactFeePercent());                
//        this.unapprovedDailyFeePercent = (BigDecimal) (factParamEntry.getUnapprovedDailyFeePercent() == null ? this.unapprovedDailyFeePercent: factParamEntry.getUnapprovedDailyFeePercent());                
//        this.underUtilizationFeePercent = (BigDecimal) (factParamEntry.getUnderUtilizationFeePercent() == null ? this.underUtilizationFeePercent: factParamEntry.getUnderUtilizationFeePercent());                      
//        this.underUtilizationPercentThreshold = (BigDecimal) (factParamEntry.getUnderUtilizationPercentThreshold() == null ? this.underUtilizationPercentThreshold: factParamEntry.getUnderUtilizationPercentThreshold());                
//        this.dailyOverdueFeePercent = (BigDecimal) (factParamEntry.getDailyOverdueFeePercent() == null ? this.dailyOverdueFeePercent: factParamEntry.getDailyOverdueFeePercent());
//        this.unapprovedAdvacePercent = (BigDecimal) (factParamEntry.getUnapprovedAdvacePercent() == null ? this.unapprovedAdvacePercent: factParamEntry.getUnapprovedAdvacePercent());
//        this.vatPercent = (BigDecimal) (factParamEntry.getVatPercent() == null ? this.vatPercent: factParamEntry.getVatPercent());
//        this.advanceLimit = (BigDecimal) (factParamEntry.getAdvanceLimit() == null ? this.advanceLimit: factParamEntry.getAdvanceLimit());
//        this.advancePercent = (BigDecimal) (factParamEntry.getAdvancePercent() == null ? this.advancePercent: factParamEntry.getAdvancePercent());                       
//        this.advailabelBalance = (BigDecimal) (factParamEntry.getAdvailabelBalance() == null ? this.advailabelBalance: factParamEntry.getAdvailabelBalance());                       
//        
//        this.createdAt = (Date) (factParamEntry.getCreatedAt() == null? this.createdAt : factParamEntry.getCreatedAt());
//        this.updationAt = (Date) (factParamEntry.getUpdationAt() == null? this.updationAt : factParamEntry.getUpdationAt());
//        this.advanceLimitcurrency= (String) (factParamEntry.getAdvanceLimitcurrency() == null ? this.advanceLimitcurrency : factParamEntry.getAdvanceLimitcurrency());
//        this.paymentCycle=(String) (factParamEntry.getPaymentCycle()==null? this.paymentCycle:factParamEntry.getPaymentCycle());
//        this.isRestricted=(Boolean) (factParamEntry.getIsRestricted()==null? this.isRestricted:factParamEntry.getIsRestricted());
//        this.isRebate=(Boolean) (factParamEntry.getIsRebate()==null? this.isRebate:factParamEntry.getIsRebate());
//        this.isInvoiceVerifyOtpRequired = (Boolean) (factParamEntry.getIsInvoiceVerifyOtpRequired()==null? this.isInvoiceVerifyOtpRequired:factParamEntry.getIsInvoiceVerifyOtpRequired());
//        this.unapprovedInvoiceDueDateGap=(Integer) (factParamEntry.getUnapprovedInvoiceDueDateGap()==null? this.unapprovedInvoiceDueDateGap:factParamEntry.getUnapprovedInvoiceDueDateGap());
//        this.minFeeDays=(Integer) (factParamEntry.getMinFeeDays()==null? this.minFeeDays:factParamEntry.getMinFeeDays());
//        this.cashSweepPercentage=(Integer) (factParamEntry.getCashSweepPercentage()==null? this.cashSweepPercentage:factParamEntry.getCashSweepPercentage());
//      }
//    }  
    
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof FactParam))
      return false;
    FactParam factParam = (FactParam) o;
    return Objects.equals(this.id, factParam.id) && 
            Objects.equals(this.vendorBuyerPlatformId.vendorId, factParam.vendorBuyerPlatformId.vendorId)&&
            Objects.equals(this.factParamOrderNum, factParam.factParamOrderNum);
  }
  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.vendorBuyerPlatformId.vendorId, this.factParamOrderNum);
  }

  @Override
  public String toString() {
    return "FactParam  {" + "Id=" + this.id +
            ", vendorId='" + this.vendorBuyerPlatformId.vendorId +
            '\'' + ", factParamOrderNum='" + this.factParamOrderNum + '\'' +            
            '}';
  }
}

