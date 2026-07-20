package com.branch.vcsuserservice.common.exception;

public class VcsServiceUnavailableException extends RuntimeException {
    public VcsServiceUnavailableException(String message) {
        super(message);
    }
}
