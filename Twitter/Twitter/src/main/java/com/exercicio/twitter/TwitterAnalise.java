package com.exercicio.twitter;

import java.util.List;

import org.ejml.simple.SimpleMatrix;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

public class TwitterAnalise {
	static StanfordCoreNLP pipeline;

	public static void init() {
		pipeline = new StanfordCoreNLP("MyPropFile.properties");
	}

	public static int findSentiment(String tweet) {
		int mainSentiment = 0;
		if (tweet != null && tweet.length() > 0) {
			int longest = 0;
			Annotation annotation = pipeline.process(tweet);
			for (CoreMap sentence : annotation
					.get(CoreAnnotations.SentencesAnnotation.class)) {
				Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
				int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
				String partText = sentence.toString();
				if (partText.length() > longest) {
					mainSentiment = sentiment;
					longest = partText.length();
				}
			}
		}
		return mainSentiment;
	}
	
	public static double findSentiments(String tweet) {
		double score = 0.0;
		if (tweet != null && tweet.length() > 0) {
			Annotation annotation = pipeline.process(tweet);
			List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
			score = getScore(sentences, 0.5);
		}
		if(Double.isNaN(score)) {
			return score = 0.0;
		}
		return score;
	}
	
	private static double getScore(List<CoreMap> sentences, double overallSentiment) {
	    int matrixIndex =
	            overallSentiment < -0.5  ? 0  // very negative
	            : overallSentiment < 0.0 ? 1  // negative
	            : overallSentiment < 0.5 ? 3  // positive
	            : 4;                       // very positive
	    double sum = 0;
	    int numberOfSentences = 0;
	    for (CoreMap sentence : sentences) {
	        Tree sentiments = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
	        int predictedClass = RNNCoreAnnotations.getPredictedClass(sentiments);
	        if (predictedClass == 2) { // neutral
	            continue;
	        }
	        SimpleMatrix matrix = RNNCoreAnnotations.getPredictions(sentiments);
	        sum += matrix.get(matrixIndex);
	        numberOfSentences++;
	    }
	    return sum / numberOfSentences;
	}
	
	public static String verificaScore(double value) {
		if(value < -0.5) {
			return value + " -> muito negativo ";
		}else if(value < 0.0) {
			return value + " -> negativo";
		}else if(value < 0.5) {
			return value + " -> positivo";
		}else {
			return value + " -> muito positivo";
		}
	}
}