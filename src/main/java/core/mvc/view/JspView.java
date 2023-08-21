package core.mvc.view;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class JspView implements View {

    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private final String viewName;

    public JspView(final String viewName) {
        if (viewName == null) {
            throw new NullPointerException("viewName is null. 이동할 URL을 추가해 주세요.");
        }
        this.viewName = viewName;
    }

    @Override
    public void render(final Map<String, ?> model, final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            response.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }
        for (String key : model.keySet()) {
            request.setAttribute(key, model.get(key));
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(viewName);
        dispatcher.forward(request, response);
    }
}
