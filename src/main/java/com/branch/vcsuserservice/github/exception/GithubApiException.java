package com.branch.vcsuserservice.github.exception;


// Custom exception for miscellaneous API errors
public class GithubApiException extends RuntimeException {
    public GithubApiException(String message) {
        super(message);
    }
}
