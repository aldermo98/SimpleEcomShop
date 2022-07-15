package com.main.service;

import java.util.List;
import java.util.Scanner;

import com.main.db.DB;
import com.main.model.Orders;
import com.main.model.Product;
import com.main.model.Vendor;

public class VendorMenuService {

	private DB db = new DB();
	private Scanner sc = new Scanner(System.in);

	public int displayVendorMenuAndReadInput() {
		System.out.println("******Vendor Main Menu******");
		System.out.println("1. View/Approve orders");
		System.out.println("2. Check inventory");
		System.out.println("3. Update inventory");
		System.out.println("4. Check balance");
		System.out.println("0. Logout");

		int input = sc.nextInt();
		return input;
	}

	public void processMenuInput(int input, Vendor v) {
		switch(input) {
			case 1:
				System.out.println("***View/Approve orders***");
				
				try {
					List<Orders> list = db.fetchVendorOrderHistory(v);
					for(Orders o : list) {
						System.out.println(o.toString());
						System.out.println("------------------------------------------------------------------------------------------");
					}
					if(list.size() == 0) {
						System.out.println("There are no pending orders...");
						break;
					}else {
						System.out.println("Enter 'all' to approve all or the product_id for which to approve all orders on: ");
						System.out.println("Enter 0 to return to menu: ");
						String inp = sc.next();
						if(inp.equals("0")) break;
						db.approveOrders(inp, v.getId(), inp);
						System.out.println("Success, orders approved");
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
				
			case 2:
				System.out.println("***Check inventory***");
				
				try {
					List<Product> list = db.fetchInventoryByVendor(v);
					for(Product p : list) {
						System.out.println(p.toString());
						System.out.println("------------------------------------------------------------------------------------------");
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
				
			case 3:
				System.out.println("***Update inventory***");
				
				while(true) {
					System.out.println("1. Add product");
					System.out.println("2. Update product");
					System.out.println("Enter one of the above choices: ");
					input = sc.nextInt();
					System.out.println();
					if(input == 1) {
						System.out.println("Enter the name of the product you would like to add: ");
						String productName = sc.next();
						System.out.println("Enter the quantity of product available: ");
						int quantity = sc.nextInt();
						System.out.println("Enter the price of the product: ");
						double price = sc.nextDouble();
						Product p = new Product(productName, quantity, price, v.getId());
						try {
							db.addProduct(p);
							System.out.println("Success, "+productName+" added...");
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					}else if(input == 2) {
						System.out.println("Enter the name of the product you would like to update: ");
						String productName = sc.next();
						System.out.println("Enter the new quantity for " + productName + ": ");
						int newQuantity = sc.nextInt();
						
						try {
							db.updateInventory(productName, newQuantity);
							System.out.println("Success, "+productName+" updated...");
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}						
					}else {
						System.out.println("Please enter a valid choice: ");
						input = sc.nextInt();
					}
					break;
				}
				break;
			
			case 4:
				System.out.println("***Check balance***");
				System.out.println("$"+v.getBalance());
				System.out.println("1. Add balance");
				System.out.println("2. Return to vendor menu");
				input = sc.nextInt();
				while(true) {
					if (input == 1) {
						System.out.println("How much to add to balance?");
						double addition = sc.nextDouble();
						v.setBalance(v.getBalance() + addition);
						
						try {
							db.updateVendorBalance(v);
							System.out.println("Your balance is now $" + v.getBalance());
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						System.out.println("Returning to vendor menu...");
					}else if(input != 2){
						System.out.println("Please enter a valid choice: ");
						input = sc.nextInt();
					}
					break;
				}
				break;
		
			default: System.out.println("Not a valid input.");
		}
		//displayVendorMenuAndReadInput();
	}
}
