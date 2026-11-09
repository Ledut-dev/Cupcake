package app.entities;

public class Customer extends User{

    public Customer(String email, String password, String name) {
        super(email, password, name);
    }
    public Customer(int id, String email, String password, String name) {
        super(id, email, password, name);
    }

}
