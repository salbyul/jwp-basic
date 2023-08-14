package next.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;

public class HomeController implements Controller {

    private static final long serialVersionUID = 1L;
    private static final HomeController INSTANCE = new HomeController();

    private HomeController() {}

    public static HomeController getInstance() {
        return INSTANCE;
    }

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("users", DataBase.findAll());
        return "index.jsp";
    }
}
