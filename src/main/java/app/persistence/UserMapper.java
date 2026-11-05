package app.persistence;

import app.entities.User;
import java.sql.*;

public class UserMapper {
	private ConnectionPool connectionPool;

	public UserMapper(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}

	public boolean checkEmailIsAvailable(String email) {
		String query = "SELECT email FROM customers WHERE email = '" + email + "'";
		try (Connection connection = connectionPool.getConnection();
		PreparedStatement stm = connection.prepareStatement(query);
		ResultSet rs = stm.executeQuery()) {
			boolean isAvailable = true;
			while (rs.next()) {
				isAvailable = false;
			}
			return isAvailable;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public void createUser(User user) {
		String query = "INSERT INTO customers (email, name, password, balance) VALUES (?, ?, ?, ?)";
		try (Connection connection = connectionPool.getConnection();
		PreparedStatement stm = connection.prepareStatement(query)) {
			stm.setString(1, user.getEmail());
			stm.setNull(2, 12); // 12 denotes the sql datatype defined in java.sql.Types, in this case VARCHAR
			stm.setString(3, user.getPassword());
			stm.setNull(4, 8); // 8 denotes the sql datatype defined in java.sql.Types, in this case DOUBLE
			int insertCount = stm.executeUpdate();
			System.out.println("insertCount = " + insertCount);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
