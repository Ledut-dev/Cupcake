package app.persistence;

import app.entities.CupcakeBottom;
import app.entities.CupcakeTop;
import app.entities.Order;
import app.entities.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {
    ConnectionPool connectionPool;
    private static final Logger logger =
            LoggerFactory.getLogger(OrderMapper.class);

    public OrderMapper (ConnectionPool connectionPool){this.connectionPool=connectionPool;}



    public List<CupcakeBottom> allCakeBottoms(){

        List<CupcakeBottom> allCupcakeBottoms = new ArrayList<>();

        String query = """
                SELECT flavor, price
                FROM cupcake-bottoms""";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {
            try (ResultSet rs = stm.executeQuery();) {
                while (rs.next()) {
                    String flavor = rs.getString("flavor");
                    double price = rs.getDouble("price");
                    allCupcakeBottoms.add(new CupcakeBottom(flavor, price));

                }
            }
        } catch (SQLException e) {
            //logger.error(e.getMessage());
            //throw new DatabaseException("Fejl ved søgning efter hvilke brugere der er tilknyttet denne husholdning");
        }

        return allCupcakeBottoms;
    }

    public List<CupcakeTop> allCakeTops(){

        List<CupcakeTop> allCupcakeTops = new ArrayList<>();
        String query = """
                SELECT flavor, price
                FROM cupcake-tops""";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {
            try (ResultSet rs = stm.executeQuery();) {
                while (rs.next()) {
                    String flavor = rs.getString("flavor");
                    double price = rs.getDouble("price");
                    allCupcakeTops.add(new CupcakeTop(flavor, price));
                }
            }
        } catch (SQLException e) {
            //logger.error(e.getMessage());
            //throw new DatabaseException("Fejl ved søgning efter hvilke brugere der er tilknyttet denne husholdning");
        }

        return allCupcakeTops;
    }

    public List<Order> getOrderHistory() {
        List<Order> orderHistory = new ArrayList<>();
        // Get orderHistory from database
        return orderHistory;
    }
}
