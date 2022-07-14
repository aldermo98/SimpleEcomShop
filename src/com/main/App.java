package com.main;

import com.main.service.MenuService;

public class App {
	public static void main(String[]args) {
				
		MenuService menuService = new MenuService();
		
        while(true) {
            int input = menuService.displayMenuAndReadInput();
            if(input == 0) {
                System.out.println("Exiting...");
                break;
            }
            menuService.processMenuInput(input);
        }
	}
}