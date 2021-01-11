package com.exercicio.twitter;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterManager {
	
	public static ArrayList<String> getTweets(String topic, String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret, int limit) {
		ConfigurationBuilder cb = configure(consumerKey, consumerSecret, accessToken, accessTokenSecret);
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		ArrayList<String> tweetList = new ArrayList<String>();
		try {
			Query query = new Query(topic);
			QueryResult result;
			long i = 1;
			System.out.println("Procurando Tweets para "+ topic +"...");
			do {
				i++;
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				for (Status tweet : tweets) {
					tweetList.add(tweet.getText());
				}
			} while ((query = result.nextQuery()) != null && i < limit);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
		}
		return tweetList;
	}

	private static ConfigurationBuilder configure(String consumerKey, String consumerSecret, String accessToken,
			String accessTokenSecret) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		 cb.setDebugEnabled(true)
		     .setOAuthConsumerKey(consumerKey)
		     .setOAuthConsumerSecret(consumerSecret)
		     .setOAuthAccessToken(accessToken)
		     .setOAuthAccessTokenSecret(accessTokenSecret);
		return cb;
	}
}