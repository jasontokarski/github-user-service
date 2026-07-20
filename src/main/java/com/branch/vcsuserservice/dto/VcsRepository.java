package com.branch.vcsuserservice.dto;

import lombok.Builder;

@Builder
public record VcsRepository(
    String name,
    String url
) {}
