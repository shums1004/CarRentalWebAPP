package com.neu.shubham.carRental.exception;

public class CustomerException extends Exception {

    public CustomerException(String message) {
        super(message);
    }

    public CustomerException(String message, Throwable cause) {
        super(message, cause);
    }
}
