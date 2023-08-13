package next.web;

import core.db.DataBase;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@WebServlet("/user/update")
public class UpdateUserServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(UpdateUserServlet.class);
    private static final String NOT_EXIST_USER = "Not Exist User";

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        if (!isValid(req, resp, userId)) return;
        log.debug("userId: {}", userId);
        User user = DataBase.findUserById(userId);
        req.setAttribute("user", user);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/user/update.jsp");
        dispatcher.forward(req, resp);
    }

    private boolean isValid(final HttpServletRequest req, final HttpServletResponse resp, final String userId) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/error.html");
        if (Objects.isNull(userId)) {
            dispatcher.forward(req, resp);
            return false;
        }

        Cookie[] cookies = req.getCookies();
        if (!isLogin(cookies)) {
            dispatcher.forward(req, resp);
            return false;
        }

        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        if (!(user instanceof User)) {
            dispatcher.forward(req, resp);
            return false;
        }
        if(!isRightUser(userId, (User) user)) {
            dispatcher.forward(req, resp);
            return false;
        }
        return true;
    }

    private boolean isLogin(final Cookie[] cookies) {
        return Arrays.stream(cookies)
                .anyMatch(cookie -> cookie.getName().equals("logined") && cookie.getValue().equals("true"));
    }

    private boolean isRightUser(final String userId, final User user) {
        return user.getUserId().equals(userId);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        User foundUser = DataBase.findUserById(userId);
        if (foundUser == null) {
            throw new IllegalArgumentException(NOT_EXIST_USER);
        }
        User user = new User(
                userId,
                req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email"));
        DataBase.addUser(user);
        resp.sendRedirect("/user/list");
    }
}
