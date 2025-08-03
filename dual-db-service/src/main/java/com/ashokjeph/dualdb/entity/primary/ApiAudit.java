package com.ashokjeph.dualdb.entity.primary;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ApiAudit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @CreationTimestamp
    Date createdOn;

    Date requestLogDate;
    String userId;
    String userEmail;
    String role;
    Date tokenIssue;
    Date tokenExpiry;
    String apiPath;
    String requestPlatform;
    String requestUserAgent;
    String requestMethod;
    @Column(columnDefinition = "TEXT")
    String requestParam;
    @Column(columnDefinition = "TEXT")
    String requestBody;
    @Column(columnDefinition = "TEXT")
    String response;
    Integer responseStatus;
}
