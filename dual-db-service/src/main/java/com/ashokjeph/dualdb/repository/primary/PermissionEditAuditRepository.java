package com.ashokjeph.dualdb.repository.primary;

import com.ashokjeph.dualdb.entity.primary.PermissionEditAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false, collectionResourceRel = "permissioneditaudit", path = "permissioneditaudit")
public interface PermissionEditAuditRepository extends JpaRepository<PermissionEditAudit, Long> {
}
