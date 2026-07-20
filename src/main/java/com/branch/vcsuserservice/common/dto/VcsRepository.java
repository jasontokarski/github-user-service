package com.branch.vcsuserservice.common.dto;

import lombok.Builder;

@Builder
public record VcsRepository(
    String name,
    String url
) {}
