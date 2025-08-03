package com.ashokjeph.dualdb.entity.primary;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class PermissionEditAudit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
	String permissionType;
    Long menuId;
    Long menuUserAccessId;
    Long roleId;
    String permission;
    String permissionDesc;
    String updatedBy;
    String userId;
    @CreationTimestamp
    Date updatedAt;
}
