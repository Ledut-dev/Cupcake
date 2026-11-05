package app.controllers;

import app.entities.User;
import app.exceptions.EmailNotAvailableException;
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
		config.routes.get("/login", ctx -> renderLoginForm(ctx));
		config.routes.get("/registerForm", ctx -> renderRegisterForm(ctx));
		config.routes.post("/register", ctx -> register(ctx));
	}

	private void renderLoginForm(Context ctx) {
		ctx.render("/login");
	}

	private void renderRegisterForm(Context ctx) {
		ctx.render("/register");
	}

	private void register(Context ctx) {
		try {
			if (userService.checkIsEmailAvailable(ctx.formParam("email"))) {
				User user = userService.createUser(ctx.formParam("email"), ctx.formParam("psw"));
				ctx.sessionAttribute("user", user);
				ctx.redirect("/menu");
			} else {
				throw new EmailNotAvailableException("Den valgte email er allerede i systemet.");
			}
		} catch (EmailNotAvailableException e) {
			System.out.println(e.getMessage());
			ctx.attribute("error", e.getMessage());
			ctx.render("/register");
		}

	}
}
