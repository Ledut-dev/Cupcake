package app.entities;

public class Admin extends User{

    public Admin(String email, String password, String name) {
        super(email, password, name);
    }

    public Admin(int id, String email, String password, String name) {
        super(id, email, password, name);
    }
}
