package app.persistence;

import app.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public List<Order> getPlacedOrders() {
        List<Order> placedOrders = new ArrayList<>();
        // Henter alle ordrelinjer, hvor ordren er PLACED
        String query = """
                SELECT o.order_id, o.status, o.order_date, o.pickup_date, c.customer_id, c.e-mail, c.name, l.quantity, t.flavor, t.price, b.flavor, b.price
                FROM orders o
                JOIN customers c ON o.customer_id = c.customer_id
                JOIN orderlines l ON o.order_id = l.order_id
                JOIN cupcake_tops t ON l.cupcake_top = t.flavor
                JOIN cupcake_bottoms b ON l.cupcake_bottom = b.flavor
                WHERE o.status = 'PLACED'
                ORDER BY o.order_id""";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {
            try (ResultSet rs = stm.executeQuery();) {
                Order currentOrder = new Order();   // Tom ordre, som placeholder til check
                while (rs.next()) {
                    if (currentOrder.getId() != rs.getInt("o.order_id")) {  // Checker om ordrelinjen tilhører en ny ordre
                        if (currentOrder.getSize() > 0) {                              // Undgår at tilføje placeholder-ordren til listen
                            placedOrders.add(currentOrder);                            // Skifter til ny ordre
                        }
                        int order_id = rs.getInt("o.order_id");
                        OrderStatus orderStatus = OrderStatus.valueOf(rs.getString("o.status"));
                        LocalDate order_date = rs.getDate("o.order_date").toLocalDate();
                        LocalDate pickup_date = rs.getDate("o.pickup_date").toLocalDate();
                        currentOrder = new Order(order_id, orderStatus, order_date, pickup_date);
                    }
                    CupcakeBottom cupcakeBottom = new CupcakeBottom(rs.getString("b.flavor"), rs.getInt("b.price"));
                    CupcakeTop cupcakeTop = new CupcakeTop(rs.getString("t.flavor"), rs.getInt("t.price"));
                    int quantity = rs.getInt("l.quantity");
                    currentOrder.addOrderline(new Orderline(cupcakeBottom, cupcakeTop, quantity));
                }

            }
        } catch (SQLException e) {
            //logger.error(e.getMessage());
            //throw new DatabaseException("Fejl ved søgning efter hvilke brugere der er tilknyttet denne husholdning");
        }
        return placedOrders;
    }

    public List<Order> getApprovedOrders() {
        List<Order> placedOrders = new ArrayList<>();
        // Henter alle ordrelinjer, hvor ordren er APPROVED
        String query = """
                SELECT o.order_id, o.status, c.customer_id, c.e-mail, c.name, l.quantity, t.flavor, t.price, b.flavor, b.price
                FROM orders o
                JOIN customers c ON o.customer_id = c.customer_id
                JOIN orderlines l ON o.order_id = l.order_id
                JOIN cupcake_tops t ON l.cupcake_top = t.flavor
                JOIN cupcake_bottoms b ON l.cupcake_bottom = b.flavor
                WHERE o.status = 'APPROVED'
                ORDER BY o.order_id""";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement stm = connection.prepareStatement(query)) {
            try (ResultSet rs = stm.executeQuery();) {
                Order currentOrder = new Order();   // Tom ordre, som placeholder til check
                while (rs.next()) {
                    if (currentOrder.getId() != rs.getInt("o.order_id")) {  // Checker om ordrelinjen tilhører en ny ordre
                        if (currentOrder.getSize() > 0) {                              // Undgår at tilføje placeholder-ordren til listen
                            placedOrders.add(currentOrder);                            // Skifter til ny ordre
                        }
                        int order_id = rs.getInt("o.order_id");
                        OrderStatus orderStatus = OrderStatus.valueOf(rs.getString("o.status"));
                        LocalDate order_date = rs.getDate("o.order_date").toLocalDate();
                        LocalDate pickup_date = rs.getDate("o.pickup_date").toLocalDate();
                        currentOrder = new Order(order_id, orderStatus, order_date, pickup_date);
                    }
                    CupcakeBottom cupcakeBottom = new CupcakeBottom(rs.getString("b.flavor"), rs.getInt("b.price"));
                    CupcakeTop cupcakeTop = new CupcakeTop(rs.getString("t.flavor"), rs.getInt("t.price"));
                    int quantity = rs.getInt("l.quantity");
                    currentOrder.addOrderline(new Orderline(cupcakeBottom, cupcakeTop, quantity));
                }

            }
        } catch (SQLException e) {
            //logger.error(e.getMessage());
            //throw new DatabaseException("Fejl ved søgning efter hvilke brugere der er tilknyttet denne husholdning");
        }
        return placedOrders;
    }
}
