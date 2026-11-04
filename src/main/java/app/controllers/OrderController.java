package app.controllers;

import app.entities.CupcakeBottom;
import app.entities.CupcakeTop;
import app.services.OrderService;

import java.util.List;

public class OrderController {


private OrderService orderService;

public OrderController(){
    orderService= new OrderService();
}








    public void addToOrder (Context ctx){

        String email = ctx.formParam("email");

        String topping = ctx.formParam("topping");
        String cake = ctx.formParam("cake");
        String topping = ctx.formParam("topping");


        Orderline orderline = new Orderline();

    }

    public void renderMenu (Context ctx){
        List<CupcakeTop> cupcakeTops =orderService.allCakeTops();
        List <CupcakeBottom> cupcakeBottoms = orderService.allCakeBottoms();

        ctx.attribute("toppings", cupcakeTops);
        ctx.attribute("cakes", cupcakeBottoms);


    }


}
