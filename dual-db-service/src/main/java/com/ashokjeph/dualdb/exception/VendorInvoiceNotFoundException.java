package com.ashokjeph.dualdb.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class VendorInvoiceNotFoundException extends RuntimeException {

    private final HttpStatus status;

    public HttpStatus getStatus() {
		return status;
	}

	// Constructor with default status
    public VendorInvoiceNotFoundException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND; // default
    }

    // Constructor with custom status
    public VendorInvoiceNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
