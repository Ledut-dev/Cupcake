package app.persistence;

import app.entities.Admin;
import app.entities.Customer;
import app.entities.User;
import java.sql.*;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

	//Get user by email, first query for the customers table, afterward admins. Creates either a Customer or Admin object.
    public User getUserByEmail(String email) throws DatabaseException {
        User user = null;
        String query = """
        SELECT email, password, name, customer_id, balance
        FROM customers
        WHERE email = ?""";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                String password = rs.getString("password");
                String name = rs.getString("name");
				int id = rs.getInt("customer_id");
				double balance = rs.getDouble("balance");
                user = new Customer(id, email, password, name, balance);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke hente brugere: " + e.getMessage());
        }

		query = """
				SELECT email, password, name, admin_id
				FROM admins
				WHERE email = ?""";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String password = rs.getString("password");
                String name = rs.getString("name");
				int id = rs.getInt("admin_id");
                user = new Admin(id, email, password, name);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke hente brugere: " + e.getMessage());
        }

		//Throws a database exception regarding no entry/user with this email found.
		if(user == null){
			throw new DatabaseException("Ingen brugere med denne e-mail fundet");
		}

        return user;
    }

	public User createUser(User user) throws DatabaseException {
	String query = "INSERT INTO customers (email, name, password, balance) VALUES (?, ?, ?, ?)";
		try (Connection connection = connectionPool.getConnection();
		PreparedStatement stm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			stm.setString(1, user.getEmail());
			stm.setString(2, user.getName());
			stm.setString(3, user.getPassword());
			stm.setDouble(4, user.getBalance());
			int insertCount = stm.executeUpdate();
			System.out.println("insertCount = " + insertCount);
			try (ResultSet rs = stm.getGeneratedKeys()) {
				if (rs.next()) {
					user.setId(rs.getInt("user_id"));
				} else throw new DatabaseException("argument kunne ikke oprettes");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return user;
	}
}
