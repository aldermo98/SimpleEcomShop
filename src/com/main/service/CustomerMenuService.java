package com.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.main.db.DB;
import com.main.model.Customer;
import com.main.model.Orders;
import com.main.model.Product;

public class CustomerMenuService {

	private Scanner sc = new Scanner(System.in);
	DB db = new DB();

	public int displayCustomerMenuAndReadInput() {
		System.out.println("******Customer Main Menu******");
		System.out.println("1. View Inventory");
		System.out.println("2. Buy Item");
		System.out.println("3. Update Purchase");
		System.out.println("4. View/Edit Balance");
		System.out.println("5. View Past Purchases");
		System.out.println("6. Logout");
		System.out.println("7. E-Commerce Main Menu");
		System.out.println("0. Exit");

		int input = sc.nextInt();
		return input;
	}

	public void processMenuInput(int customerInput, Customer c) {

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
			System.out.println("******Buy Item******");
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

			double price = inputQuantity * list2.get(0).getPrice();
			list2.get(0).setPrice(price);
			if (price > c.getBalance()) {
				System.out.println("Error: Not enough balance");
				System.out.println("The price is " + price);
				System.out.println("Your current balance is " + c.getBalance());

				break;
			} else {
				// update customerBalance
				c.setBalance(c.getBalance()-price);

				try {
					db.updateCustomerBalance(c);
					System.out.println("Your balance is now $" + c.getBalance());
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
				db.insertPurchase(c, list2.get(0), quantity-inputQuantity);
				System.out.println("You have completed the purchase");
			}

			break;

		case 3:
			System.out.println("***Update Purchase***");
			try {
				for (Orders o : db.fetchCustomerOrderHistory(c)) {
					System.out.println(o.toString());
					System.out.println(
							"--------------------------------------------------------------------------------------------------------------------------------------------");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			System.out.println("Enter the product name of which you would like to cancel or update: ");
			String productName = sc.next();

			System.out.println("Enter 'x' to cancel this order or a new quantity for this order: ");
			String updateVal = sc.next();

			Product p = db.getProduct(productName);

			db.updatePurchase(c.getId(), p, updateVal);

			break;

		case 4:
			System.out.println("*******Current Balance*******");
			System.out.println("$" + c.getBalance());
			System.out.println("1. Add balance");
			System.out.println("2. Return to customer menu");
			input = sc.nextInt();
			if (input == 1) {
				System.out.println("How much to add to balance?");
				double addition = sc.nextDouble();
				c.setBalance(c.getBalance() + addition);

				try {
					db.updateCustomerBalance(c);
					System.out.println("Your balance is now $" + c.getBalance());
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				System.out.println("Returning to customer menu...");
			}
			break;

		case 5:
			System.out.println("***View Past Purchase***");
			try {
				for (Orders o : db.fetchCustomerOrderHistory(c)) {
					System.out.println(o.toString());
					System.out.println(
							"--------------------------------------------------------------------------------------------------------------------------------------------");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			break;

		default:
			System.out.println("Invalid input");
			break;
		}

	}
}
