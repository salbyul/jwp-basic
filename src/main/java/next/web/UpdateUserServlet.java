package next.web;

import core.db.DataBase;
import next.dao.UserDao;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/update")
public class UpdateUserServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(UpdateUserServlet.class);
    private static final String NOT_EXIST_USER = "Not Exist User";

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        log.debug("userId: {}", userId);
        User user = DataBase.findUserById(userId);
        req.setAttribute("user", user);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/user/update.jsp");
        dispatcher.forward(req, resp);
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
