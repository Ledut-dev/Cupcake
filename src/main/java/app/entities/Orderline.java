package app.entities;

public class Orderline {
    private CupcakeBottom cupcakeBottom;
    private CupcakeTop cupcakeTop;
    private int quantity;

    public Orderline(CupcakeBottom cupcakeBottom, CupcakeTop cupcakeTop, int quantity){
        this.cupcakeBottom = cupcakeBottom;
        this.cupcakeTop = cupcakeTop;
        this.quantity = quantity;
    }

    public String getCupcakeTop() {
        return cupcakeTop.getFlavor();
    }

    public String getcupcakeBottom() {
        return cupcakeBottom.getFlavor();
    }

    public double getIndividualPrice() {
        return cupcakeBottom.getPrice() + cupcakeTop.getPrice();
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return getIndividualPrice() * quantity;
    }
}
