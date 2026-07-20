package com.branch.vcsuserservice.github.exception;

// Custom exception for service unavailable (503) errors
public class GithubServiceUnavailableException extends RuntimeException {
    public GithubServiceUnavailableException(String message) {
        super(message);
    }
}
