package com.branch.githubuserservice.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import org.springframework.stereotype.Service;

import com.branch.githubuserservice.client.GithubRestClient;
import com.branch.githubuserservice.dto.github.GithubRepositoryDto;
import com.branch.githubuserservice.dto.github.GithubUserDto;
import com.branch.githubuserservice.dto.response.GithubUserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GithubUserService {

    private final GithubRestClient githubRestClient;
    private final ExecutorService virtualThreadExecutor;
    
    public GithubUserResponse getGithubUserAndRepos(String username) {
        CompletableFuture<GithubUserDto> userFuture = CompletableFuture.supplyAsync(
            () -> githubRestClient.getUser(username),
            virtualThreadExecutor
        );

        CompletableFuture<List<GithubRepositoryDto>> repositoriesFuture = CompletableFuture.supplyAsync(
            () -> githubRestClient.getUserRepositories(username),
            virtualThreadExecutor
        );

        GithubUserResponse response = CompletableFuture.allOf(userFuture, repositoriesFuture)
        .thenApply(v -> mergeUserAndRepositories(userFuture.join(), repositoriesFuture.join()))
        .join();

        return response;
    }
    
    public GithubUserResponse mergeUserAndRepositories(GithubUserDto user, List<GithubRepositoryDto> repositories) {
        return GithubUserResponse.builder()
            .userName(user.login())
            .displayName(user.name())
            .avatar(user.avatarUrl())
            .geoLocation(user.location())
            .email(user.email())
            .url(user.url())
            .createdAt(user.createdAt())
            .repos(repositories)
            .build();
    }
}
