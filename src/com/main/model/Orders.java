package com.main.model;

public class Orders {
	private int product_id;
	private String product_name;
	private int quantity;
	private double price;
	private int vendor_id;
	private int customer_id;
	private boolean approval_status;

	public Orders(int product_id, String product_name, int quantity, double price, int vendor_id, int customer_id,
			boolean approval_status) {
		super();
		this.product_id = product_id;
		this.product_name = product_name;
		this.quantity = quantity;
		this.price = price;
		this.vendor_id = vendor_id;
		this.customer_id = customer_id;
		this.approval_status = approval_status;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
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

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public boolean isApproval_status() {
		return approval_status;
	}

	public void setApproval_status(boolean approval_status) {
		this.approval_status = approval_status;
	}

	@Override
	public String toString() {
		return String.format("| product_id: %-3d | product_name: %-15s | quantity: %-3d | price: %-8.2f | vendor_id: %d | customer_id: %d | approval_status: %s |", 
				product_id, product_name, quantity, price, vendor_id, customer_id, approval_status);
	}
	
}
