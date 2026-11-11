package app.entities;

public class CupcakeTop {
    private String flavor;
    private double price;


    public CupcakeTop (String flavor, double price){
    this.flavor =flavor;
    this.price=price;
    }

    public CupcakeTop(String flavor) {
        this.flavor = flavor;
    }


    public String getFlavor() {
        return flavor;
    }

    public double getPrice() {
        return price;
    }
}
