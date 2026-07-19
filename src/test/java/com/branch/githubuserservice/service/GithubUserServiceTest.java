package com.branch.githubuserservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.branch.githubuserservice.client.GithubApiClient;
import com.branch.githubuserservice.dto.github.GithubRepositoryDto;
import com.branch.githubuserservice.dto.github.GithubUserDto;
import com.branch.githubuserservice.dto.response.GithubUserResponse;

@ExtendWith(MockitoExtension.class)
class GithubUserServiceTest {

    @Mock
    private GithubApiClient githubApiClient;

    private ExecutorService executorService;

    private GithubUserService githubUserService;

    @BeforeEach
    void setUp() {
        executorService = Executors.newVirtualThreadPerTaskExecutor();

        githubUserService = new GithubUserService(
                githubApiClient,
                executorService
        );
    }

    @AfterEach
    void tearDown() {
        executorService.shutdown();
    }

    @Test
    void shouldReturnGithubUserResponse() {

        GithubUserDto githubUserDto = GithubUserDto.builder()
            .login("octocat")
            .name("The Octocat")
            .avatarUrl("https://github.com/octocat.png")
            .location("San Francisco")
            .email(null)
            .url("https://github.com/octocat")
            .createdAt(Instant.parse("2021-01-01T00:00:00Z"))
            .build();

        GithubRepositoryDto githubRepositoryDto1 = GithubRepositoryDto.builder()
            .name("Hello-World")
            .url("https://github.com/octocat/Hello-World")
            .build();

        GithubRepositoryDto githubRepositoryDto2 = GithubRepositoryDto.builder()
            .name("Jankins-CI")
            .url("https://github.com/octocat/Jankins-CI")
            .build();

        List<GithubRepositoryDto> githubRepositoryDtos = List.of(githubRepositoryDto1, githubRepositoryDto2);

        when(githubApiClient.getUser("octocat")).thenReturn(githubUserDto);
        when(githubApiClient.getUserRepositories("octocat")).thenReturn(githubRepositoryDtos);

        GithubUserResponse githubUserResponse = githubUserService.getGithubUserAndRepos("octocat");

        assertEquals(githubUserResponse.userName(), "octocat");
        assertEquals(githubUserResponse.displayName(), "The Octocat");
        assertEquals(githubUserResponse.avatar(), "https://github.com/octocat.png");
        assertEquals(githubUserResponse.geoLocation(), "San Francisco");
        assertEquals(githubUserResponse.email(), null);
        assertEquals(githubUserResponse.url(), "https://github.com/octocat");
        assertEquals(githubUserResponse.repos(), githubRepositoryDtos);
        assertEquals(githubUserResponse.repos().size(), 2);
        verify(githubApiClient).getUser("octocat");
        verify(githubApiClient).getUserRepositories("octocat");
    }
}