package next.controller;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowQNAController implements Controller {

    private static final Logger log = LoggerFactory.getLogger(ShowQNAController.class);

    @Override
    public String execute(final HttpServletRequest req, final HttpServletResponse resp) throws Exception {
        String questionId = req.getParameter("id");
        QuestionDao questionDao = new QuestionDao();
        AnswerDao answerDao = new AnswerDao();

        Question question = questionDao.findByQuestionId(questionId);
        req.setAttribute("question", question);
        req.setAttribute("answers", answerDao.findAllById(question.getQuestionId()));

        return "/qna/show.jsp";
    }
}
