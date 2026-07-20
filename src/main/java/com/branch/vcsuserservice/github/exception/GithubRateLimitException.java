package com.branch.vcsuserservice.github.exception;

import com.branch.vcsuserservice.exception.VcsRateLimitException;

public class GithubRateLimitException extends VcsRateLimitException {
    public GithubRateLimitException(String message) {
        super(message);
    }
}
