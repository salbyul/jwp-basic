package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static next.controller.qna.DeleteQuestionController.*;

public class ApiDeleteQuestionController extends AbstractController {

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        long questionId = Long.parseLong(request.getParameter("questionId"));
        if (canDelete(questionId)) {
            return jsonView().addObject("ok", Result.ok());
        }
        return jsonView().addObject("fail", Result.fail("failed"));
    }
}
