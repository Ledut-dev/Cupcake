package app.entities;

public class CupcakeBottom {
    private String flavor;
    private double price;


    public CupcakeBottom(String type, double price){
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
