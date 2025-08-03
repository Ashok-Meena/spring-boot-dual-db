package com.ashokjeph.dualdb.config.entity.secondary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalDocument implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

	@ManyToOne(targetEntity= Merchant.class)
	@JoinColumn(name="merchant_id",referencedColumnName="id",nullable=true,updatable=true)
    @JsonIgnore 
	private Merchant merchant;
    
    @ManyToOne(targetEntity= BuyerPlatform.class)
    @JoinColumn(name = "buyer_platform_id", referencedColumnName="id",nullable = true, updatable = true)
    @JsonIgnore
    private BuyerPlatform buyerPlatform;

    String documentName;
    String documentPath;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    Date docExpiryDate;
    String status;
    
    @CreationTimestamp
    Date creationTimestamp;

    @UpdateTimestamp
    Date updationTimestamp;

    @Transient
    MultipartFile file;
}
