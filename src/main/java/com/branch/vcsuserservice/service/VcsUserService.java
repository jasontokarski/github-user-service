package com.branch.vcsuserservice.service;

import com.branch.vcsuserservice.dto.VcsUserResponse;

public interface VcsUserService {
    VcsUserResponse getUserAndRepos(String username);
    String getProviderName();
}
