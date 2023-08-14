package next.controller;

import core.db.DataBase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ListUserController implements Controller {

    private static final long serialVersionUID = 1L;
    private static final ListUserController INSTANCE = new ListUserController();

    private ListUserController() {
    }

    public static ListUserController getInstance() {
        return INSTANCE;
    }

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return "redirect:/users/loginForm";
        }

        request.setAttribute("users", DataBase.findAll());
        return "/user/list.jsp";
    }
}
