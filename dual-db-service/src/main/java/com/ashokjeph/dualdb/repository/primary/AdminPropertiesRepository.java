package com.ashokjeph.dualdb.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ashokjeph.dualdb.entity.primary.AdminProperties;

@RepositoryRestResource(exported=false,collectionResourceRel = "adminproperties", path = "adminproperties")
public interface AdminPropertiesRepository extends JpaRepository<AdminProperties, Long> {
	AdminProperties findTopByParamDesc(String paramDesc);
}
