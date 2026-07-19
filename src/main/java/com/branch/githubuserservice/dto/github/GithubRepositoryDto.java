package com.branch.githubuserservice.dto.github;

import lombok.Builder;

@Builder
public record GithubRepositoryDto(
    String name,
    String url
) {}