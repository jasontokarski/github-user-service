package com.branch.vcsuserservice.github.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.branch.vcsuserservice.common.util.CompletionExceptionUtil;
import com.branch.vcsuserservice.common.util.DateFormatter;
import com.branch.vcsuserservice.common.dto.VcsRepository;
import com.branch.vcsuserservice.common.dto.VcsUserResponse;
import com.branch.vcsuserservice.github.client.GithubApiClient;
import com.branch.vcsuserservice.github.dto.github.GithubRepositoryDto;
import com.branch.vcsuserservice.github.dto.github.GithubUserDto;
import com.branch.vcsuserservice.common.service.VcsUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubUserService implements VcsUserService {

    private final GithubApiClient githubApiClient;
    private final ExecutorService virtualThreadExecutor;
    
    @Override
    @Cacheable("githubUsers")
    public VcsUserResponse getUserAndRepos(String username) {
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
            VcsUserResponse response = CompletableFuture.allOf(userFuture, repositoriesFuture)
                .thenApply(v -> mergeUserAndRepositories(userFuture.join(), repositoriesFuture.join()))
                .join();
            
            log.info("Successfully fetched data for user: {}", username);
            return response;
        } catch (CompletionException ex) {
            log.error("Error fetching GitHub data for {}: {}", username, ex.getMessage());
            throw CompletionExceptionUtil.unwrap(ex);
        }
    }
    
    private VcsUserResponse mergeUserAndRepositories(GithubUserDto user, List<GithubRepositoryDto> repositories) {
        List<VcsRepository> vcsRepos = repositories.stream()
            .map(repo -> VcsRepository.builder()
                .name(repo.name())
                .url(repo.url())
                .build())
            .toList();
            
        return VcsUserResponse.builder()
            .userName(user.login())
            .displayName(user.name())
            .avatar(user.avatarUrl())
            .geoLocation(user.location())
            .email(user.email())
            .url(user.url())
            .createdAt(DateFormatter.formatToRfc1123(user.createdAt()))
            .repos(vcsRepos)
            .provider(getProviderName())
            .build();
    }
    
    @Override
    public String getProviderName() {
        return "github";
    }
}
