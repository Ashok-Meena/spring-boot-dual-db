package com.ashokjeph.dualdb.entity.primary;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OtpRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @CreationTimestamp
    private Date createdAt;

    private String uuId;
    private String phone;
    private String userId;
    private String email;
    private String otp;
    private String event;
    private Date validity;
    private String status;
}

