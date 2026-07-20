package com.branch.vcsuserservice.github.exception;

// Custom exception for rate limit errors (429)
public class GithubRateLimitException extends RuntimeException {
    public GithubRateLimitException(String message) {
        super(message);
    }
}
