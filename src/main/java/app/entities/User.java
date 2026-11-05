package app.entities;

import java.util.ArrayList;
import java.util.List;

public class User {
	private String email;
	private String name;
	private String password;
	private double balance;
	private Order cart;

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Order getCart() {
		return cart;
	}

	public void setCart(Order cart) {
		this.cart = cart;
	}
}
