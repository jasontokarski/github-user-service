package com.branch.vcsuserservice.github.dto.github;

import lombok.Builder;

@Builder
public record GithubRepositoryDto(
    String name,
    String url
) {}