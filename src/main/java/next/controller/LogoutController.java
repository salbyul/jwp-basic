package next.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutController implements Controller {

    private static final long serialVersionUID = 1L;
    private static final LogoutController INSTANCE = new LogoutController();

    private LogoutController() {}

    public static LogoutController getInstance() {
        return INSTANCE;
    }

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }
}
