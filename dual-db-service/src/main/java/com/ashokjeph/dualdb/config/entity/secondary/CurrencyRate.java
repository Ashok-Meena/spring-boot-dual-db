package com.ashokjeph.dualdb.config.entity.secondary;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CurrencyRate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Date date;
    String baseCurrency;
    String targetCurrency;
    @Column(precision = 19, scale = 6)
    BigDecimal rate = BigDecimal.ZERO;

    @CreationTimestamp
    Date createdOn;
}
