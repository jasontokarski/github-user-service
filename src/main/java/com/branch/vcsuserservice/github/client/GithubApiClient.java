package com.branch.vcsuserservice.github.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.branch.vcsuserservice.github.dto.github.GithubRepositoryDto;
import com.branch.vcsuserservice.github.dto.github.GithubUserDto;
import com.branch.vcsuserservice.github.exception.GithubServiceUnavailableException;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class GithubApiClient {
    private final RestClient githubRestClient;
    
    @Retryable(retryFor = GithubServiceUnavailableException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    public GithubUserDto getUser(String username) {
        return githubRestClient.get()
            .uri("/users/{username}", username)
            .retrieve()
            .body(GithubUserDto.class);
    }

    @Retryable(retryFor = GithubServiceUnavailableException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    public List<GithubRepositoryDto> getUserRepositories(String username) {
        GithubRepositoryDto[] repositories = githubRestClient.get()
            .uri("/users/{username}/repos", username)
            .retrieve()
            .body(GithubRepositoryDto[].class);
            
        return repositories != null ? Arrays.asList(repositories) : List.of();
    }
}