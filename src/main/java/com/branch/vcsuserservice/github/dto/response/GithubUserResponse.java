package com.branch.vcsuserservice.github.dto.response;

import java.util.List;

import com.branch.vcsuserservice.github.dto.github.GithubRepositoryDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record GithubUserResponse(
    @JsonProperty("user_name")
    String userName,
    @JsonProperty("display_name")
    String displayName,
    @JsonProperty("avatar_url")
    String avatar,
    @JsonProperty("geo_location")
    String geoLocation,
    String email,
    String url,
    @JsonProperty("created_at")
    String createdAt,
    List<GithubRepositoryDto> repos
) {}