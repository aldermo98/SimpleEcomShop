package com.main;

import java.util.Scanner;

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
			//db.customerValidate(name,pass)
			
			break;
		case 2:
			System.out.println("******Vendor Login******");
			System.out.println("Enter vendor name: ");
			sc.nextLine(); //skip this
			name = sc.nextLine();
			System.out.println("Enter password: ");
			pass = sc.next();
			//vendor check
			//db.vendorValidate(name,pass)
			
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
}
