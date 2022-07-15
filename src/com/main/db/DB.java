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
import com.main.model.Orders;
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
	
	public void insertPurchase(Customer customer, Product product, int newQuantity) {
		dbConnect();
		 String sql="insert into product_customer"
		 		+ "(product_id, productName, quantity, price, customer_id,vendor_id, approval_status) "
		 		+ "values (?,?,?,?,?,?,?)";
		 
		 try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, product.getId());
			pstmt.setString(2, product.getProductName());
			pstmt.setInt(3, product.getQuantity());
			pstmt.setDouble(4, product.getPrice());
			pstmt.setInt(5, customer.getId());
			pstmt.setInt(6, product.getVendor_id());
			pstmt.setBoolean(7, false);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 
		//update inventory quantity
		sql="update product set quantity=? where id=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, newQuantity);
			pstmt.setInt(2, product.getId());
			
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
			while(rst.next()) {
				c.setId(rst.getInt("id"));
				c.setName(name);
				c.setBalance(rst.getDouble("balance"));
				c.setPassword(rst.getString("password"));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		dbClose();
		return c;
	}
	
	public Product getProduct(String name) {
		dbConnect();
		String sql="select * from product where productName=?";
		Product p=new Product();
		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			ResultSet rst = pstmt.executeQuery();
			while(rst.next()) {
				p=new Product(
					rst.getInt("id"),
					name,
					rst.getInt("quantity"),
					rst.getInt("price"),
					rst.getInt("vendor_id")
				);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		dbClose();
		return p;
	}
	
	public Vendor getVendor(String name) {
		dbConnect();
		String sql="select * from vendor where vendorName=?";
		Vendor v = new Vendor();
		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			ResultSet rst = pstmt.executeQuery();
			while(rst.next()) {
				v.setId(rst.getInt("id"));
				v.setVendorName(name);
				v.setBalance(rst.getDouble("balance"));
				v.setPassword(rst.getString("password"));
			}
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		dbClose();
		return v;
	}
	
	public void addProduct(Product product) {
		 dbConnect();
		 String sql="insert into product(productName,quantity,price,vendor_id) "
		 		+ "values (?,?,?,?)";
		 
		 try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, product.getProductName());
			pstmt.setInt(2, product.getQuantity());
			pstmt.setDouble(3, product.getPrice());
			pstmt.setInt(4, product.getVendor_id());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 
		 dbClose();
	}

	public List<Product> fetchInventoryByVendor(Vendor v) {
		dbConnect();
		List<Product> list = new ArrayList<>();
		String sql = "select * from product where vendor_id=" + v.getId();
				
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
		for(Product p : list) System.out.println(p.toString());
		return list;
	}
	
	public void updateInventory(String productName, int newQuantity) {
		dbConnect();
		String sql = "UPDATE product SET quantity=" + newQuantity + " WHERE productName=?";
				
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, productName);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
	}

	public List<Orders> fetchVendorOrderHistory(Vendor v) {
		dbConnect();
		List<Orders> list = new ArrayList<>();
		String sql = "select * from product_customer where vendor_id=?";
				
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, v.getId());
			ResultSet  rst = pstmt.executeQuery();
			
			while(rst.next()) {
				list.add(
					new Orders(
						rst.getInt("product_id"), 
						rst.getString("productName"), 
						rst.getInt("quantity"), 
						rst.getDouble("price"),
						rst.getInt("vendor_id"),
						rst.getInt("customer_id"),
						rst.getBoolean("approval_status")
					)
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		return list;
	}

	public void updateCustomerBalance(Customer c) {
		dbConnect();
		String sql = "UPDATE customer SET balance=? WHERE id=?";
				
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setDouble(1, c.getBalance());
			pstmt.setInt(2, c.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		
	}

	public List<Orders> fetchCustomerOrderHistory(Customer c) {
		dbConnect();
		List<Orders> list = new ArrayList<>();
		String sql = "select * from product_customer where customer_id=?";
				
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, c.getId());
			ResultSet  rst = pstmt.executeQuery();
			
			while(rst.next()) {
				list.add(
					new Orders(
						rst.getInt("product_id"), 
						rst.getString("productName"), 
						rst.getInt("quantity"), 
						rst.getDouble("price"),
						rst.getInt("vendor_id"),
						rst.getInt("customer_id"),
						rst.getBoolean("approval_status")
					)
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		return list;
	}

	public void updatePurchase(int cust_id, Product p, String updateVal) {
		dbConnect();
		String sql;
		if(updateVal.equals("x")) {
			sql="delete from product_customer where customer_id=? AND productName=?";
			try {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, cust_id);
				pstmt.setString(2, p.getProductName());
				
				pstmt.executeUpdate();
				System.out.println("Success, "+p.getProductName()+" order cancelled...");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			sql="update product_customer set quantity=?, price=? where customer_id=? AND productName=?";
			try {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(updateVal));
				pstmt.setDouble(2, p.getPrice()*Integer.parseInt(updateVal));
				pstmt.setInt(3, cust_id);
				pstmt.setString(4, p.getProductName());
				
				pstmt.executeUpdate();
				System.out.println("Success, "+updateVal+" item added to your purchase...");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			//update inventory quantity
			sql="update product set quantity=? where id=?";
			try {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(updateVal));
				pstmt.setInt(2, p.getId());
				
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 
		 dbClose();
		
	}

	public void approveOrders(String inp, int vendor_id, String rows) {
		dbConnect();
		String sql;
		if(rows.equalsIgnoreCase("all")) {
			sql="update product_customer set approval_status=? where vendor_id=?";
			try {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setBoolean(1, true);
				pstmt.setInt(2, vendor_id);
				
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			sql="update product_customer set approval_status=? where vendor_id=? AND product_id=?";
			try {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setBoolean(1, true);
				pstmt.setInt(2, vendor_id);
				pstmt.setInt(3, Integer.parseInt(rows));
				
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 
		 dbClose();
		
	}
	
}