package com.ashokjeph.dualdb.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntityAlreadyExistsException extends RuntimeException {
    private final HttpStatus status;

    public EntityAlreadyExistsException(String message) {
        super(message);
        this.status = HttpStatus.CONFLICT;
    }

    public EntityAlreadyExistsException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

	public HttpStatus getStatus() {
		return status;
	}

}
