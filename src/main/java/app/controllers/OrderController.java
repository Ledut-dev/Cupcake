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

       // config.routes.get("/cart", ctx -> renderCart(ctx));
        config.routes.get("templates/menu", ctx -> renderMenu(ctx));
    }



    public void addToOrder (Context ctx){



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

        ctx.render("public/menu.html");
    }

    public void addToCart(Context ctx){
        //lav orderline objekter
        //add til ssessionattribute cart
        //gem i databasen med status cart

        Order cart = ctx.sessionAttribute("cart");

        String cake = ctx.formParam("cake");
        String topping = ctx.formParam("topping");
        int quantity = Integer.parseInt(ctx.formParam("quantity"));

        CupcakeBottom cupcakeBottom = orderService.getCupcakeBottomByFlavor(cake);
        CupcakeTop cupcakeTop = orderService.getCupcakeTopByFlavor(topping);

        Orderline orderline = new Orderline(cupcakeBottom, cupcakeTop, quantity);

        cart.addOrderline(orderline);

        //orderSservice.saveOrder(cart); gemmer hele svineriet i DB
    }

}