package app.entities;

public class Admin extends User{

    public Admin(String email, String password, String name, double balance) {
        super(email, password, name, balance);
    }

    public Admin(int id, String email, String password, String name, double balance) {
        super(id, email, password, name, balance);
    }
}
