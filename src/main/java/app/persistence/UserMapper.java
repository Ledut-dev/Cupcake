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

    public User getUserByEmail(String email) throws DatabaseException {
        User user = null;
        String query = "SELECT email, password, name FROM customers WHERE email = '?'";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                String password = rs.getString("password");
                String name = rs.getString("name");
                user = new Customer(email, password, name);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke hente brugere: " + e.getMessage());
        }

        query = "SELECT email, password, name FROM admins WHERE email = '?'";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            //Check for if there even is any users associated with this email. NEEDS to be in the try-statement for the LAST query, since it the code only gets here if there were no users found in earlier tables.
            //rs.isBeforeFirst returns false if it either already has read a row, OR if there are no rows in the ResultSet
            if(!rs.isBeforeFirst()){
                throw new DatabaseException("Ingen brugere med denne e-mail fundet");
            }
            while (rs.next()) {
                String password = rs.getString("password");
                String name = rs.getString("name");
                user = new Admin(email, password, name);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke hente brugere: " + e.getMessage());
        }

        return user;
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
