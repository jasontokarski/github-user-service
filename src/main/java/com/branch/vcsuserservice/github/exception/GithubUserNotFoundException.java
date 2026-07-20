package com.branch.vcsuserservice.github.exception;

// Custom exception for user not found (404) errors
public class GithubUserNotFoundException extends RuntimeException {
    public GithubUserNotFoundException(String message) {
        super(message);
    }
}
