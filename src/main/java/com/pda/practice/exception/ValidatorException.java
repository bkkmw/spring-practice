package com.pda.practice.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidatorException extends Exception {

    private List<String[]> errors;
    public ValidatorException(List<String[]> errors) {
        super();
        this.errors = errors;
    }

    public ValidatorException(String target, String message) {
        this(new ArrayList<>() {{ add(new String[] {target, message}); }});
    }

    public List<String[]> getValidationErrors() {
        return this.errors;
    }
}
