package com.pda.practice.exception;

public class InvalidLoginReqException extends Exception {

    private String message;

    public InvalidLoginReqException() {}

    public InvalidLoginReqException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
