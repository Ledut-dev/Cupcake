package app.controllers;

import app.entities.Order;
import app.entities.Orderline;
import app.persistence.ConnectionPool;
import app.services.OrderService;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;

public class OrderController {
    private ConnectionPool connectionPool;
    private OrderService orderService;

    public OrderController(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        this.orderService = new OrderService(connectionPool);
    }

    public void setRoutes(JavalinConfig config) {
        config.routes.get("/cart", ctx -> renderCart(ctx));
    }

    private void renderCart(Context ctx) {
        Order order = orderService.openOrder();
        order.addOrderline(new Orderline("Hindbær", "Chokolade", 12));
        order.addOrderline(new Orderline("Blåbær", "Pistacie", 6));
        ctx.sessionAttribute("order", order);
        ctx.render("cart");
    }
}