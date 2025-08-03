package com.ashokjeph.dualdb.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OutputMessage {
    private String userId;
    private String message;
    private String dateTime;
    private String token;

    public OutputMessage(final String userId, final String message, final String dateTime) {
        this.userId = userId;
        this.message = message;
        this.dateTime = dateTime;
    }

	public OutputMessage(String userId, String message, String dateTime, String token) {
		super();
		this.userId = userId;
		this.message = message;
		this.dateTime = dateTime;
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
