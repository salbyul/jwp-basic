package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.view.ModelAndView;
import next.dao.AnswerDao;
import next.model.Result;

public class DeleteAnswerController extends AbstractController {

    private final AnswerDao answerDao = new AnswerDao();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long answerId = Long.parseLong(req.getParameter("answerId"));

        answerDao.delete(answerId);

        return jsonView().addObject("ok", Result.ok());
    }
}
