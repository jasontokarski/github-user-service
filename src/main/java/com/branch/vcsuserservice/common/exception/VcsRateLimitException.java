package com.branch.vcsuserservice.common.exception;

public class VcsRateLimitException extends RuntimeException {
    public VcsRateLimitException(String message) {
        super(message);
    }
}
