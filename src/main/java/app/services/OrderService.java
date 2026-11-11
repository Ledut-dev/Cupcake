package app.services;


import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;

import java.util.List;


public class OrderService {
    private ConnectionPool connectionPool;
    private OrderMapper orderMapper;


    public OrderService(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        this.orderMapper = new OrderMapper(connectionPool);
    }

    public List<CupcakeTop> allCakeTops() {
        List<CupcakeTop> allCakeTops = null;
        try {
            allCakeTops = orderMapper.allCakeTops();
        } catch (DatabaseException d) {
            d.getMessage();
        }
        return allCakeTops;
    }

    public List<CupcakeBottom> allCakeBottoms() {
        List<CupcakeBottom> allCakeBottoms = null;
        try {
            allCakeBottoms = orderMapper.allCakeBottoms();
        } catch (DatabaseException d) {
            d.getMessage();
        }
        return allCakeBottoms;
    }

    public CupcakeBottom getCupcakeBottomByFlavor(String flavor) {
        return null;
    }

    public CupcakeTop getCupcakeTopByFlavor(String flavor) {
        return null;
    }

    public void saveOrder(Order order) {
        int orderId = 0;
        try {
            orderId = orderMapper.saveOrder(order);
        } catch (DatabaseException d) {
            d.getMessage();
        }
        saveOrderlines(orderId, order);
    }

    public void saveOrderlines(int orderId, Order order) {

        for (Orderline o : order.getOrderlines()) {
            try {
                orderMapper.saveOrderline(o, orderId);
            } catch (DatabaseException d) {
                d.getMessage();
            }
        }
    }

    public List<Order> getOrderHistory() {
        List<Order> orderHistory = null;
        try {
            orderHistory = orderMapper.getOrderHistory();
        } catch (DatabaseException e) {
            e.getMessage();
        }
        return orderHistory;
    }

    public List<Order> getPlacedOrders() {
        return orderMapper.getPlacedOrders();
    }

    public List<Order> getApprovedOrders() {
        return orderMapper.getApprovedOrders();
    }

    public void updateOrderStatus(int id, String status) {
        orderMapper.updateOrderStatus(id, status);
        if (status.equalsIgnoreCase("completed")){
            try{
                completeOrder(orderMapper.getOrderById(id));
            } catch (DatabaseException e) {
                e.getMessage();
            }
        }
    }

    public void completeOrder(Order order){

        try {
            int historicOrderId = orderMapper.saveToOrderHistory(order);
            System.out.println(historicOrderId + "Order saved to history");
            for (Orderline ol : order.getOrderlines()){
                System.out.println("in forloop");
                orderMapper.saveToOrderlineHistory(ol, historicOrderId);
            }

            orderMapper.removeOrder(order);

        } catch (DatabaseException e) {
            System.out.println(e.getMessage());
        }
    }
}
