package app.services;

import app.persistence.ConnectionPool;
import app.persistence.UserMapper;

public class UserService {
	private ConnectionPool connectionPool;
	private UserMapper userMapper;

	public UserService(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
		userMapper = new UserMapper(connectionPool);
	}
}
