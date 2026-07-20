package com.branch.vcsuserservice.github.fixtures;

import java.time.Instant;
import java.util.List;

import com.branch.vcsuserservice.github.dto.github.GithubRepositoryDto;
import com.branch.vcsuserservice.github.dto.github.GithubUserDto;

public class GithubTestFixtures {

    public static final GithubUserDto DEFAULT_USER = GithubUserDto.builder()
        .login("octocat")
        .name("The Octocat")
        .avatarUrl("https://avatars.githubusercontent.com/u/583231")
        .location("San Francisco")
        .email("octocat@github.com")
        .url("https://api.github.com/users/octocat")
        .createdAt(Instant.parse("2011-01-25T18:44:36Z"))
        .build();

    public static final GithubRepositoryDto HELLO_WORLD_REPO = GithubRepositoryDto.builder()
        .name("Hello-World")
        .url("https://api.github.com/repos/octocat/Hello-World")
        .build();

    public static final GithubRepositoryDto JANKINS_CI_REPO = GithubRepositoryDto.builder()
        .name("Jankins-CI")
        .url("https://api.github.com/repos/octocat/Jankins-CI")
        .build();

    public static final List<GithubRepositoryDto> DEFAULT_REPOS = List.of(
        HELLO_WORLD_REPO,
        JANKINS_CI_REPO
    );

    private GithubTestFixtures() {
        // Utility class - prevent instantiation
    }
}
