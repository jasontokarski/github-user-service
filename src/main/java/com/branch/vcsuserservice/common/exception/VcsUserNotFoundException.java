package com.branch.vcsuserservice.common.exception;

public class VcsUserNotFoundException extends RuntimeException {
    public VcsUserNotFoundException(String message) {
        super(message);
    }
}
