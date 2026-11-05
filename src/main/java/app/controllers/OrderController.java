package app.controllers;

import app.entities.CupcakeBottom;
import app.entities.CupcakeTop;
import app.entities.Order;
import app.entities.Orderline;
import app.persistence.ConnectionPool;
import app.services.OrderService;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class OrderController {
    private ConnectionPool connectionPool;
    private OrderService orderService;

    public OrderController(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        this.orderService = new OrderService(connectionPool);
    }

    public void setRoutes(JavalinConfig config) {

        config.routes.get("/cart", ctx -> renderCart(ctx));
        config.routes.get("/menu", ctx -> renderMenu(ctx));
        config.routes.get("/active-orders", ctx -> renderActiveOrders(ctx));
    }

    public void renderMenu (Context ctx){
       // List<CupcakeTop> cupcakeTops =orderService.allCakeTops();
        //List <CupcakeBottom> cupcakeBottoms = orderService.allCakeBottoms();

        List<CupcakeTop> cupcakeTops =new ArrayList<>();
        List <CupcakeBottom> cupcakeBottoms = new ArrayList<>();

        cupcakeTops.add(new CupcakeTop("Vanilla", 10));
        cupcakeTops.add(new CupcakeTop("Chocolate", 15));
        cupcakeTops.add(new CupcakeTop("banana", 20));

        cupcakeBottoms.add(new CupcakeBottom("plain", 10));
        cupcakeBottoms.add(new CupcakeBottom("red", 15));
        cupcakeBottoms.add(new CupcakeBottom("caramel", 20));



        ctx.attribute("cupcakeTops", cupcakeTops); //sessionattribute
        ctx.attribute("cupcakeBottoms", cupcakeBottoms);

        ctx.render("menu");
    }

    public void addToCart(Context ctx){
        //lav orderline objekter
        //add til ssessionattribute cart
        //gem i databasen med status cart

        Order cart = ctx.sessionAttribute("cart");

        String cupcakeBottomChoice = ctx.formParam("cupcakeBottom");
        String cupcakeTopChoice = ctx.formParam("cupcakeTop");
        int quantity = Integer.parseInt(ctx.formParam("quantity"));

        CupcakeBottom cupcakeBottom = orderService.getCupcakeBottomByFlavor(cupcakeBottomChoice);
        CupcakeTop cupcakeTop = orderService.getCupcakeTopByFlavor(cupcakeTopChoice);

        Orderline orderline = new Orderline(cupcakeBottom, cupcakeTop, quantity);

        cart.addOrderline(orderline);

        //orderSservice.saveOrder(cart); gemmer hele svineriet i DB
    }

    private void renderCart(Context ctx) {
        ctx.render("cart");
    }

    private void renderActiveOrders(Context ctx) {
        List<Order> placedOrders = new ArrayList<>();
        Order order1 = new Order();
        Order order2 = new Order();
        Order order3 = new Order();
        CupcakeTop top1 = new CupcakeTop("Abrikos", 1);
        CupcakeTop top2 = new CupcakeTop("Banan", 10);
        CupcakeTop top3 = new CupcakeTop("Citron", 100);
        CupcakeBottom bottom1 = new CupcakeBottom("Ananas", 2);
        CupcakeBottom bottom2 = new CupcakeBottom("Blåbær", 20);
        order1.addOrderline(new Orderline(bottom1, top1, 1));
        order1.addOrderline(new Orderline(bottom2, top2, 1));
        order1.addOrderline(new Orderline(bottom2, top1, 1));
        order2.addOrderline(new Orderline(bottom2, top2, 10));
        order2.addOrderline(new Orderline(bottom2, top2, 10));
        order3.addOrderline(new Orderline(bottom1, top3, 100));
        order3.addOrderline(new Orderline(bottom2, top3, 100));
        placedOrders.add(order1);
        placedOrders.add(order2);
        placedOrders.add(order3);
        ctx.attribute("placedOrders", placedOrders);
        ctx.render("activeOrders");
    }
}