package app.persistence;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        String query="INSERT INTO orders (customer_id, status, order_date, pickup_date) VALUES(?, ?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {
             stm.setInt(1, order.getCustomer().getId());
             stm.setString(2, String.valueOf(order.getStatus()));
             stm.setDate(3, Date.valueOf(order.getOrderDate()));

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

    public void updateOrderStatus(int id, String status) {
        String query = """
                UPDATE orders
                SET status = ?
                WHERE order_id = ?;""";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {
            stm.setString(1, status);
            stm.setInt(2, id);
            stm.executeQuery();
        } catch (SQLException e) {
            //logger.error(e.getMessage());
            //throw new DatabaseException("Fejl ved søgning efter aktive ordrer");
        }
    }

    public List<Order> getPlacedOrders()   { return getOrdersByStatus(OrderStatus.PLACED); }
    public List<Order> getApprovedOrders() { return getOrdersByStatus(OrderStatus.APPROVED); }

    private List<Order> getOrdersByStatus(OrderStatus status) {
        List<Order> orders = new ArrayList<>();
        String query = """
            SELECT o.order_id, o.status, o.order_date, o.pickup_date,
                   c.costumer_id, c.email, c.name,
                   l.quantity,
                   t.top_flavor, t.price AS top_price,
                   b.bottom_flavor, b.price AS bottom_price
            FROM orders o
            JOIN customers c ON o.customer_id = c.costumer_id
            JOIN orderlines l ON o.order_id = l.order_id
            JOIN cupcake_tops t ON l.cupcake_top = t.top_flavor
            JOIN cupcake_bottoms b ON l.cupcake_bottom = b.bottom_flavor
            WHERE o.status = ?
            ORDER BY o.order_id
            """;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {
            stm.setString(1, status.name());

            try (ResultSet rs = stm.executeQuery()) {
                Order currentOrder = null;
                while (rs.next()) {
                    int orderId = rs.getInt("order_id");

                    if (currentOrder == null || currentOrder.getId() != orderId) {
                        LocalDate orderDate = rs.getDate("order_date").toLocalDate();
                        LocalDate pickupDate = rs.getDate("pickup_date").toLocalDate();

                        currentOrder = new Order(
                                orderId,
                                OrderStatus.valueOf(rs.getString("status")),
                                orderDate,
                                pickupDate);
                                currentOrder.setCustomer(new Customer(
                                rs.getInt("costumer_id"),
                                rs.getString("email"),
                                rs.getString("name")));
                        orders.add(currentOrder);
                    }

                    CupcakeBottom bottom = new CupcakeBottom(rs.getString("bottom_flavor"), rs.getDouble("bottom_price"));
                    CupcakeTop top = new CupcakeTop(rs.getString("top_flavor"), rs.getDouble("top_price"));
                    currentOrder.addOrderline(new Orderline(bottom, top, rs.getInt("quantity")));
                }
            }
        } catch (SQLException e) {
            //logger.error(e.getMessage());
            //throw new DatabaseException("Fejl ved søgning efter aktive ordrer");
        }
        return orders;
    }
}
