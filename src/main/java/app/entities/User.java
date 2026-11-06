package app.entities;

public abstract class User {
    private int id;
    private String email;
    private String name;
    private String password;
    private double balance;
    private Order cart;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

	public void setEmail(String email) {
		this.email = email;
	}

    public String getName() {
        return this.name;
    }

	public void setName(String name) {
		this.name = name;
	}

    public String getPassword() {
        return this.password;
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

    public int getId() {return id;}
}
