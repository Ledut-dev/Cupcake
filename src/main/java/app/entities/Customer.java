package app.entities;

public class Customer extends User{

    public Customer(String email, String password, String name) {
        super(email, password, name);
    }
    public Customer(String email, String password) {
        super(email, password);
    }
    public Customer(int id, String email, String name) {
        super(id, email, name);
    }
}
