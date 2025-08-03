package com.ashokjeph.dualdb.repository.primary;

import com.ashokjeph.dualdb.entity.primary.TokenRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported=false,collectionResourceRel = "tokenrecord", path = "tokenrecord")
public interface TokenRecordRepository extends JpaRepository<TokenRecord, String> {
	List<TokenRecord> findAllByTokenExpiryDateLessThan(Date currentDate);
	TokenRecord findTopByUserIdOrderByTokenIssueDateDesc(String userId);
	List<TokenRecord> findAllByUserIdAndTokenExpiryDateGreaterThan(String userId, Date currentDate);
//	TokenRecord findByToken(String token);


	Optional<TokenRecord> findByToken(String token);
	List<TokenRecord> findByUserId(String userId);
}
