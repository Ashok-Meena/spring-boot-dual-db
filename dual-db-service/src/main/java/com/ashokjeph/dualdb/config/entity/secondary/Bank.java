package com.ashokjeph.dualdb.config.entity.secondary;

import com.ashokjeph.dualdb.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Bank implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Include
    @JsonIgnore
    Merchant merchant;

//    @NotNull
    String accountNum;
    String ibanNum, bankName, branchName;
    String swiftCode;
    ProfileStatus profileStatus;
    BigDecimal verificationAmount;
    String templateId;
    String paymentMethod;

    public Bank(Map<String, Object> bankModel) {
        if (bankModel.get("id") != null)
            id = Long.valueOf(bankModel.get("id") + "");
        accountNum = (String) bankModel.get("accountNum");
        ibanNum = (String) bankModel.get("ibanNum");
        bankName = (String) bankModel.get("bankName");
        branchName = (String) bankModel.get("branchName");
        swiftCode = (String) bankModel.get("swiftCode");
        templateId = (String) bankModel.get("templateId");
        paymentMethod = (String) bankModel.get("paymentMethod");
    }

    public void update(Map<String, Object> bankModel) {
        EntityMapper.mapModel(this, bankModel);
    }

    public void validate() {
        if (accountNum == null || "".equals(accountNum) || ibanNum == null || "".equals(ibanNum) ||
                bankName == null || "".equals(bankName) || branchName == null || "".equals(branchName)) {
            profileStatus = ProfileStatus.INCOMPLETE;
        } else
            profileStatus = ProfileStatus.VERIFICATION_PENDING;
    }
}
