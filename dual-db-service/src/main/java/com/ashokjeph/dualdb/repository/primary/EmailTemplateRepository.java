package com.ashokjeph.dualdb.repository.primary;

import com.ashokjeph.dualdb.entity.primary.EmailTemplates;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@RepositoryRestResource(exported=false,collectionResourceRel = "emailtemplate", path = "emailtemplate")
public interface EmailTemplateRepository extends JpaRepository<EmailTemplates, Long>{
    public EmailTemplates findTopByActivityAndEventAndStatus(String activity, String event, String status);
    @Query("select c from EmailTemplates c where (c.activity = :#{#cp.activity} or :#{#cp.activity} is null) And "
			 + " (c.status = :#{#cp.status} or :#{#cp.status} is null) And "
			 + " (c.subject = :#{#cp.subject} or :#{#cp.subject} is null) And "
			 + " (c.event = :#{#cp.event} or :#{#cp.event} is null)  " )
	Page<EmailTemplates> findAsParam(@Param("cp") EmailTemplates cp,Pageable pageable );
}
