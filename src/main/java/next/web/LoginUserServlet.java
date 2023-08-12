package next.web;

import core.db.DataBase;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/login")
public class LoginUserServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(LoginUserServlet.class);

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        if (isValidUser(req.getParameter("userId"), req.getParameter("password"))) {
            resp.addCookie(new Cookie("logined", "true"));
            log.debug("{} is login!", req.getParameter("userId"));
            resp.sendRedirect("/index.html");
            return;
        }
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.addCookie(new Cookie("logined", "false"));
        RequestDispatcher dispatcher = req.getRequestDispatcher("/user/login_failed.html");
        dispatcher.forward(req, resp);
    }

    private boolean isValidUser(final String userId, final String password) {
        if (userId == null || password == null) {
            return false;
        }
        User user = DataBase.findUserById(userId);
        return user != null && user.getPassword().equals(password);
    }
}
