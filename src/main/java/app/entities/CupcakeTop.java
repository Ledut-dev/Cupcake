package app.entities;

public class CupcakeTop {
    private String type;
    private double price;


    public CupcakeTop (String type, double price){
    this.type=type;
    this.price=price;
    }


    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }
}
