package com.branch.githubuserservice.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.branch.githubuserservice.dto.github.GithubRepositoryDto;
import com.branch.githubuserservice.dto.github.GithubUserDto;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class GithubRestClient {
    private final RestClient githubRestClient;
    
    public GithubUserDto getUser(String username) {
        return githubRestClient.get()
            .uri("/users/{username}", username)
            .retrieve()
            .body(GithubUserDto.class);
    }

    public List<GithubRepositoryDto> getUserRepositories(String username) {
        GithubRepositoryDto[] repositories = githubRestClient.get()
            .uri("/users/{username}/repos", username)
            .retrieve()
            .body(GithubRepositoryDto[].class);
            
        return repositories != null ? Arrays.asList(repositories) : List.of();
    }
}