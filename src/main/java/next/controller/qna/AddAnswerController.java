package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.controller.Controller;
import next.dao.QuestionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.model.Answer;

@Controller(urlPatterns = "/api/qna/addAnswer")
public class AddAnswerController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

    private final AnswerDao answerDao = new AnswerDao();
    private final QuestionDao questionDao = new QuestionDao();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
        long questionId = Long.parseLong(req.getParameter("questionId"));
        Answer answer = new Answer(req.getParameter("writer"), req.getParameter("contents"),
                questionId);
        log.debug("answer : {}", answer);

        Answer savedAnswer = answerDao.insert(answer);
        questionDao.addCountsOfAnswerById(questionId);

        return jsonView().addObject("answer", savedAnswer);
    }
}
