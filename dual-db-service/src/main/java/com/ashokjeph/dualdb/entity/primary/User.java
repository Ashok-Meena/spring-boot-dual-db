package com.ashokjeph.dualdb.entity.primary;

import com.ashokjeph.dualdb.enums.LoginLockStatus;
import com.ashokjeph.dualdb.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Entity
@Data
@ToString
@NoArgsConstructor
@Table(name = "app_user", schema = "dbo")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 50)
    private String id;

    //make unique
    @Column(unique = true, length = 50)
//    @Size(min = 6, max = 15)
    private String phone;


    //add constraints for length
    private String name;


    //make unique
    @Column(name = "email", nullable = false, length = 255) //    @Size(min = 3, max = 240)
    private String email;

    @JsonIgnore
    private String password;

    /*
    @ElementCollection(targetClass = RoleEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role_id")
    private Set<RoleEnum> roles;
    */
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "user_role_id", referencedColumnName = "id")
    private UserRole userRole;

    private String phoneCountry;
    private UserStatus status;

    @CreationTimestamp
    private Date creationTimestamp;
    @UpdateTimestamp
    private Date updationTimestamp;

    //Dated 11-07-2023 By Nama NK
    private Boolean emailVerified;
    private Boolean phoneVerified;
    private String parentId;
    private Date pswdExpiry;
    private Date lastLogin;

    //fields added for locked password flow
    private Date lastFailedLoginAttempt;
    private Integer LoginFailedCount;
    private Integer OTPFailedCount;
    private LoginLockStatus lockStatus;
    private Long uniqueId;

    @Transient
    private String token;
    @Transient
    private Long tokenExpiryMinute;
    @Transient
    private Date loginTime;

    @Transient
    private String  accountStatus;

    @Transient
    private String[] roles = new String[1];

    @Transient
    private Long roleId;
    public Long getRoleId() {
        return Objects.nonNull(userRole) ? userRole.getRole().getId() : null;
    }

    public String[] getRoles(){
        roles[0] = Objects.nonNull(role) ? role.getAbbreviation() : "";
        return roles;
    }
    @Transient
    List<Map<String, String>> allowedActions;
    @Transient
    Map<String, String> subUser;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public String getPhoneCountry() {
		return phoneCountry;
	}

	public void setPhoneCountry(String phoneCountry) {
		this.phoneCountry = phoneCountry;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public Date getUpdationTimestamp() {
		return updationTimestamp;
	}

	public void setUpdationTimestamp(Date updationTimestamp) {
		this.updationTimestamp = updationTimestamp;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public Boolean getPhoneVerified() {
		return phoneVerified;
	}

	public void setPhoneVerified(Boolean phoneVerified) {
		this.phoneVerified = phoneVerified;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Date getPswdExpiry() {
		return pswdExpiry;
	}

	public void setPswdExpiry(Date pswdExpiry) {
		this.pswdExpiry = pswdExpiry;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Date getLastFailedLoginAttempt() {
		return lastFailedLoginAttempt;
	}

	public void setLastFailedLoginAttempt(Date lastFailedLoginAttempt) {
		this.lastFailedLoginAttempt = lastFailedLoginAttempt;
	}

	public Integer getLoginFailedCount() {
		return LoginFailedCount;
	}

	public void setLoginFailedCount(Integer loginFailedCount) {
		LoginFailedCount = loginFailedCount;
	}

	public Integer getOTPFailedCount() {
		return OTPFailedCount;
	}

	public void setOTPFailedCount(Integer oTPFailedCount) {
		OTPFailedCount = oTPFailedCount;
	}

	public LoginLockStatus getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(LoginLockStatus lockStatus) {
		this.lockStatus = lockStatus;
	}

	public Long getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(Long uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getTokenExpiryMinute() {
		return tokenExpiryMinute;
	}

	public void setTokenExpiryMinute(Long tokenExpiryMinute) {
		this.tokenExpiryMinute = tokenExpiryMinute;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	public List<Map<String, String>> getAllowedActions() {
		return allowedActions;
	}

	public void setAllowedActions(List<Map<String, String>> allowedActions) {
		this.allowedActions = allowedActions;
	}

	public Map<String, String> getSubUser() {
		return subUser;
	}

	public void setSubUser(Map<String, String> subUser) {
		this.subUser = subUser;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public User(Map<String, Object> userModal) {
        name = (String) userModal.get("name");
        phone = (String) userModal.get("phone");
        phoneCountry = (String) userModal.get("phoneCountry");
        email = (String) userModal.get("email");
        password = (String) userModal.get("password");
        accountStatus = (String) userModal.get("accountStatus");
        status = UserStatus.valueOf((String) userModal.get("status"));
        parentId = (String) userModal.get("parentId");
        pswdExpiry = (Date) userModal.get("pswdExpiry");
        lastLogin = (Date) userModal.get("lastLogin");

        emailVerified = (Boolean) userModal.getOrDefault ("emailVerified",false);
        phoneVerified = (Boolean) userModal.getOrDefault ("phoneVerified",false);
    }

    public void update(Map<String, Object> userModal) {
        name = (String) (userModal.get("name") == null ? name : userModal.get("name"));
        phone = (String) (userModal.get("phone") == null ? phone : userModal.get("phone"));
        phoneCountry = (String) (userModal.get("phoneCountry") == null ? phoneCountry : userModal.get("phoneCountry"));

        email = (String) (userModal.get("email") == null ? email : userModal.get("email"));
        password = (String) (userModal.get("password") == null ? password : userModal.get("password"));
        accountStatus = (String) (userModal.get("accountStatus") == null ? accountStatus : userModal.get("accountStatus"));
        status = UserStatus.valueOf((String) (userModal.get("status") == null ? status : userModal.get("status")));
        parentId = (String) (userModal.get("parentId") == null ? parentId : userModal.get("parentId"));
        //added on 10/4/25 (converting enum to obj)
//        Object statusObj = userModal.get("status");
//        if (statusObj != null) {
//            if (statusObj instanceof String) {
//                status = UserStatus.valueOf((String) statusObj);
//            } else if (statusObj instanceof Integer) {
//                int ordinal = (Integer) statusObj;
//                UserStatus[] values = UserStatus.values();
//                if (ordinal >= 0 && ordinal < values.length) {
//                    status = values[ordinal];
//                } else {
//                    throw new IllegalArgumentException("Invalid status ordinal: " + ordinal);
//                }
//            } else {
//                throw new IllegalArgumentException("Invalid status value type: " + statusObj.getClass().getName());
//            }
//        }
        pswdExpiry = (Date) (userModal.get("pswdExpiry") == null ? pswdExpiry : userModal.get("pswdExpiry"));
        lastLogin = (Date) (userModal.get("lastLogin") == null ? lastLogin : userModal.get("lastLogin"));

        emailVerified = (Boolean) userModal.getOrDefault ("emailVerified",emailVerified);
        phoneVerified = (Boolean) userModal.getOrDefault ("phoneVerified",phoneVerified);
    }

    public User orElse(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public User(String id) {
        this.id = id;
    }
}
