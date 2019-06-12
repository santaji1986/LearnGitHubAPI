package org.san.githubapi.services;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.san.githubapi.models.Following;
import org.san.githubapi.models.FollowingDTO;
import org.san.githubapi.models.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GitHubAPIService {

    @Autowired
    private WebClient webClient;

    @Value("${github.url}")
    private String gitHubURL;

    @Value("${github.username}")
    private String gitHubUsername;

    @Value("${github.password}")
    private String gitHubPassword;

    public List<Repository> getPublicRepoList() {
        // @formatter:off
		final List<Repository> list = webClient.get()
		                              .uri(gitHubURL + "repositories")
		                              .retrieve()
		                              .bodyToFlux(Repository.class)
		                              .collectList()
		                              .block();
		// @formatter:on
        System.out.println("GitHubAPIService.getPublicRepoList() : " + list);
        return list;
    }

    public FollowingDTO getFollowingList(String username) {
        final FollowingDTO followingListForUser = getFollowingListForUser(username);
        return followingListForUser;
    }

    public FollowingDTO getFollowingListForUser(String username) {
        final String contextPath = StringUtils.isEmpty(username) ? "user/following" : "users/" + username + "/following";

        final List<Following> list = getFollowersList(contextPath);
        final List<String> followers = getLoginNamesFromFollowersList(list);

        return new FollowingDTO(username, followers);
    }

    private List<String> getLoginNamesFromFollowersList(final List<Following> list) {
        final List<String> followers = new ArrayList<>();
        for (int i = 0; i < 5 && i < list.size(); i++) {
            final String loginName = getLoginName(list.get(i));
            followers.add(loginName);
        }
        return followers;
    }

    private List<Following> getFollowersList(final String context) {
        // @formatter:off
        final List<Following> list =webClient
                                    .get()
									.uri(context)
									.header("Authorization", getAuthHeader())
							        .retrieve()
									.bodyToFlux(Following.class)
									.collectList()
									.block();
        // @formatter:on
        return list;
    }

    private String getLoginName(Following following) {
        return following.getLogin();
    }

    private String getAuthHeader() {
        return "Basic " + Base64Utils.encodeToString((gitHubUsername + ":" + gitHubPassword).getBytes(StandardCharsets.UTF_8));
    }

    public List<Repository> getUserRepos(String username) {
        // @formatter:off
		return webClient.get()
		        .uri("/user/repos")
		        .header("Authorization", getAuthHeader())
		        .retrieve()
		        .bodyToFlux(Repository.class)
		        .collectList()
		        .block();
		// @formatter:on
    }

}
