package com.main.model;

public class Vendor {
	private int id;
	private String vendorName;
	private String password;
	private double balance;
	public Vendor() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Vendor(int id, String vendorName, String password, double balance) {
		super();
		this.id = id;
		this.vendorName = vendorName;
		this.password = password;
		this.balance = balance;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
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
		return "Vendor [id=" + id + ", vendorName=" + vendorName + ", password=" + password + ", balance=" + balance
				+ "]";
	}
	
}
