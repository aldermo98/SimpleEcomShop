package com.main.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.main.model.Customer;
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

	public void insertCustomer(Customer customer) {
		 dbConnect();
		 String sql="insert into customer(name,password,balance) "
		 		+ "values (?,?,?)";
		 
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
			pstmt.setString(1, vendor.getVendorName());
			pstmt.setString(2, vendor.getPassword());
			pstmt.setDouble(3, vendor.getBalance());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 
		 dbClose();
	}
	
	public void insertPurchase(Customer customer, Product product) {
		dbConnect();
		 String sql="insert into product_customer"
		 		+ "(product_id, productName, quantity, price, customer_id,vendor_id) "
		 		+ "values (?,?,?,?,?,?)";
		 
		 try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, product.getId());
			pstmt.setString(2, product.getProductName());
			pstmt.setInt(3, product.getQuantity());
			pstmt.setDouble(3, product.getPrice());
			pstmt.setInt(4, customer.getId());
			pstmt.setInt(5, product.getVendor_id());
			
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
									  rst.getDouble("balance")
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
									  rst.getDouble("balance")
									  ));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		return list;
	}
	
	public void addNew(int input) {
		dbConnect();
		Scanner sc = new Scanner(System.in);
		if(input == 1) {//Customer
			System.out.println("Input name: ");
			String name = sc.next();
			System.out.println("Input password: ");
			String pass = sc.next();
			System.out.println("Input balance: ");
			double balance = sc.nextDouble();
			
			Customer c = new Customer();
			c.setName(name);
			c.setPassword(pass);
			c.setBalance(balance);
			
			insertCustomer(c);
		}

		else if(input == 2) {//Vendor
			System.out.println("Input name: ");
			String name = sc.next();
			System.out.println("Input password: ");
			String pass = sc.next();
			System.out.println("Input balance: ");
			double balance = sc.nextDouble();
			
			Vendor v = new Vendor();
			v.setVendorName(name);
			v.setPassword(pass);
			v.setBalance(balance);
			
			insertVendor(v);
		}
		else {
			System.out.println("Invalid input");
		}
		dbClose();
	}

	//case 4
	public List<Product> fetchInventory(String ...args) {
		dbConnect();
		List<Product> list = new ArrayList<>();
		String sql = "select * from product";
		
		if(args.length > 0) 
			sql = sql.concat(" where " + args[0] + " like \"%" + args[1] + "%\"");
		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet  rst = pstmt.executeQuery();
			
			while(rst.next()) {
				list.add(
					new Product(rst.getInt("id"), 
					rst.getString("productName"), 
					rst.getInt("quantity"), 
					rst.getDouble("price"),
					rst.getInt("vendor_id"))
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		return list;
	}
	
	public Customer getCustomer(String name) {
		dbConnect();
		String sql="select * from customer where name=?";
		Customer c = new Customer();
		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			
			ResultSet rst = pstmt.executeQuery();
			c.setId(rst.getInt("id"));
			c.setName(name);
			c.setBalance(rst.getDouble("balance"));
			c.setPassword("password");
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		dbClose();
		return c;
	}
	
	public Vendor getVendor(String name) {
		dbConnect();
		String sql="select * from customer where name=?";
		Vendor v = new Vendor();
		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			
			ResultSet rst = pstmt.executeQuery();
			v.setId(rst.getInt("id"));
			v.setVendorName(name);
			v.setBalance(rst.getDouble("balance"));
			v.setPassword("password");
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		dbClose();
		return v;
	}
}