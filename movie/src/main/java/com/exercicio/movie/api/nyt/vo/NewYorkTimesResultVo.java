package com.exercicio.movie.api.nyt.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class NewYorkTimesResultVo {

	private String display_title;
	private String mpaa_rating;
	private String critics_pick;
	private String byline;
	private String headline;
	private String summary_short;
	private Date publication_date;
	private Date opening_date;

	public String getDisplay_title() {
		return display_title;
	}
	public void setDisplay_title(String display_title) {
		this.display_title = display_title;
	}
	public String getMpaa_rating() {
		return mpaa_rating;
	}
	public void setMpaa_rating(String mpaa_rating) {
		this.mpaa_rating = mpaa_rating;
	}
	public String getCritics_pick() {
		return critics_pick;
	}
	public void setCritics_pick(String critics_pick) {
		this.critics_pick = critics_pick;
	}
	public String getByline() {
		return byline;
	}
	public void setByline(String byline) {
		this.byline = byline;
	}
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public String getSummary_short() {
		return summary_short;
	}
	public void setSummary_short(String summary_short) {
		this.summary_short = summary_short;
	}
	public Date getPublication_date() {
		return publication_date;
	}
	public void setPublication_date(Date publication_date) {
		this.publication_date = publication_date;
	}
	public Date getOpening_date() {
		return opening_date;
	}
	public void setOpening_date(Date opening_date) {
		this.opening_date = opening_date;
	}
	
}
