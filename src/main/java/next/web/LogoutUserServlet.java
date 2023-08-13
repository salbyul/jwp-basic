package next.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/user/logout")
public class LogoutUserServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        Cookie[] cookies = req.getCookies();
        Cookie loginCookie = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("logined"))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Inaccessible"));
        loginCookie.setValue("false");
        resp.addCookie(loginCookie);
        resp.sendRedirect("/");
    }
}
