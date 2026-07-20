package com.branch.vcsuserservice.github.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.branch.vcsuserservice.dto.VcsUserResponse;
import com.branch.vcsuserservice.github.service.GithubUserService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/github/users")
public class GithubUserController {

    private final GithubUserService githubUserService;
    
    @GetMapping("/{username}")
    public ResponseEntity<VcsUserResponse> getUser(
            @PathVariable("username")
            @NotBlank(message = "Username cannot be blank")
            @Pattern(regexp = "^[a-zA-Z0-9]([a-zA-Z0-9-]{0,37}[a-zA-Z0-9])?$", 
                     message = "Invalid GitHub username format")
            String username) {
        VcsUserResponse vcsUserResponse = githubUserService.getUserAndRepos(username);
        return ResponseEntity.ok(vcsUserResponse);
    }
}
