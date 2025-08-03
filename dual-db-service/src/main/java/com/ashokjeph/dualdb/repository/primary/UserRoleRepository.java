package com.ashokjeph.dualdb.repository.primary;

import com.ashokjeph.dualdb.entity.primary.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported=false,collectionResourceRel = "userrole", path = "userrole")
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	UserRole findByUserId(String userId);
	List<UserRole> findByRoleId(String roleId);
}
