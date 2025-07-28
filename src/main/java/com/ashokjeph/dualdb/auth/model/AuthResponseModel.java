package com.ashokjeph.dualdb.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseModel {

    private String accessToken;
    private String refreshToken;
}
