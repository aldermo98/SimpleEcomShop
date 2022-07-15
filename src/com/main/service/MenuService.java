package com.main.service;

import java.util.List;
import java.util.Scanner;

import com.main.db.DB;
import com.main.model.Customer;
import com.main.model.Product;
import com.main.model.Vendor;

public class MenuService {
	
	private Scanner sc = new Scanner(System.in);

	DB db = new DB();	

	public int displayMenuAndReadInput() {
		System.out.println("******E-Commerce Main Menu******");
		System.out.println("1. Customer Login");
		System.out.println("2. Vendor Login");
		System.out.println("3. Create new account");
		System.out.println("4. View inventory");
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
			String name = sc.next();
			System.out.println("Enter password: ");
			String pass = sc.next();
			//customer login
			if (customerLogin(name, pass)) {
				System.out.println("Welcome " + name);
				//go to customer Menu
				CustomerMenuService customerMenuService = new CustomerMenuService();
				while(true) {
		            int customerInput = customerMenuService.displayCustomerMenuAndReadInput();
		            if(customerInput == 0) {
		                System.out.println("Logging out...");
		                break;
		            }
		            
		            Customer c = db.getCustomer(name);
		            customerMenuService.processMenuInput(customerInput, c);

		        }
				
			}
			else {
				System.out.println("Incorrect login");
			}
			
			break;
		case 2:			
			System.out.println("******Vendor Login******");
			System.out.println("Enter vendor name: ");
			name = sc.next();
			System.out.println("Enter password: ");
			pass = sc.next();
			
			if (vendorLogin(name, pass)) {
				System.out.println("Welcome " + name);
				
				//go to vendor Menu
				VendorMenuService vendorMenuService = new VendorMenuService();
				while(true) {
		            int vendorInput = vendorMenuService.displayVendorMenuAndReadInput();
		            if(vendorInput == 0) {
		                System.out.println("Logging out...");
		                break;
		            }
		            
		            
		            Vendor v = db.getVendor(name);
		            vendorMenuService.processMenuInput(vendorInput, v);
		        }
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
			db.addNew(input);			
			
			break;
			
		case 4:
			System.out.println("******View Inventory******");
			System.out.println("1. View all");
			System.out.println("2. Filter results");
			System.out.println("Enter one of the above options: ");
			
			List<Product> list;
			input = sc.nextInt();
			if(input == 2) {
				System.out.println("Enter a column to filter by: ");
				String columnFilter = sc.next();
				
				System.out.println("Enter a string to filter by: ");
				String stringFilter = sc.next();
				
				list = db.fetchInventory(columnFilter, stringFilter);
			}else {
				list = db.fetchInventory();
			}
			
			System.out.println("------------------------------------------------------------------------------------------------");
			for(Product p : list) {
				System.out.println(p.toString());
				System.out.println("------------------------------------------------------------------------------------------------");
			}
			System.out.println();
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
			if (c.getName().equals(name)) {
				if (c.getPassword().equals(pass)) {
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
			if (v.getVendorName().equals(name)) {
				if (v.getPassword().equals(pass)) {
					return true;
				}
				else return false;
			}
		}
		return false;
	}
}
