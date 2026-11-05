package app.entities;

public class CupcakeTop {
    private String flavor;
    private double price;


    public CupcakeTop (String type, double price){
    this.flavor =type;
    this.price=price;
    }


    public String getFlavor() {
        return flavor;
    }

    public double getPrice() {
        return price;
    }
}
