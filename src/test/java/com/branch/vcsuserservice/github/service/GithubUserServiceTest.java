package com.branch.vcsuserservice.github.service;

import static com.branch.vcsuserservice.github.fixtures.GithubTestFixtures.DEFAULT_REPOS;
import static com.branch.vcsuserservice.github.fixtures.GithubTestFixtures.DEFAULT_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.branch.vcsuserservice.github.client.GithubApiClient;
import com.branch.vcsuserservice.github.dto.response.GithubUserResponse;

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
        when(githubApiClient.getUser("octocat")).thenReturn(DEFAULT_USER);
        when(githubApiClient.getUserRepositories("octocat")).thenReturn(DEFAULT_REPOS);

        GithubUserResponse githubUserResponse = githubUserService.getGithubUserAndRepos("octocat");

        assertEquals(githubUserResponse.userName(), "octocat");
        assertEquals(githubUserResponse.displayName(), "The Octocat");
        assertEquals(githubUserResponse.avatar(), "https://avatars.githubusercontent.com/u/583231");
        assertEquals(githubUserResponse.geoLocation(), "San Francisco");
        assertEquals(githubUserResponse.email(), "octocat@github.com");
        assertEquals(githubUserResponse.url(), "https://api.github.com/users/octocat");
        assertEquals(githubUserResponse.repos(), DEFAULT_REPOS);
        assertEquals(githubUserResponse.repos().size(), 2);
        verify(githubApiClient).getUser("octocat");
        verify(githubApiClient).getUserRepositories("octocat");
    }
}