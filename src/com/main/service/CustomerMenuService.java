package com.main.service;

import java.util.List;
import java.util.Scanner;

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
			if (price > c.getBalance()) {
				System.out.println("Error: Not enough balance");
				System.out.println("The price is " + price);
				System.out.println("Your current balance is " + c.getBalance());

				break;
			}
			else {
				db.insertPurchase(c, list2.get(0));
				System.out.println("You have completed the purchase");
			}

			break;

		case 3:
			break;

		case 4:
			System.out.println("*******Current Balance*******");
			System.out.println("$"+c.getBalance());
			System.out.println("1. Add balance");
			System.out.println("2. Return to customer menu");
			input = sc.nextInt();
			if (input == 1) {
				System.out.println("How much to add to balance?");
				double addition = sc.nextDouble();
				c.setBalance(c.getBalance() + addition);
				System.out.println("Your balance is now $"+c.getBalance());
				System.out.println("Returning to customer menu...");
			}
			break;

			
		case 5:
			break;
						
		default:
			System.out.println("Invalid input");
			break;
		}

	}
}
