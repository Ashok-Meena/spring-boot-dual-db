package com.ashokjeph.dualdb.repository.primary;

import com.ashokjeph.dualdb.entity.primary.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported=false,collectionResourceRel = "role", path = "role")
public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findTopByAbbreviation(String abbreviation);
	@Query("SELECT COUNT(*) FROM Role WHERE abbreviation LIKE :displayId%")
	Integer getRecordCountForDisplayId(@Param("displayId") String displayId);
}
