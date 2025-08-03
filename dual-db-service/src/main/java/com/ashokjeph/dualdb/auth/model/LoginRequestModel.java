package com.ashokjeph.dualdb.auth.model;

import lombok.Data;

@Data
public class LoginRequestModel {
    private String email;
    private String password;
    private Object expiryMinute;
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
	public Object getExpiryMinute() {
		return expiryMinute;
	}
	public void setExpiryMinute(Object expiryMinute) {
		this.expiryMinute = expiryMinute;
	}
}

