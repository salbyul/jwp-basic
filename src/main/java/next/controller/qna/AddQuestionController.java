package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddQuestionController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(AddQuestionController.class);
    private final QuestionDao questionDao = new QuestionDao();

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        Question question = new Question(request.getParameter("writer"), request.getParameter("title"), request.getParameter("contents"));

        questionDao.insert(question);
        log.debug("Question Created!");
        return jspView("redirect:/");
    }
}
