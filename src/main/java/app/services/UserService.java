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
            //Grabs the user related to the entered email via the mapper, this is null if no user is found.
            User user = userMapper.getUserByEmail(email);

            //If either the email (which shouldn't ever be "wrong) or the password doesn't match with the fetched user, the user is set to null which denies login.
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

    //Check if the current User is of the "Admin" class, in which case it returns true. If the Users class is "Customer" it returns false.
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
