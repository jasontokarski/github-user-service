package com.branch.vcsuserservice.github.exception;

import com.branch.vcsuserservice.common.exception.VcsServiceUnavailableException;

public class GithubServiceUnavailableException extends VcsServiceUnavailableException {
    public GithubServiceUnavailableException(String message) {
        super(message);
    }
}
