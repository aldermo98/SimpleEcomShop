package com.main.model;

public class Customer {
	private int id;
	private String name;
	private String password;
	private double balance;
	
	
	
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Customer(int id, String name, String password, double balance) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.balance = balance;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", password=" + password + ", balance=" + balance + "]";
	}
	
	
	
	
}
