package com.kevin.es.model;

public class User {
	
	public int age;
	public String name;
	public long weight;
	public boolean married;
	
	public User() {
		super();
	}
	
	public User(int age, String name, long weight, boolean married) {
		super();		
		this.age = age;
		this.name = name;
		this.weight = weight;
		this.married = married;
	}
	
	

	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getWeight() {
		return weight;
	}
	public void setWeight(long weight) {
		this.weight = weight;
	}
	public boolean isMarried() {
		return married;
	}
	public void setMarried(boolean married) {
		this.married = married;
	}
	
	
	
}
