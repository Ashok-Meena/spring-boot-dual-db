package com.ashokjeph.dualdb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import com.ashokjeph.dualdb.entity.primary.User;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {

    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
//        config.exposeIdsFor(BuyerPlatform.class);
        config.exposeIdsFor(User.class);
//        config.exposeIdsFor(Company.class);
//        config.exposeIdsFor(Bank.class);
//        config.exposeIdsFor(DocumentInfo.class);
//        config.exposeIdsFor(Document.class);
    }
}