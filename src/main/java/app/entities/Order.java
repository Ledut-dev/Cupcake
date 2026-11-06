package app.entities;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private List<Orderline> orderlines;
    private OrderStatus status;
    private User customer;
    private String orderDate;
    private String pickupDate;

    // Getters/setters
    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public List<Orderline> getOrderlines() {
        return orderlines;
    }

    // Constructors
    public Order(int id, OrderStatus status){ //constructor to use in mapper method that returns a order
        this.id = id;
        this.status = status;
        orderlines = new ArrayList<>();
    }

    public Order() { //constructor to use when new user is logged in and cart is empty
        this.status=OrderStatus.CART;
        orderlines = new ArrayList<>();
    }

    // Methods
    public void addOrderline (Orderline orderline){
        orderlines.add(orderline);
    }

    public double getTotal() {
        double total = 0;
        for (Orderline orderline : orderlines) {
            total += orderline.getTotalPrice();
        }
        return total;
    }
}
