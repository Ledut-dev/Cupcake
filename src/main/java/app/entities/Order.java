package app.entities;

import java.util.ArrayList;

public class Order {
    private int id;
    private List<Orderline> items;
    private OrderStatus status;


    public Order(int id, OrderStatus status){ //constructor to use in mapper method that returns a order
        this.id=id;
        this.status=status;
        items= new ArrayList<>();
    }

    public ArrayList<Orderline> getOrderlines() {
        return orderlines;
    }

    public Order(OrderStatus status){ //constructor to use when new user is logged in and cart is empty
        this.status=OrderStatus.CART;
        items= new ArrayList<>();
    }

    public void addOrderline (Orderline orderline){
        orderlines.add(orderline);
    }
}
