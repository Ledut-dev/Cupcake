package app.entities;

public class Customer extends User{

    public Customer(String email, String password, String name, double balance) {
        super(email, password, name, balance);
    }
    public Customer(int id, String email, String password, String name, double balance) {
        super(id, email, password, name, balance);
    }
    public Customer(int id, String email, String name) {
        super(id, email, name);
    }

}
