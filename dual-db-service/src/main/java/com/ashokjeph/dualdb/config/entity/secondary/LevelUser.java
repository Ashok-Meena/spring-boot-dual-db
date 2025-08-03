package com.ashokjeph.dualdb.config.entity.secondary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@Table(uniqueConstraints=@UniqueConstraint(columnNames={"approvalLevelId","supplierName", "userId"}))
@Entity
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LevelUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long approvalLevelId;
    String userId;
    String supplierName;

    @CreationTimestamp @JsonIgnore
    Date createdOn;
    @UpdateTimestamp @JsonIgnore
    Date updatedOn;

    @Transient
    String page;
    @Transient
    String size;
    @Transient
    String sort;
}
