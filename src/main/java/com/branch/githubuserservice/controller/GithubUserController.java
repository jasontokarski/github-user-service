package com.branch.githubuserservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.branch.githubuserservice.dto.response.GithubUserResponse;

@RestController
@RequestMapping("/api/v1/github/users")
public class GithubUserController {

    @GetMapping("/{username}")
    public ResponseEntity<GithubUserResponse> getUser(@PathVariable("username") String username) {
        return ResponseEntity.ok(GithubUserResponse.builder().build());
    }
}
    

