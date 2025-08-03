package com.ashokjeph.dualdb.entity.primary;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TokenRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 50)
	String id;

	@Column(length = 2048, nullable = false)
	String token;

	@Column(nullable = false)
	String userId;

	@Column(name = "token_issue_date", nullable = false)
	Date tokenIssueDate;

	@Column(name = "token_expiry_date", nullable = false)
	Date tokenExpiryDate;

	@Column(nullable = false)
	String status;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getTokenIssueDate() {
		return tokenIssueDate;
	}

	public void setTokenIssueDate(Date tokenIssueDate) {
		this.tokenIssueDate = tokenIssueDate;
	}

	public Date getTokenExpiryDate() {
		return tokenExpiryDate;
	}

	public void setTokenExpiryDate(Date tokenExpiryDate) {
		this.tokenExpiryDate = tokenExpiryDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
