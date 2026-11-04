package app.persistence;

import app.entities.User;
import java.sql.*;

public class UserMapper {
	private ConnectionPool connectionPool;

	public UserMapper(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}

	public void createUser(User user) {
		String query = "INSERT INTO customers (e-mail, name, password, balance)" +
				"VALUES (?, ?, ?, ?)";
		try (Connection connection = connectionPool.getConnection();
		PreparedStatement stm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			stm.setString(1, user.getEmail());
			stm.setString(2, null);
			stm.setString(3, user.getPassword());
			stm.setDouble(4, 0);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
