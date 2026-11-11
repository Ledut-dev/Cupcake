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

    // Constructors
    public Order(int id, OrderStatus status, LocalDate orderDate){ //constructor to use in mapper method that returns a order
        this.id = id;
        this.status = status;
        orderlines = new ArrayList<>();
        this.orderDate = orderDate;
    }

    public Order(){ //constructor to use when new user is logged in and cart is empty
        this.status=OrderStatus.CART;
        orderlines = new ArrayList<>();
    }

    // Getters/setters
    public List<Orderline> getOrderlines() {
        return orderlines;
    }

    public void setOrderlines(List<Orderline> orderlines) {
        this.orderlines = orderlines;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return String.valueOf(status);
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        }

    public Order(){ //constructor to use when new user is logged in and cart is empty
        this.status = OrderStatus.CART;
        orderlines = new ArrayList<>();
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
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

    public int getSize() {
        int size = 0;
        for (Orderline orderline : orderlines) {
            size += orderline.getQuantity();
        }
        return size;
    }
}
