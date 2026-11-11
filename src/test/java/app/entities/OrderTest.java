package app.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    Order order= new Order(1, OrderStatus.PLACED, LocalDate.now());

    @BeforeEach
    void setUp() {

        CupcakeTop top1 = new CupcakeTop("Vanilla", 10.50);
        CupcakeTop top2 = new CupcakeTop("Chocolate", 15.75);
        CupcakeTop top3 = new CupcakeTop("banana", 20.30);

        CupcakeBottom bottom1 = new CupcakeBottom("plain", 10);
        CupcakeBottom bottom2 = new CupcakeBottom("red", 15);
        CupcakeBottom bottom3 = new CupcakeBottom("caramel", 20);

        Orderline orderline1 = new Orderline(bottom1, top1, 1);
        Orderline orderline2 = new Orderline(bottom2, top2, 1);
        Orderline orderline3 = new Orderline(bottom3, top3, 1);

        order.addOrderline(orderline1);
        order.addOrderline(orderline2);
        order.addOrderline(orderline3);
    }

    @Test
    void getTotal() {

        assertEquals(91.55, order.getTotal());

    }


    @Test
    void getSize() {

        assertEquals(3, order.getSize());
    }
}