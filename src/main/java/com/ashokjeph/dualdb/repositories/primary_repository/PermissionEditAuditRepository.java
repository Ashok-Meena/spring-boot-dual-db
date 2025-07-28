package com.ashokjeph.dualdb.repositories.primary_repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ashokjeph.dualdb.entity.primary.PermissionEditAudit;

@RepositoryRestResource(exported = false, collectionResourceRel = "permissioneditaudit", path = "permissioneditaudit")
public interface PermissionEditAuditRepository extends JpaRepository<PermissionEditAudit, Long> {
}
