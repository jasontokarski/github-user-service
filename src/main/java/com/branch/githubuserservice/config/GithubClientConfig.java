package com.branch.githubuserservice.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestClient;

import com.branch.githubuserservice.exception.GithubApiException;
import com.branch.githubuserservice.exception.GithubServiceUnavailableException;
import com.branch.githubuserservice.exception.GithubUserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableRetry
@Configuration
public class GithubClientConfig {
    
    @Bean
    public RestClient githubRestClient(@Value("${github.api.base-url}") String baseUrl) {
        return RestClient.builder()
            .baseUrl(baseUrl)
            .defaultStatusHandler(
                status -> status.value() == 404,
                (request, response) -> {
                    log.warn("GitHub API returned 404");
                    throw new GithubUserNotFoundException("Resource not found");
                }
            )
            .defaultStatusHandler(
                status -> status.value() == 403,
                (request, response) -> {
                    log.error("GitHub API forbidden error");
                    throw new GithubApiException("GitHub API forbidden error");
                }
            )
            .defaultStatusHandler(
                HttpStatusCode::is4xxClientError,
                (request, response) -> {
                    log.error("GitHub API client error: {}", response.getStatusCode());
                    throw new GithubApiException("GitHub API client error: " + response.getStatusCode());
                }
            )
            .defaultStatusHandler(
                HttpStatusCode::is5xxServerError,
                (request, response) -> {
                    log.error("GitHub API server error: {}", response.getStatusCode());
                    throw new GithubServiceUnavailableException("GitHub API is temporarily unavailable");
                }
            )
            .build();
    }

    @Bean
    public ExecutorService virtualThreadExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}
