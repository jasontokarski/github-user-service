package com.branch.vcsuserservice.github.exception;

import com.branch.vcsuserservice.common.exception.VcsUserNotFoundException;

public class GithubUserNotFoundException extends VcsUserNotFoundException {
    public GithubUserNotFoundException(String message) {
        super(message);
    }
}
