package com.ashokjeph.dualdb.config.entity.secondary;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ashokjeph.dualdb.enums.ExistenceType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BuyerPlatform implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;
    //blob file for logo
    @OneToOne
    private DBFile logo;
    private String name;
    private String identifier;
    private String typeDescription;
    private boolean isActive;
    private long linkedWith;
    private String emailTo;
    private String emailCC;
    private String logoPath;
    private String vertical;
    
    private String authPersonName;
    private String authPersonDesignation;
    private String authPersonPhoneNumber;
    // added on 29/06/2022
    private String CEDERRoseRating;
    private String companyBackground;
    private String vaDescription;
    
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="buyerPlatform")
	private List<AdditionalDocument> additionalDocuments=new ArrayList<AdditionalDocument>();
    
    @CreationTimestamp
    private Date creationTimestamp;
    @UpdateTimestamp
    private Date updationTimestamp;
    // added on 07/02/2023
    private String affiliateProductId;
    private ExistenceType existenceType;
    private Boolean exemptConcLimit;
    private String toolTip;
    private Integer maxApprovalLevel;
    private FlowType flowType;

    @Transient
    String page;
    @Transient
    String size;
    @Transient
    String sort;

    public BuyerPlatform(Long id) {
    	this.id = id;
    }
    public BuyerPlatform(String name, String typeDescription, boolean isActive, String identifier, long linkedWith,
                         String emailTo, String emailCC, String vertical, String authPersonName, String authPersonDesignation, String authPersonPhoneNumber,
                         String CEDERRoseRating, String companyBackground, String vaDescription, List<AdditionalDocument> additionalDocuments, String affiliateProductId,
                         ExistenceType existenceType, Boolean exemptConcLimit, String toolTip, Integer maxApprovalLevel, FlowType flowType ) {
        this.name = name;
        this.identifier = identifier;
        this.typeDescription = typeDescription;
        this.isActive = isActive;
        this.linkedWith =linkedWith;
        this.emailTo = emailTo;
        this.emailCC = emailCC;
        this.vertical = vertical;
        this.authPersonName = authPersonName;
        this.authPersonDesignation = authPersonDesignation;
        this.authPersonPhoneNumber = authPersonPhoneNumber;
        // added on 29/06/2022
        this.CEDERRoseRating = CEDERRoseRating;
        this.companyBackground = companyBackground;
        this.additionalDocuments = additionalDocuments;
        this.vaDescription= vaDescription; 
        this.affiliateProductId = affiliateProductId;
        this.existenceType = existenceType;
        this.exemptConcLimit = exemptConcLimit;
        this.toolTip = toolTip;

        this.maxApprovalLevel = maxApprovalLevel;
        this.flowType = flowType;
    }

    public void update(Map<String, Object> buyerPlatformModel) {
        EntityMapper.mapModel(this, buyerPlatformModel);
    }

    public enum FlowType {SEQUENTIAL,PARALLEL};
}
