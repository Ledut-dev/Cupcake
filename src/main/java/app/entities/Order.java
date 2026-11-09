package app.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private Customer customer;
    private List<Orderline> orderlines;
    private OrderStatus status;
    private LocalDate orderDate;


    public Order(int id, OrderStatus status, LocalDate orderDate, LocalDate pickupDate){ //constructor to use in mapper method that returns a order
        this.id = id;
        this.status = status;
        orderlines = new ArrayList<>();
        this.orderDate = orderDate;
    }

    public List<Orderline> getOrderlines() {
        return orderlines;
    }

    public Order(){ //constructor to use when new user is logged in and cart is empty
        this.status=OrderStatus.CART;
        orderlines = new ArrayList<>();
    }

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

    public int getId() {
        return id;
    }

    public String getStatus() {
        return String.valueOf(status);
    }

    public int getSize() {
        int size = 0;
        for (Orderline orderline : orderlines) {
            size += orderline.getQuantity();
        }
        return size;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }
}
