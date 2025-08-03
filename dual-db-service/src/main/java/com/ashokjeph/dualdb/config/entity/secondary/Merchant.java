package com.ashokjeph.dualdb.config.entity.secondary;

import com.ashokjeph.dualdb.entity.primary.Role;
import com.ashokjeph.dualdb.entity.primary.User;
import com.ashokjeph.dualdb.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Merchant implements Serializable {
	private static final long serialVersionUID = 1L;

	//make save merchant api
    @Id
    @Column(length = 50)
    @EqualsAndHashCode.Include
    String id;

//    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    User user;

    @OneToOne(mappedBy = "merchant", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("merchant")
    Company company;

    @OneToMany(mappedBy = "merchant", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("merchant")
    List<Bank> bank;

    @OneToMany(mappedBy = "merchant")
    @JsonIgnoreProperties("merchant")
    Set<VendorBuyerPlatforms> buyerPlatforms;

    ProfileStatus documentStatus;
    ProfileStatus bankStatus;
    
    String displayId;
    String merchantCategory;
    String accountStatus;
    String rating;
    Integer mailStatus;
    String microsoftDynamicsId;
    String leadOwner;
    
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="merchant")
	private List<AdditionalDocument> additionalDocuments=new ArrayList<AdditionalDocument>();

    @Transient
    String subMerchantId;
    @Transient
    Role subMerchantRole;

    @Transient
    String page;
    @Transient
    String size;
    @Transient
    String sort;
    
//    public Merchant(User user) {
//        if (user == null)
//            throw new NullPointerException("User can't be null");
//        this.user = user;
//        this.id = user.getId();
//    }

    public Merchant(String id) {
        this.id = id;
    }

    public Merchant orElse(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
//    public ProfileStatus getBankStatus() {
//    	decideBankStatus();
//    	return this.bankStatus;
//    }
//    
//    public void setBankStatus(ProfileStatus bankStatus) {
//    	this.bankStatus = bankStatus;
//    	decideBankStatus();
//    }
    
//    private void decideBankStatus() {
//    	if(this.bank != null && this.bank.size() > 0) {
//    		this.bankStatus = ProfileStatus.VERIFICATION_PENDING;
//    		for(Bank bk : bank) {
//				if(bk.getProfileStatus().equals(ProfileStatus.VERIFIED)) {
//					this.bankStatus = bk.getProfileStatus();
//    				break;
//    			}
//    		}
//    		if(this.bankStatus.equals(ProfileStatus.VERIFICATION_PENDING)) {
//        		for(Bank bk : bank) {
//    				if(bk.getProfileStatus().equals(ProfileStatus.VERIFICATION_INITIATED)) {
//    					this.bankStatus = bk.getProfileStatus();
//        				break;
//        			}
//        		}
//    		}
//    	}
//    }
}
