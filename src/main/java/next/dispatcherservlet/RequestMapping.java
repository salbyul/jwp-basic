package next.dispatcherservlet;

import next.controller.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestMapping {

    private static final Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/users/create", CreateUserController.getInstance());
        controllers.put("/users/form", CreateUserController.getInstance());
        controllers.put("/", HomeController.getInstance());
        controllers.put("/index.html", HomeController.getInstance());
        controllers.put("/users", ListUserController.getInstance());
        controllers.put("/users/login", LoginController.getInstance());
        controllers.put("/users/loginForm", LoginController.getInstance());
        controllers.put("/users/logout", LogoutController.getInstance());
        controllers.put("/users/profile", ProfileController.getInstance());
        controllers.put("/users/update", UpdateUserController.getInstance());
        controllers.put("/users/updateForm", UpdateUserController.getInstance());
    }

    public static String mapping(final HttpServletRequest request, final HttpServletResponse response)  throws ServletException, IOException {
        return controllers.get(request.getRequestURI()).execute(request, response);
    }
}
