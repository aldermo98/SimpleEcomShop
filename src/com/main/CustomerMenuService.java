package com.main;

import java.util.Scanner;

public class CustomerMenuService {

	private Scanner sc = new Scanner(System.in);

	public int displayCustomerMenuAndReadInput() {
		System.out.println("******Customer Main Menu******");
		System.out.println("1. View Items");
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

	public void processMenuInput(int customerInput) {
		// TODO Auto-generated method stub
		switch(customerInput) {
		case 1:
			
			break;
		case 2:
			break;
			
		case 3:
			break;
			
		case 4:
			break;
			
		
		}
	}
}
