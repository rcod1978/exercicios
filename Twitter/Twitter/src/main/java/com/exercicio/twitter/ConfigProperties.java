package com.exercicio.twitter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class ConfigProperties {
    @Value("${oauth.consumerKey}")    
    private String consumerKey;

    @Value("${oauth.consumerSecret}")    
    private String consumerSecret;

    @Value("${oauth.accessToken}")    
    private String accessToken;

    @Value("${oauth.accessTokenSecret}")    
    private String accessTokenSecret;

    @Value("${tweets.limitPesquisa}")    
    private int limitePesquisaTweets;

	public String getConsumerKey() {
		return consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public int getLimitePesquisaTweets() {
		return limitePesquisaTweets;
	}

	
}
