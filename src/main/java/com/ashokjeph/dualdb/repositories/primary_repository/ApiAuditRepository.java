package com.ashokjeph.dualdb.repositories.primary_repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ashokjeph.dualdb.entity.primary.ApiAudit;

@RepositoryRestResource(exported=false,collectionResourceRel = "apiaudit", path = "apiaudit")
public interface ApiAuditRepository extends JpaRepository<ApiAudit, Long> {
}
