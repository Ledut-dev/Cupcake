package app.entities;

import app.persistence.ConnectionPool;
import app.services.OrderService;

public class Orderline {
    private String topping;
    private String bottom;
    private int individualPrice;
    private int quantity;

    public Orderline(String topping, String bottom, int quantity) {
        this.topping = topping;
        this.bottom = bottom;
        this.individualPrice = 25;
        this.quantity = quantity;
    }

    public String getTopping() {
        return topping;
    }

    public String getBottom() {
        return bottom;
    }

    public int getIndividualPrice() {
        return individualPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return individualPrice * quantity;
    }
}
