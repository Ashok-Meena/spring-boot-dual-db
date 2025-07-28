package com.ashokjeph.dualdb.squid_admin.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RefreshTokenRequestModel {
        @NotBlank
        private String userId;
        @NotBlank
        private String oldToken;

}
