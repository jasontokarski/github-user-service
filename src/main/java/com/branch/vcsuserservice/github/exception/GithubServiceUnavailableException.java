package com.branch.vcsuserservice.github.exception;

import com.branch.vcsuserservice.exception.VcsServiceUnavailableException;

public class GithubServiceUnavailableException extends VcsServiceUnavailableException {
    public GithubServiceUnavailableException(String message) {
        super(message);
    }
}
