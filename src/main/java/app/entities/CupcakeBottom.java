package app.entities;

public class CupcakeBottom {
    private String flavor;
    private double price;


    public CupcakeBottom(String flavor, double price){
    this.flavor = flavor;
    this.price = price;
    }


    public String getFlavor() {
        return flavor;
    }

    public double getPrice() {
        return price;
    }
}
