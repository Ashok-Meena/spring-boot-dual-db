package com.ashokjeph.dualdb.auth.model;

import lombok.Data;

@Data
public class LoginRequestModel {
    private String email;
    private String password;
    private Object expiryMinute;
}

