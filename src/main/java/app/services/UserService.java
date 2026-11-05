package app.services;

import app.entities.Customer;
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

	public boolean checkIsEmailAvailable(String email) {
		return userMapper.checkEmailIsAvailable(email);
	}

    public User createUser(String email, String password) {
        User user = new Customer(email, password);
        userMapper.createUser(user);
        return user;
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

	public boolean checkIsPasswordValid(String psw) {
		if (psw.length()<3) return false; // Remember to update the error message in UserController when changing this.
		// More checks can be added
		return true;
	}



}
