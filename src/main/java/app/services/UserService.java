package app.services;

import app.entities.User;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;

public class UserService {
	private ConnectionPool connectionPool;
	private UserMapper userMapper;

	public UserService(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
		userMapper = new UserMapper(connectionPool);
	}

	public User createUser(String email, String password) {
		User user = new User(email, password);
		userMapper.createUser(user);
		return user;
	}
}
