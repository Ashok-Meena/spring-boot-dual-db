package com.ashokjeph.dualdb.repositories.primary_repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ashokjeph.dualdb.entity.primary.PswdHistory;

import java.util.List;

@RepositoryRestResource(exported=false,collectionResourceRel = "pswdhistory", path = "pswdhistory")
public interface PswdHistoryRepository extends JpaRepository<PswdHistory, Long> {
    List<PswdHistory> findAllByUserIdOrderByIdDesc(String userId);
}
