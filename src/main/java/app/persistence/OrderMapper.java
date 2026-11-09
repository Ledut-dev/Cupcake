package app.persistence;

import app.entities.*;
import app.exceptions.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {
    ConnectionPool connectionPool;
    private static final Logger logger =
            LoggerFactory.getLogger(OrderMapper.class);

    public OrderMapper (ConnectionPool connectionPool){this.connectionPool=connectionPool;}

    public int saveOrder(Order order) throws DatabaseException{
        int orderId = 0;

        String query="INSERT INTO orders (customer_id, status) VALUES(?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {
             stm.setInt(1, order.getUser().getId());
             stm.setString(2, String.valueOf(order.getStatus()));

            try (ResultSet rs = stm.getGeneratedKeys()) {
                if (rs.next()) {
                    orderId=rs.getInt("order_id");
                } else throw new DatabaseException("argument kunne ikke oprettes");
            }
        } catch (SQLException e) {
            throw new DatabaseException("kunne ikke gemme ordre");
        }
        return orderId;
    }

    public void saveOrderline (Orderline orderline, int orderId) throws DatabaseException {

        String query="INSERT INTO orderlines (order_id, cupcake_topping, cupcake_bottom, quantity) VALUES(?, ?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {
            stm.setInt(1, orderId);
            stm.setString(2, orderline.getCupcakeTop());
            stm.setString(3,  orderline.getCupcakeBottom());
            stm.setInt(4, orderline.getQuantity());

        } catch (SQLException e) {
            throw new DatabaseException("orderline kunne ikke gemmes");
        }
    }


    public List<CupcakeBottom> allCakeBottoms() throws DatabaseException{

        List<CupcakeBottom> allCupcakeBottoms = new ArrayList<>();

        String query = """
                SELECT flavor, price
                FROM cupcake_bottoms""";

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
            logger.error(e.getMessage());
            throw new DatabaseException("Fejl ved søgning efter cupcakebunde");
        }

        return allCupcakeBottoms;
    }

    public List<CupcakeTop> allCakeTops() throws DatabaseException{

        List<CupcakeTop> allCupcakeTops = new ArrayList<>();
        String query = """
                SELECT flavor, price
                FROM cupcake_tops""";

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
            logger.error(e.getMessage());
            throw new DatabaseException("Fejl ved søgning efter cupcaketoppings");
        }

        return allCupcakeTops;
    }
}
