package com.ashokjeph.dualdb.repository.secondary;

import com.ashokjeph.dualdb.config.entity.secondary.CurrencyMaster;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported=false,collectionResourceRel = "currencymaster", path = "currencymaster")
public interface CurrencyMasterRepository extends JpaRepository<CurrencyMaster, Long> {
    boolean existsByCurrencyCode(@NotBlank String invoiceCurrency);
}
