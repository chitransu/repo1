package com.java.collection;

import java.util.Date;

public class Employee {

	private int id;
	private String name;
	private Date date;

	public Employee(int id, String name,Date date) {
		super();
		this.id = id;
		this.name = name;
		this.date=date;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", date=" + date + "]";
	}

	
}
