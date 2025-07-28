package com.ashokjeph.dualdb.repositories.secondary_repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ashokjeph.dualdb.entity.secontary.CurrencyRate;

import java.util.Date;

@RepositoryRestResource(exported=false,collectionResourceRel = "currencyrate", path = "currencyrate")
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
	CurrencyRate findByBaseCurrencyAndTargetCurrencyAndDate(String baseCurrency, String argetCurrency, Date date);
}
