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



        ctx.sessionAttribute("cupcakeTops", cupcakeTops);
        ctx.sessionAttribute("cupcakeBottoms", cupcakeBottoms);

        ctx.render("menu");
    }

    public void addToCart(Context ctx){
        //lav orderline objekter
        //add til ssessionattribute cart
        //gem i databasen med status cart

       /* User user = sessionAttribute("user");*/

        ArrayList<CupcakeBottom> cupcakeBottoms = ctx.sessionAttribute("cupcakeBottoms");
        ArrayList<CupcakeTop> cupcakeTops = ctx.sessionAttribute("cupcakeTops");

        if (ctx.sessionAttribute("cart")==null){ //sesssionAttributeCart, tjek om der findes en sessionattribute cart, hvis ikke oprettes en ny.
            if(ctx.sessionAttribute("user")==null){
            ctx.sessionAttribute("cart", new Order());
        } else {
               /* ctx.sessionAttribute("cart", new Order())*/
            }
        }

        Order cart = ctx.sessionAttribute("cart");

        String bottom = ctx.formParam("bottom");
        String top = ctx.formParam("top");
        int quantity = Integer.parseInt(ctx.formParam("quantity"));

        CupcakeBottom currentCupcakeBottom = null;
        CupcakeTop currentCupcakeTop=null;

        CupcakeBottom cupcakeBottom = orderService.getCupcakeBottomByFlavor(cake);
        CupcakeTop cupcakeTop = orderService.getCupcakeTopByFlavor(topping);

        for(CupcakeBottom b: cupcakeBottoms){
            if (bottom.equals(b.getFlavor())) {
                currentCupcakeBottom=b;
            }
            }
        for(CupcakeTop t: cupcakeTops){
            if (top.equals(t.getFlavor())) {
                currentCupcakeTop=t;
            }
            }


        Orderline orderline = new Orderline(currentCupcakeBottom, currentCupcakeTop, quantity);

        cart.addOrderline(orderline);

        //orderSservice.saveOrder(cart); gemmer hele svineriet i DB
    }

    private void renderCart(Context ctx) {
        ctx.render("cart");
    }
}