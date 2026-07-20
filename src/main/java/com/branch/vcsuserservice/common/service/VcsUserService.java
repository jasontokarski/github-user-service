package com.branch.vcsuserservice.common.service;

import com.branch.vcsuserservice.common.dto.VcsUserResponse;

public interface VcsUserService {
    VcsUserResponse getUserAndRepos(String username);
    String getProviderName();
}
