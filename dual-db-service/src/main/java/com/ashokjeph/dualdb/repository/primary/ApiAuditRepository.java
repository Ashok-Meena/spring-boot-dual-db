package com.ashokjeph.dualdb.repository.primary;

import com.ashokjeph.dualdb.entity.primary.ApiAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported=false,collectionResourceRel = "apiaudit", path = "apiaudit")
public interface ApiAuditRepository extends JpaRepository<ApiAudit, Long> {
}
