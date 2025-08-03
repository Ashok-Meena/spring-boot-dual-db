package com.ashokjeph.dualdb.repository.secondary;

import com.ashokjeph.dualdb.config.entity.secondary.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;

@RepositoryRestResource(exported=false,collectionResourceRel = "currencyrate", path = "currencyrate")
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
	CurrencyRate findByBaseCurrencyAndTargetCurrencyAndDate(String baseCurrency, String argetCurrency, Date date);
}
