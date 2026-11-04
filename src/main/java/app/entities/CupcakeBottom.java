package app.entities;

public class CupcakeBottom {
    private String type;
    private double price;


    public CupcakeBottom(String type, double price){
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
