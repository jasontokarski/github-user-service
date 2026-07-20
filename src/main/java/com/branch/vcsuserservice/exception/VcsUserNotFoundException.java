package com.branch.vcsuserservice.exception;

public class VcsUserNotFoundException extends RuntimeException {
    public VcsUserNotFoundException(String message) {
        super(message);
    }
}
