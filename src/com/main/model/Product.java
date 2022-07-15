package com.main.model;

public class Product {

	private int id;
	private String productName;
	private int quantity;
	private double price;
	private int vendor_id;
	
	public Product() {}
	
	public Product(int id, String productName, int quantity, double price, int vendor_id) {
		super();
		this.id = id;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
		this.vendor_id = vendor_id;
	}
	
	public Product(String productName, int quantity, double price, int vendor_id) {
		super();
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
		this.vendor_id = vendor_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(int vendor_id) {
		this.vendor_id = vendor_id;
	}

	@Override
	public String toString() {
		return String.format("| id: %-3d | productName: %-15s | quantity: %-6d | price: %-8.2f | vendor_id: %-3d |", 
				id, productName, quantity, price, vendor_id);
	}
	
}
