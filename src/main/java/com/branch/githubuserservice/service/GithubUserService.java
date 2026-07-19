package com.branch.githubuserservice.service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.branch.githubuserservice.client.GithubApiClient;
import com.branch.githubuserservice.dto.github.GithubRepositoryDto;
import com.branch.githubuserservice.dto.github.GithubUserDto;
import com.branch.githubuserservice.dto.response.GithubUserResponse;
import com.branch.githubuserservice.util.CompletionExceptionUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubUserService {

    private final GithubApiClient githubApiClient;
    private final ExecutorService virtualThreadExecutor;
    
    @Cacheable("githubUsers")
    public GithubUserResponse getGithubUserAndRepos(String username) {
        log.info("Fetching GitHub user and repositories for: {}", username);
        
        CompletableFuture<GithubUserDto> userFuture = CompletableFuture.supplyAsync(
            () -> githubApiClient.getUser(username),
            virtualThreadExecutor
        );

        CompletableFuture<List<GithubRepositoryDto>> repositoriesFuture = CompletableFuture.supplyAsync(
            () -> githubApiClient.getUserRepositories(username),
            virtualThreadExecutor
        );

        try {
            GithubUserResponse response = CompletableFuture.allOf(userFuture, repositoriesFuture)
                .thenApply(v -> mergeUserAndRepositories(userFuture.join(), repositoriesFuture.join()))
                .join();
            
            log.info("Successfully fetched data for user: {}", username);
            return response;
        } catch (CompletionException ex) {
            log.error("Error fetching GitHub data for {}: {}", username, ex.getMessage());
            throw CompletionExceptionUtil.unwrap(ex);
        }
    }
    
    private GithubUserResponse mergeUserAndRepositories(GithubUserDto user, List<GithubRepositoryDto> repositories) {
        String formattedCreatedAt = user.createdAt() != null 
            ? DateTimeFormatter.RFC_1123_DATE_TIME.format(user.createdAt().atZone(ZoneId.of("GMT")))
            : null;
            
        return GithubUserResponse.builder()
            .userName(user.login())
            .displayName(user.name())
            .avatar(user.avatarUrl())
            .geoLocation(user.location())
            .email(user.email())
            .url(user.url())
            .createdAt(formattedCreatedAt)
            .repos(repositories)
            .build();
    }
}
