package app.entities;

import java.util.ArrayList;

public class Order {
    private ArrayList<Orderline> orderlines;
    private String status;

    public Order() {
        this.orderlines = new ArrayList<>();
        this.status = "open";
    }

    public ArrayList<Orderline> getOrderlines() {
        return orderlines;
    }

    public int getTotal() {
        int total = 0;
        for (Orderline orderline : orderlines) {
            total += orderline.getTotalPrice();
        }
        return total;
    }

    public void addOrderline (Orderline orderline){
        orderlines.add(orderline);
    }
}
