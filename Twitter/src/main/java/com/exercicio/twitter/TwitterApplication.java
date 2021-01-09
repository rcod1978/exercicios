package com.exercicio.twitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TwitterApplication {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static void main(String[] args) {
		ApplicationContext c = SpringApplication.run(TwitterApplication.class, args);
		ConfigProperties con = c.getBean(ConfigProperties.class);
		checkTwitter(con.getConsumerKey(), con.getConsumerSecret(), con.getAccessToken(), con.getAccessTokenSecret(), con.getLimitePesquisaTweets());
	}
	
	private static void checkTwitter(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret, int limitePesquisaTweets) {
		List<String> arrayTweetsInput = Arrays.asList("Turismo","Portugal","Bancos", "UE");
		HashMap<String, Double> classificacao = new HashMap<String, Double>();
		TwitterAnalise.init();
		for(String tweetInput : arrayTweetsInput) {
			ArrayList<String> tweets = TwitterManager.getTweets(tweetInput, consumerKey, consumerSecret, accessToken, accessTokenSecret, limitePesquisaTweets);
			if(tweets != null && tweets.size() > 0) {
				System.out.println("Quantidade tweets = "+ tweets.size());
				double count = 0.0;
				for(String tweet : tweets) {
					count += TwitterAnalise.findSentiments(tweet);
				}
				classificacao.put(tweetInput, count);
				System.out.println("Total sentimentos = " + count);
			}else {
				System.out.println("Nenhum  tweet encontrado para "+ tweetInput);
			}
		}

		Map<String, Double> result = retornarClassificacaoSentimento(classificacao);
                
		exibeConsoleResultado(result);
	}

	private static void exibeConsoleResultado(Map<String, Double> result) {
		System.out.println("#########  Resultado analise  #############");
		result.forEach((key, value) -> System.out.println(key + " = " + TwitterAnalise.verificaScore(value)));
	}

	private static Map<String, Double> retornarClassificacaoSentimento(HashMap<String, Double> classificacao) {
		Map<String, Double> result = classificacao
		        .entrySet()
		        .stream()
		        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
		        .collect(Collectors.
		            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
		                LinkedHashMap::new));
		return result;
	}
}
