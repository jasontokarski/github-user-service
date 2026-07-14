package com.branch.githubuserservice.dto.github;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubUserDto(

    String login,

    String name,

    @JsonProperty("avatar_url")
    String avatarUrl,

    String location,

    String email,

    String url,

    @JsonProperty("created_at")
    Instant createdAt

) {}
