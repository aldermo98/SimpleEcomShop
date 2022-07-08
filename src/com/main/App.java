package com.main;

import java.util.List;
import java.util.Scanner;

import com.main.db.DB;

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
        
        
		//DB db = new DB();
		
		
	}
}

