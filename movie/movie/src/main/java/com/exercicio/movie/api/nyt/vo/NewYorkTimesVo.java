package com.exercicio.movie.api.nyt.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class NewYorkTimesVo {
	private String status;
	private String copyright;
	private boolean has_more;
	private int num_results;
	private List<NewYorkTimesResultVo> results;
	
	public String getStatus() {
		return status;
	}
	public String getCopyright() {
		return copyright;
	}
	public boolean isHas_more() {
		return has_more;
	}
	public int getNum_results() {
		return num_results;
	}
	public List<NewYorkTimesResultVo> getResults() {
		return results;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public void setHas_more(boolean has_more) {
		this.has_more = has_more;
	}
	public void setNum_results(int num_results) {
		this.num_results = num_results;
	}
	public void setResults(List<NewYorkTimesResultVo> results) {
		this.results = results;
	}
	
	
}
