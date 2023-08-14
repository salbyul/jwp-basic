package next.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Controller {

    String execute(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException;
}
