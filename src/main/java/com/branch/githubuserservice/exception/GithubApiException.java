package com.branch.githubuserservice.exception;


// Custom exception for miscellaneous API errors
public class GithubApiException extends RuntimeException {
    public GithubApiException(String message) {
        super(message);
    }
}
