package com.ashokjeph.dualdb.config.entity.secondary;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.ashokjeph.dualdb.enums.ProfileStatus;

@Entity
@Data
@NoArgsConstructor
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Include
    Merchant merchant;
    String tradeLicenseNum;
    String tradeName;
    String address1, address2, address3;
    String taxNum;
    String authSignName;
    Boolean iAmAuth;
    String designation;
    String contact;
    String email;
    ProfileStatus profileStatus;
    Integer yearsInBusiness;
    String monthYearIncopration;

    String authSignatoryNationality;
    Date authSignatoryDOB;
    @SneakyThrows
    public Company(Map<String, Object> companyModel) {
        if (companyModel.get("id") != null)
            id = Long.valueOf(companyModel.get("id") + "");
        tradeLicenseNum = (String) companyModel.get("tradeLicenseNum");
        tradeName = (String) companyModel.get("tradeName");
        address1 = (String) companyModel.get("address1");
        address2 = (String) companyModel.get("address2");
        address3 = (String) companyModel.get("address3");
        taxNum = (String) companyModel.get("taxNum");
        authSignName = (String) companyModel.get("authSignName");
        iAmAuth = (Boolean) (companyModel.get("iAmAuth") == null ? false : companyModel.get("iAmAuth"));
        designation = (String) companyModel.get("designation");
        contact = (String) companyModel.get("contact");
        email = (String) companyModel.get("email");
        profileStatus = ProfileStatus.INCOMPLETE;
        yearsInBusiness = (Integer) companyModel.get("yearsInBusiness");
        monthYearIncopration = (String) companyModel.get("monthYearIncopration");
        authSignatoryNationality = (String) companyModel.get("authSignatoryNationality");
        authSignatoryDOB = (Date) companyModel.get("authSignatoryDOB");
    }

    public void validate() {
        if (!((tradeLicenseNum == null || "".equals(tradeLicenseNum)) || (tradeName == null || "".equals(tradeName)) ||
                (address1 == null || "".equals(address1)) || (taxNum == null || "".equals(taxNum)) ||
                (authSignName == null || "".equals(authSignName)) || (!iAmAuth) ||
                (designation == null || "".equals(designation)))) {
            profileStatus = ProfileStatus.COMPLETED;
        } else
            profileStatus = ProfileStatus.INCOMPLETE;
    }

    public void update(Map<String, Object> companyModel) {
        EntityMapper.mapModel(this, companyModel);
    }
}
