package com.exercicio.movie.api.themoviedb.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TheMovieDbCastVo {
	private long id;
	private String known_for_department;
	private String name;
	private int order;
	private Date release_date;
	private String title;
	
	public String getKnown_for_department() {
		return known_for_department;
	}
	public void setKnown_for_department(String known_for_department) {
		this.known_for_department = known_for_department;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public Date getRelease_date() {
		return release_date;
	}
	public void setRelease_date(Date release_date) {
		this.release_date = release_date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
}
