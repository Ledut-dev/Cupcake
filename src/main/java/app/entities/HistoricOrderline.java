package app.entities;

public class HistoricOrderline extends Orderline {
    private double cupcakeBottomPrice;
    private double cupcakeTopPrice;

    public HistoricOrderline(CupcakeBottom cupcakeBottom, CupcakeTop cupcakeTop, int quantity) {
        super(cupcakeBottom, cupcakeTop, quantity);
    }
}
