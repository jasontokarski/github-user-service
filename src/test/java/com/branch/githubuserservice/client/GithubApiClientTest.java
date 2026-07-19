package com.branch.githubuserservice.client;

import static com.branch.githubuserservice.fixtures.GithubTestFixtures.HELLO_WORLD_REPO;
import static com.branch.githubuserservice.fixtures.GithubTestFixtures.JANKINS_CI_REPO;
import static com.branch.githubuserservice.fixtures.GithubTestFixtures.DEFAULT_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import com.branch.githubuserservice.dto.github.GithubRepositoryDto;
import com.branch.githubuserservice.dto.github.GithubUserDto;

@ExtendWith(MockitoExtension.class)
class GithubApiClientTest {

    @Mock
    private RestClient githubRestClient;

    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    private GithubApiClient githubApiClient;

    @BeforeEach
    void setUp() {
        githubApiClient = new GithubApiClient(githubRestClient);
    }

    @Test
    void shouldReturnGithubUserDto() {
        String username = "octocat";
        GithubUserDto expectedUser = DEFAULT_USER;

        when(githubRestClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), eq(username))).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(GithubUserDto.class)).thenReturn(expectedUser);

        GithubUserDto result = githubApiClient.getUser(username);

        assertNotNull(result);
        assertEquals("octocat", result.login());
        assertEquals("The Octocat", result.name());
        assertEquals("https://avatars.githubusercontent.com/u/583231", result.avatarUrl());
        assertEquals("San Francisco", result.location());
        assertEquals("octocat@github.com", result.email());
        assertEquals("https://api.github.com/users/octocat", result.url());
        assertEquals(Instant.parse("2011-01-25T18:44:36Z"), result.createdAt());
    }

    @Test
    void shouldReturnListOfRepositories() {
        String username = "octocat";

        List<GithubRepositoryDto> expectedRepos = List.of(HELLO_WORLD_REPO, JANKINS_CI_REPO);

        when(githubRestClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), eq(username))).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(GithubRepositoryDto[].class)).thenReturn(expectedRepos.toArray(new GithubRepositoryDto[0]));

        List<GithubRepositoryDto> result = githubApiClient.getUserRepositories(username);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Hello-World", result.get(0).name());
        assertEquals("https://api.github.com/repos/octocat/Hello-World", result.get(0).url());
        assertEquals("Jankins-CI", result.get(1).name());
        assertEquals("https://api.github.com/repos/octocat/Jankins-CI", result.get(1).url());
    }
}
