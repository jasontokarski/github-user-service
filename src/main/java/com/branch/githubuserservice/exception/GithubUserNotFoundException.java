package com.branch.githubuserservice.exception;

// Custom exception for user not found (404) errors
public class GithubUserNotFoundException extends RuntimeException {
    public GithubUserNotFoundException(String message) {
        super(message);
    }
}
