package com.branch.vcsuserservice.exception;

public class VcsRateLimitException extends RuntimeException {
    public VcsRateLimitException(String message) {
        super(message);
    }
}
