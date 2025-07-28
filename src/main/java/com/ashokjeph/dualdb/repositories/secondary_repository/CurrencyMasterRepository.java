package com.ashokjeph.dualdb.repositories.secondary_repository;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ashokjeph.dualdb.entity.secontary.CurrencyMaster;

@RepositoryRestResource(exported=false,collectionResourceRel = "currencymaster", path = "currencymaster")
public interface CurrencyMasterRepository extends JpaRepository<CurrencyMaster, Long> {
    boolean existsByCurrencyCode(@NotBlank String invoiceCurrency);
}
