package com.ashokjeph.dualdb.repositories.primary_repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ashokjeph.dualdb.entity.primary.OtpRecords;

import java.util.List;

@RepositoryRestResource(exported=false,collectionResourceRel = "otprecords", path = "otprecords")
public interface OtpRecordsRepository extends JpaRepository<OtpRecords, Long> {
    OtpRecords findFirstByEmailAndEventAndStatusOrderByCreatedAtDesc(String email, String event, String status);
    OtpRecords findFirstByUuIdAndOtp(String uuid,String otp);
    OtpRecords findFirstByUuId(String uuid);
    OtpRecords findFirstByPhoneAndEventAndStatusOrderByCreatedAtDesc(String phone,String event,String status);
    List<OtpRecords> findAllByUserIdAndEventAndStatus(String userId, String event, String status);
}
