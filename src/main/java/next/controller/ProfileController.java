package next.controller;

import core.db.DataBase;
import next.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileController implements Controller {

    private static final long serialVersionUID = 1L;
    private static final ProfileController INSTANCE = new ProfileController();

    private ProfileController() {}

    public static ProfileController getInstance() {
        return INSTANCE;
    }

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        request.setAttribute("user", user);
        return "/user/profile.jsp";
    }
}
