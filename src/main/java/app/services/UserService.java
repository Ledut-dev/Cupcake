package app.services;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;

public class UserService {
    private ConnectionPool connectionPool;
    private UserMapper userMapper;

    public UserService(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        userMapper = new UserMapper(connectionPool);
    }

    public User login(String email, String password) {

        try {
            User user = userMapper.getUserByEmail(email);

            if (!user.getEmail().equalsIgnoreCase(email) || !user.getPassword().equals(password)) {
                user = null;
            }
            return user;
        }
        catch (DatabaseException e){
            System.out.println("Error: " + e);
        }

        return null;
    }

    public boolean isUserAdmin(User user){
        boolean isAdmin = false;

        if (user.getClass().getSimpleName().equalsIgnoreCase("admin")) {
            isAdmin = true;
        }
        return isAdmin;
    }

}
