package com.ashokjeph.dualdb.repository.primary;

import com.ashokjeph.dualdb.entity.primary.PswdHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported=false,collectionResourceRel = "pswdhistory", path = "pswdhistory")
public interface PswdHistoryRepository extends JpaRepository<PswdHistory, Long> {
    List<PswdHistory> findAllByUserIdOrderByIdDesc(String userId);
}
