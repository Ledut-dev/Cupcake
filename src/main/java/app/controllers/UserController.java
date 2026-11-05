package app.controllers;

import app.entities.User;
import app.exceptions.IncorrectLoginInfoException;
import app.persistence.ConnectionPool;
import app.services.UserService;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;

public class UserController {
    private ConnectionPool connectionPool;
    private UserService userService;

    public UserController(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        userService = new UserService(connectionPool);
    }

    public void setRoutes(JavalinConfig config) {
        config.routes.get("/login", ctx -> renderLogin(ctx));
        config.routes.get("/logout", ctx -> logout(ctx));

        config.routes.post("/login", ctx -> login(ctx));
    }

    private void renderLogin(Context ctx){
        ctx.render("/login");
    }

    private void renderAdminSite(Context ctx){
        ctx.render("/admin");
    }

    private void login(Context ctx){
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        User user = userService.login(email, password);
        try {
            if (user == null){
                throw new IncorrectLoginInfoException("Forkert brugernavn/password");
            }
            ctx.sessionAttribute("errorMessage", null);
            ctx.sessionAttribute("loggedInUser", user);
            ctx.sessionAttribute("isUserAdmin", userService.isUserAdmin(user));
        } catch (IncorrectLoginInfoException e){
            ctx.sessionAttribute("errorMessage", e.getMessage());
        }
        ctx.redirect("/");
    }

    private void logout(Context ctx){
        ctx.sessionAttribute("loggedInUser", null);
        ctx.render("/");
    }

    private void redirectAdmin(Context ctx){
        try {
            if (ctx.sessionAttribute("isUserAdmin")) {
                renderAdminSite(ctx);
            }
        }
        catch (NullPointerException e){
            return;
        }
    }
}
