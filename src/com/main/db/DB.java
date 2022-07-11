package com.main.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.main.model.Product;
import com.main.model.Vendor;

public class DB {
	Connection con; 
	
	public void dbConnect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			//System.out.println("Driver loaded..");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}  
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce_team1"
					,"root","Password123");
			//System.out.println("Connection Established..");
		} catch (SQLException e) {
			 
			e.printStackTrace();
		}
	}
	
	public void dbClose() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	/*
	 *  
	 */
	public void insertCustomer(Customer customer) {
		 dbConnect();
		 String sql="insert into customer(name,password,balance) "
		 		+ "values (?,?,?,?)";
		 
		 try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customer.getName());
			pstmt.setString(2, customer.getPassword());
			pstmt.setDouble(3, customer.getBalance());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 
		 dbClose();
	}
	public void insertVendor(Vendor vendor) {
		 dbConnect();
		 String sql="insert into vendor(VendorName,password,balance) "
		 		+ "values (?,?,?,?)";
		 
		 try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customer.getvendorName());
			pstmt.setString(2, customer.getPassword());
			pstmt.setDouble(3, customer.getBalance());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 
		 dbClose();
	}

	public List<Customer> fetchCustomers() {
		dbConnect();
		String sql="select * from customer";
		List<Customer> list = new ArrayList<>();
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet  rst = pstmt.executeQuery();
			
			while(rst.next()) {
				list.add(new Customer(rst.getInt("id"),
									  rst.getString("name"),
									  rst.getString("password"), 
									  rst.getDouble("balance"),
									  ));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		return list;
	}

	public List<Vendor> fetchVendors() {
		dbConnect();
		String sql="select * from vendor";
		List<Vendor> list = new ArrayList<>();
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet  rst = pstmt.executeQuery();
			
			while(rst.next()) {
				list.add(new Vendor(rst.getInt("id"),
									  rst.getString("vendorName"),
									  rst.getString("password"), 
									  rst.getDouble("balance"),
									  ));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		return list;
	}
	
}