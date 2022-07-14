package com.main.service;

import java.util.Scanner;

import com.main.db.DB;

public class VendorMenuService {

	private DB db = new DB();
	private Scanner sc = new Scanner(System.in);

	public int displayVendorMenuAndReadInput() {
		System.out.println("******Vendor Main Menu******");
		System.out.println("1. View/Approve orders");
		System.out.println("2. Check inventory");
		System.out.println("3. Check balance");
		System.out.println("4. View order history");
		System.out.println("6. Logout");
		System.out.println("0. Exit");

		int input = sc.nextInt();
		return input;
	}

	public void processMenuInput(int input) {
		switch(input) {
			case 1:
				System.out.println("***View/Approve orders***");
				break;
				
			case 2:
				System.out.println("***Check inventory***");
				System.out.println("Enter 1=Restock item, 2=Add new item, 0=Back to main menu");
				System.out.println(db.fetchInventoryByVendor());
				System.out.println("Enter 1=Restock item, 2=Add new item, 0=Back to main menu");
				
				input = sc.nextInt();
				
				if(input == 1) {
					
				}else if(input == 2) {
					
				}else if(input == 0) {
					break;
				}else {
					System.out.println("Please enter a valid input.");
				}
				
				sc.next();
				break;
				
			case 3:
				System.out.println("***Check balance***");
				break;
				
			case 4:
				System.out.println("***View order history***");
				break;
				
			case 5:
				System.out.println("***Logout***");
				break;
				
			case 0:
				System.out.println("Goodbye...");
				System.exit(0);
				break;
		
			default: System.out.println("Enter a valid input.");
		}
		displayVendorMenuAndReadInput();
	}
}
