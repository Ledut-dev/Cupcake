package app.controllers;

import app.entities.Cake;
import app.entities.Topping;
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
        List<Topping> toppings=orderService.allToppings();
        List <Cake> cakes= orderService.allCakes();

        ctx.attribute("toppings", toppings);
        ctx.attribute("cakes", cakes);


    }


}
