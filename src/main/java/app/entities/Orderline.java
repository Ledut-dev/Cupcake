package app.entities;

public class Orderline {
    private CupcakeBottom cupcakeBottom;
    private CupcakeTop cupcakeTop;
    private int quantity;

    public Orderline(CupcakeBottom cupcakeBottom, CupcakeTop cupcakeTop, int quantity){
        this.cupcakeBottom=cupcakeBottom;
        this.cupcakeTop=cupcakeTop;
        this.quantity=quantity;
    }


}
