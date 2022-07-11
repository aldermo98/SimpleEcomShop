package com.main;

import java.util.List;
import java.util.Scanner;

import com.main.model.Customer;
import com.main.model.Vendor;

public class MenuService {
	
	private Scanner sc = new Scanner(System.in);

	public int displayMenuAndReadInput() {
		System.out.println("******E-Commerce Main Menu******");
		System.out.println("1. Customer Login");
		System.out.println("2. Vendor Login");
		System.out.println("3. Create new account");
		System.out.println("4. View as guest");
		System.out.println("0. Exit");
		System.out.println("Enter your choice: ");
		
		int input = sc.nextInt();
		
		return input;
	}

	
	public void processMenuInput(int input) {
	
		switch(input) {
		case 1:
			System.out.println("******Customer Login******");
			System.out.println("Enter customer name: ");
			sc.nextLine(); //skip this
			String name = sc.nextLine();
			System.out.println("Enter password: ");
			String pass = sc.next();
			//customer login
			if (customerLogin(name, pass)) {
				System.out.println("Welcome " + name);
				//go to customer Menu
			}
			else {
				System.out.println("Incorrect login");
			}
			
			break;
		case 2:
			System.out.println("******Vendor Login******");
			System.out.println("Enter vendor name: ");
			sc.nextLine(); //skip this
			name = sc.nextLine();
			System.out.println("Enter password: ");
			pass = sc.next();
			//vendor check
			if (vendorLogin(name, pass)) {
				System.out.println("Welcome " + name);
				//go to vendor Menu
			}
			else {
				System.out.println("Incorrect login");
			}
			
			break;
			
		case 3:
			System.out.println("******Account Creation******");
			System.out.println("1. Customer");
			System.out.println("2. Vendor");
			System.out.println("Enter which account to create: ");
			input = sc.nextInt();
			//db.addNew(input);
			
			
			break;
			
		case 4:
			System.out.println("******Displaying all products******");
			//System.out.println(productsDisplay);
			
			
			break;
		
		default:
			System.out.println("Invalid input");
			break;
		}
	}
	
	public boolean customerLogin(String name, String pass) {
		//call db.fetchCustomers into a list
		List<Customer> customerList = db.fetchCustomers();
		
		for (Customer c : customerList) {
			if (c.getName() == name) {
				if (c.getPassword() == pass) {
					return true;
				}
				else return false;
			}
		}
		return false;
	}
	
	public boolean vendorLogin(String name, String pass) {
		//call db.fetchVendors into a list
		List<Vendor> vendorList = db.fetchVendors();
		
		for (Vendor v : vendorList) {
			if (v.getVendorName() == name) {
				if (v.getPassword() == pass) {
					return true;
				}
				else return false;
			}
		}
		return false;
	}
}