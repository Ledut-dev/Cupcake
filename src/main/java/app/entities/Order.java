package app.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private List<Orderline> orderlines;
    private OrderStatus status;
    private Date orderDate;
    private Date pickupDate;
    private User user;


    public Order(int id, OrderStatus status){ //constructor to use in mapper method that returns a order
        this.id=id;
        this.status=status;
        orderlines = new ArrayList<>();
    }

    public List<Orderline> getOrderlines() {
        return orderlines;
    }

    public Order(){ //constructor to use when items are added to cart, but there is no cart.
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }


    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }
}
