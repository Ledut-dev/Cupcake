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

    public CupcakeTop getCupcakeTop() {
        return cupcakeTop;
    }

    public CupcakeBottom getCupcakeBottom() {
        return cupcakeBottom;
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

    @Override
    public String toString() {
        return cupcakeBottom.getFlavor() + " " + cupcakeTop.getFlavor() + " cupcake (" + quantity + " styk)";
    }
}
