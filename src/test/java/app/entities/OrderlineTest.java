package app.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderlineTest {

    Orderline orderline1;
    Orderline orderline2;
    Orderline orderline3;

    @BeforeEach
    void setUp() {

        CupcakeTop top1 = new CupcakeTop("Vanilla", 10.50);
        CupcakeTop top2 = new CupcakeTop("Chocolate", 15.75);
        CupcakeTop top3 = new CupcakeTop("banana", 20.30);

        CupcakeBottom bottom1 = new CupcakeBottom("plain", 10);
        CupcakeBottom bottom2 = new CupcakeBottom("red", 15);
        CupcakeBottom bottom3 = new CupcakeBottom("caramel", 20);

        orderline1 = new Orderline(bottom1, top1, 1);
        orderline2 = new Orderline(bottom2, top2, 11);
        orderline3 = new Orderline(bottom3, top3, 3);
    }



    @Test
    void getTotalPriceOrderline1() {

        assertEquals(20.5, orderline1.getTotalPrice());

    }
    @Test
    void getTotalPriceOrderline2(){

        assertEquals(338.25, orderline2.getTotalPrice());
    }

    @Test
    void getIndividualPriceOrderline3(){
        assertEquals(40.3, orderline3.getIndividualPrice());
    }
}