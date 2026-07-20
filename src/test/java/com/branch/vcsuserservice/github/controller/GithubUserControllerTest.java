package com.branch.vcsuserservice.github.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.branch.vcsuserservice.common.dto.VcsUserResponse;
import com.branch.vcsuserservice.github.exception.GithubUserNotFoundException;
import com.branch.vcsuserservice.github.service.GithubUserService;

@WebMvcTest(GithubUserController.class)
public class GithubUserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GithubUserService githubUserService;
    
    @Test
    void shouldReturnUserWhenValidUsername() throws Exception {
        String username = "octocat";
        VcsUserResponse mockResponse = VcsUserResponse.builder()
                .userName("octocat")
                .displayName("The Octocat")
                .provider("github")
                .repos(Collections.emptyList())
                .build();
        
        when(githubUserService.getUserAndRepos(username)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/v1/github/users/{username}", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user_name").value("octocat"))
                .andExpect(jsonPath("$.display_name").value("The Octocat"))
                .andExpect(jsonPath("$.provider").value("github"));
    }

    @Test
    void shouldReturnNotFoundWhenUsernameIsInvalid() throws Exception {
        String username = "invalid";
        when(githubUserService.getUserAndRepos(username)).thenThrow(new GithubUserNotFoundException("User not found"));
        mockMvc.perform(get("/api/v1/github/users/{username}", username))
                .andExpect(status().isNotFound());
    }
}
