package app.controllers;

import app.entities.User;
import app.exceptions.IncorrectLoginInfoException;
import app.exceptions.EmailNotAvailableException;
import app.exceptions.PasswordNotValidException;
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
        config.routes.get("/", ctx -> renderFrontpage(ctx));
		config.routes.get("/login", ctx -> renderLoginForm(ctx));
        config.routes.get("/logout", ctx -> logout(ctx));
        config.routes.get("/registerForm", ctx -> renderRegisterForm(ctx));

        config.routes.post("/login", ctx -> login(ctx));
        config.routes.post("/register", ctx -> register(ctx));
    }

    private void renderFrontpage(Context ctx) {
        ctx.render("/frontpage");
    }

    private void renderLoginForm(Context ctx) {
		ctx.render("/login");
	}

    private void renderAdminSite(Context ctx){
        ctx.render("/admin");
    }
	private void renderRegisterForm(Context ctx) {
		ctx.render("/register");
	}

    private void login(Context ctx){
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        User user = userService.login(email, password);
        try {
            if (user == null){
                throw new IncorrectLoginInfoException("Forkert brugernavn/password");
            }

            ctx.sessionAttribute("loggedInUser", user);
            ctx.sessionAttribute("isUserAdmin", userService.isUserAdmin(user));

        } catch (IncorrectLoginInfoException e){
            ctx.attribute("errorMessage", e.getMessage());
            renderLoginForm(ctx);
        }
        ctx.redirect("/");
    }

	private void register(Context ctx) {
		try {
			if (!userService.checkIsEmailAvailable(ctx.formParam("email"))) {
				throw new EmailNotAvailableException("Den valgte email er allerede i systemet.");
			}
			if (!userService.checkIsPasswordValid(ctx.formParam("psw"))) {
				throw new PasswordNotValidException("Password skal være længere end 2 karakterer.");
			}
			User user = userService.createUser(ctx.formParam("email"), ctx.formParam("psw"));
			ctx.sessionAttribute("user", user);
			ctx.redirect("/menu");
        } catch (EmailNotAvailableException | PasswordNotValidException e) {
            System.out.println(e.getMessage());
            ctx.attribute("error", e.getMessage());
            ctx.render("/register");
        }

	}

    private void logout(Context ctx){
        ctx.sessionAttribute("loggedInUser", null);
        ctx.sessionAttribute("isUserAdmin", false);
        ctx.redirect("/");
    }

    //Method that can generally be used to check for if a logged in user is an admin, and in that case, redirect them to the admin-page if they to try directly access "normal" pages.
    private void redirectAdmin(Context ctx){
        Boolean isUserAdmin = ctx.sessionAttribute("isUserAdmin");
        if (isUserAdmin != null && isUserAdmin) {
                renderAdminSite(ctx);
            }
        }

    }
