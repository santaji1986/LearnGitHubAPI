package org.san.githubapi.controllers;

import java.util.List;

import org.san.githubapi.models.FollowingDTO;
import org.san.githubapi.models.Repository;
import org.san.githubapi.services.GitHubAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitHubAPIController {
    @Autowired
    private GitHubAPIService gitHubAPIService;

    @GetMapping(value = "PublicRepoList")
    public List<Repository> getPublicRepoList() {
        final List<Repository> listRepositories = gitHubAPIService.getPublicRepoList();
        return listRepositories;
    }

    @GetMapping(value = "FollowingList")
    public FollowingDTO getFollowingList(@RequestParam(required = false) String username) {
        final FollowingDTO followingDTO = gitHubAPIService.getFollowingList(username);
        return followingDTO;
    }

    @GetMapping(value = "UserRepos")
    public List<Repository> getUserRepos(@RequestParam(required = false) String username) {
        final List<Repository> listRepositories = gitHubAPIService.getUserRepos(username);
        return listRepositories;
    }
}
