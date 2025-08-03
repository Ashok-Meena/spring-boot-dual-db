package com.ashokjeph.dualdb.repository.primary;

import com.ashokjeph.dualdb.entity.primary.PswdConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported=false,collectionResourceRel = "pswdconfig", path = "pswdconfig")
public interface PswdConfigRepository extends JpaRepository<PswdConfig, Long> {
    PswdConfig findTopByIsActive(Boolean isActive);
}
