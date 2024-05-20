package com.pda.practice.exception;

public class DuplicatedKeyException extends RuntimeException {
    private final String message;

    public DuplicatedKeyException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
