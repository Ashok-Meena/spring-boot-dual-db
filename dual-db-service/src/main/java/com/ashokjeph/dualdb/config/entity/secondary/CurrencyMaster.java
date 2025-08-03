package com.ashokjeph.dualdb.config.entity.secondary;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CurrencyMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long currencyId;
     
    @Column(length = 100)
    private String  currencyName  ;    // United Arab Emirates Dirham
    
    @Column(unique = true, length = 5)    
    private String  currencyCode  ;    //AED

    @Column(length = 20)
    private String  currencyShortName  ;    //Dirham 

}
