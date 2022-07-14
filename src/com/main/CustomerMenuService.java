package com.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthSeparatorUI;

import com.main.db.DB;
import com.main.model.Customer;
import com.main.model.Product;

public class CustomerMenuService {

	private Scanner sc = new Scanner(System.in);
	DB db = new DB();

	public int displayCustomerMenuAndReadInput() {
		System.out.println("******Customer Main Menu******");
		System.out.println("1. View Inventory");
		System.out.println("2. Buy Item");
		System.out.println("3. Cancel Purchase");
		System.out.println("4. View/Edit Balance");
		System.out.println("5. View Past Purchases");
		System.out.println("6. Logout");
		System.out.println("7. E-Commerce Main Menu");
		System.out.println("0. Exit");

		int input = sc.nextInt();
		return input;
	}

	public void processMenuInput(int customerInput, String currentCustomerName) {
		// TODO Auto-generated method stub
		switch (customerInput) {
		case 1:
			System.out.println("******View Inventory******");
			System.out.println("1. View all");
			System.out.println("2. Filter results");
			System.out.println("Enter one of the above options: ");

			List<Product> list;
			int input = sc.nextInt();
			if (input == 2) {
				System.out.println("Enter a column to filter by: ");
				String columnFilter = sc.next();

				System.out.println("Enter a string to filter by: ");
				String stringFilter = sc.next();

				list = db.fetchInventory(columnFilter, stringFilter);
			} else {
				list = db.fetchInventory();
			}

			System.out.println(
					"------------------------------------------------------------------------------------------------");
			for (Product p : list) {
				System.out.println(p.toString());
				System.out.println(
						"------------------------------------------------------------------------------------------------");
			}
			System.out.println();
			break;

		case 2:
			sc.nextLine();
			System.out.println("Enter product name: ");
			String inp = sc.next();
			// Return list of that productName
			List<Product> list2;
			list2 = db.fetchInventory("productName", inp);
			System.out.println(
					"------------------------------------------------------------------------------------------------");
			for (Product p : list2) {
				System.out.println(p.toString());
				System.out.println(
						"------------------------------------------------------------------------------------------------");
			}
			// check if quantity is available
			int quantity = list2.get(0).getQuantity();
			if (quantity <= 0) {
				System.out.println("Error: Out of Stock");
				break;
			}

			System.out.println("Enter quantity: ");
			int inputQuantity = sc.nextInt();
			if (inputQuantity > quantity || inputQuantity == 0) {
				System.out.println("Error: Quantity not available");
				break;
			}

			// check if user can purchase it
			List<Customer> customers = db.fetchCustomers();
			Customer currentCustomer = new Customer();
			for (Customer c : customers) {
				if (c.getName() == currentCustomerName) {
					currentCustomer.setBalance(customers.get(0).getBalance());
					currentCustomer.setId(customers.get(0).getId());
					currentCustomer.setName(customers.get(0).getName());
					currentCustomer.setPassword(customers.get(0).getPassword());
				}
			}
			
			System.out.println("Customer id is "+currentCustomer.getId());

			double price = inputQuantity * list2.get(0).getPrice();
			if (price > currentCustomer.getBalance()) {
				System.out.println("Error: Not enough balance");
				System.out.println("The price is " + price);
				System.out.println("Your current balance is " + currentCustomer.getBalance());
				break;
			}
			else {
				db.insertPurchase(currentCustomer, list2.get(0));
				System.out.println("You have completed the purchase");
			}


			// Need a DB.java method to adjust balance

			break;

		case 3:
			break;

		case 4:
			break;

		default:
			System.out.println("Invalid input");
			break;
		}

	}
}
