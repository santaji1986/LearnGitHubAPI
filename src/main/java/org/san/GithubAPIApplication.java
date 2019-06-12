package org.san;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class GithubAPIApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(GithubAPIApplication.class, args);
	}
	
	@Bean
	public WebClient getWebClient() {
		// @formatter:off
		return WebClient.builder().baseUrl("https://api.github.com")
		        .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.github.v3+json")
		        .defaultHeader(HttpHeaders.USER_AGENT, "Spring 5 WebClient")
		        .build();
		// @formatter:on
	}
}
