package next.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;
import next.model.User;

public class CreateUserController implements Controller {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);
    private static final CreateUserController INSTANCE = new CreateUserController();

    private CreateUserController() {}

    public static CreateUserController getInstance() {
        return INSTANCE;
    }

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("GET")) {
            return "/user/form.jsp";
        } else {
            User user = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"),
                    request.getParameter("email"));
            log.debug("User : {}", user);
            DataBase.addUser(user);
            return "redirect:/";
        }
    }
}
