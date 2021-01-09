package com.exercicio.movie.api.themoviedb.vo;

import java.util.List;

public class TheMovieDbResultadoVo {
	private boolean adult;
	private int gender;
	private long id;
	private List<TheMovieDbKnownVo> known_for;
	private String known_for_department;
	private String name;
	private String popularity;
	private String profile_path;
	
	public boolean isAdult() {
		return adult;
	}
	public void setAdult(boolean adult) {
		this.adult = adult;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<TheMovieDbKnownVo> getKnown_for() {
		return known_for;
	}
	public void setKnown_for(List<TheMovieDbKnownVo> known_for) {
		this.known_for = known_for;
	}
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
	public String getPopularity() {
		return popularity;
	}
	public void setPopularity(String popularity) {
		this.popularity = popularity;
	}
	public String getProfile_path() {
		return profile_path;
	}
	public void setProfile_path(String profile_path) {
		this.profile_path = profile_path;
	}
	
	
	
}
