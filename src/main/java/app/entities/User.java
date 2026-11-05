package app.entities;

public abstract class User {
    private String email;
    private String name;
    private String password;
    private double balance;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
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
}
