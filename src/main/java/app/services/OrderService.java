package app.services;

import app.entities.Order;
import app.entities.Orderline;
import app.persistence.ConnectionPool;

public class OrderService {
    private ConnectionPool connectionPool;

    public OrderService(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Order openOrder() {
        return new Order();
    }

    public void addToOrder(Order order, Orderline orderline) {
        order.addOrderline(orderline);
    }
}
