package com.ashokjeph.dualdb.repositories.secondary_repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ashokjeph.dualdb.entity.secontary.VendorRpaInvoicesEntity;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface VendorRpaInvoiceRepository extends JpaRepository<VendorRpaInvoicesEntity, Long> {
//    Optional<VendorRpaInvoicesEntity> findByBuyerPlatformIdAndVendorIdAndInvoiceNumber(Long buyerPlatformId, String vendorId, String invoiceNumber);

    Optional<VendorRpaInvoicesEntity> findByVendorBuyerPlatformId_BuyerPlatformIdAndVendorBuyerPlatformId_VendorIdAndInvoiceNumber(
            Long buyerPlatformId,
            String vendorId,
            String invoiceNumber
    );

    @Query("SELECT a FROM VendorRpaInvoicesEntity a WHERE a.invoiceCreationDate BETWEEN :fromDate AND :toDate")
    Page<VendorRpaInvoicesEntity> findInvoices(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate, Pageable pageable);

}

