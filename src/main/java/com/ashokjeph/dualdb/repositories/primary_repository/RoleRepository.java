package com.ashokjeph.dualdb.repositories.primary_repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ashokjeph.dualdb.entity.primary.Role;

@RepositoryRestResource(exported=false,collectionResourceRel = "role", path = "role")
public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findTopByAbbreviation(String abbreviation);
	@Query("SELECT COUNT(*) FROM Role WHERE abbreviation LIKE :displayId%")
	Integer getRecordCountForDisplayId(@Param("displayId") String displayId);
}
