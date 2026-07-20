package com.branch.vcsuserservice.exception;

public class VcsServiceUnavailableException extends RuntimeException {
    public VcsServiceUnavailableException(String message) {
        super(message);
    }
}
