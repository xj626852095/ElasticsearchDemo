package com.kevin.es.model;

import java.io.Serializable;
import java.util.List;

public class TestModel implements Serializable{
	private static final long serialVersionUID = 7046187046473012435L;
	
	private long id;
	private String type;
	/**
	* 这里是一个列表
	*/
	private List<Integer> catIds;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Integer> getCatIds() {
		return catIds;
	}
	public void setCatIds(List<Integer> catIds) {
		this.catIds = catIds;
	}
	
	
	
}
