package core.mvc;

import core.mvc.view.ModelAndView;
import core.mvc.view.ModelAndViewImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController implements Controller {
    private String forwardUrl;

    public ForwardController(String forwardUrl) {
        this.forwardUrl = forwardUrl;
        if (forwardUrl == null) {
            throw new NullPointerException("forwardUrl is null. 이동할 URL을 입력하세요.");
        }
    }

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ModelAndViewImpl modelAndView = new ModelAndViewImpl(req, resp);
        modelAndView.setPath(forwardUrl);
        return modelAndView;
    }
}
