package app.controllers;

import app.entities.*;
import app.exceptions.BalanceTooLowException;
import app.persistence.ConnectionPool;
import app.services.OrderService;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import app.entities.OrderStatus;

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
        config.routes.post("/addToCart", ctx -> addToCart(ctx));
        config.routes.post("/order", ctx -> order(ctx));

    }

    public void renderMenu(Context ctx) { //creates lists of all cupcaketops and bpttoms, savess the lists as sessionAttributes and renders /menu
        // List<CupcakeTop> cupcakeTops =orderService.allCakeTops();
        //List <CupcakeBottom> cupcakeBottoms = orderService.allCakeBottoms();

        List<CupcakeTop> cupcakeTops = new ArrayList<>();
        List<CupcakeBottom> cupcakeBottoms = new ArrayList<>();

        cupcakeTops.add(new CupcakeTop("Vanilla", 10));
        cupcakeTops.add(new CupcakeTop("Chocolate", 15));
        cupcakeTops.add(new CupcakeTop("banana", 20));

        cupcakeBottoms.add(new CupcakeBottom("plain", 10));
        cupcakeBottoms.add(new CupcakeBottom("red", 15));
        cupcakeBottoms.add(new CupcakeBottom("caramel", 20));


        ctx.sessionAttribute("cupcakeTops", cupcakeTops);
        ctx.sessionAttribute("cupcakeBottoms", cupcakeBottoms);

        ctx.render("menu");
    }

    public void addToCart(Context ctx) { //gets sessionAttribute user, cupcaketops(list), cupcakebottoms(list), checks if there is a sessionAttribute cart. ggets the cupcake choice from form in html, creates an orderline and adds to car

        User currentUser = getCurrentUser(ctx);

        ArrayList<CupcakeBottom> cupcakeBottoms = ctx.sessionAttribute("cupcakeBottoms");
        ArrayList<CupcakeTop> cupcakeTops = ctx.sessionAttribute("cupcakeTops");

        String bottom = ctx.formParam("bottom");
        String top = ctx.formParam("top");
        int quantity = Integer.parseInt(ctx.formParam("quantity"));

        CupcakeBottom currentCupcakeBottom = null;
        CupcakeTop currentCupcakeTop = null;

        for (CupcakeBottom b : cupcakeBottoms) {
            if (bottom.equals(b.getFlavor())) {
                currentCupcakeBottom = b;
            }
        }
        for (CupcakeTop t : cupcakeTops) {
            if (top.equals(t.getFlavor())) {
                currentCupcakeTop = t;
            }
        }


        Orderline orderline = new Orderline(currentCupcakeBottom, currentCupcakeTop, quantity);

        Order cart = sessionAttributeCart(ctx);
        cart.addOrderline(orderline);

        ctx.render("menu");
    }

    private void renderCart(Context ctx) {
        ctx.render("/cart");
    }

    private void order(Context ctx) {
        User currentUser = getCurrentUser(ctx);

        if (currentUser == null) {
            ctx.redirect("/login");
        }

        Order cart = sessionAttributeCart(ctx);

        // Check if account balance is big enough
        // NullPointer if balance is Null
        try {
            if (currentUser.getBalance() < cart.getTotal()) {
                throw new BalanceTooLowException("Ikke nok penge på konto");
            }
            // Set order status and date and save order to db
            cart.setStatus(OrderStatus.PLACED);
            cart.setOrderDate(LocalDate.now());
            orderService.saveOrder(cart);

            ctx.sessionAttribute("cart", null);
            ctx.render("/success");
        } catch (NullPointerException | BalanceTooLowException e) {
            ctx.attribute("errorMessage", e.getMessage());
            ctx.render("/cart");
        }
    }

    public Order sessionAttributeCart(Context ctx) {

        User currentUser = getCurrentUser(ctx);
        Order cart;

        try {
            cart = ctx.sessionAttribute("cart");
            if (cart == null) throw new NullPointerException();
            if (currentUser != null) {
                cart.setUser(currentUser);
                ctx.sessionAttribute("cart", cart);
                return cart;
            }
        } catch (NullPointerException n) {
            ctx.sessionAttribute("cart", new Order());
            cart = ctx.sessionAttribute("cart");
            return cart;
        }
        return null;
    }

    public User getCurrentUser(Context ctx) {

        try {
            User currentUser = ctx.sessionAttribute("currentUser");
            return currentUser;
        } catch (NullPointerException n) {
            return null;
        }


    }


}