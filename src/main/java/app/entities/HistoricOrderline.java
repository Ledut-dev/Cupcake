package app.entities;

public class HistoricOrderline extends Orderline {
    private double cupcakeBottomPrice;
    private double cupcakeTopPrice;

    public HistoricOrderline(CupcakeBottom cupcakeBottom, CupcakeTop cupcakeTop, int quantity,
                             double cupcakeBottomPrice, double cupcakeTopPrice) {
        super(cupcakeBottom, cupcakeTop, quantity);
        this.cupcakeBottomPrice = cupcakeBottomPrice;
        this.cupcakeTopPrice = cupcakeTopPrice;
    }

    @Override public double getTotalPrice() {
        return (cupcakeBottomPrice + cupcakeTopPrice) * getQuantity();
    }
}
