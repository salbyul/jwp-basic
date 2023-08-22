package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateQuestionController extends AbstractController {

    private final QuestionDao questionDao = new QuestionDao();
    private final AnswerDao answerDao = new AnswerDao();

    private static final Logger log = LoggerFactory.getLogger(UpdateQuestionController.class);

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        long questionId = Long.parseLong(request.getParameter("questionId"));

        log.debug("{} will update", questionId);

        Question question = questionDao.findById(questionId);
        if (question == null) {
            throw new IllegalAccessException("해당 게시글이 존재하지 않습니다.");
        }
        Question questionUpdated = new Question(
                questionId,
                question.getWriter(),
                request.getParameter("title"),
                request.getParameter("contents"),
                question.getCreatedDate(),
                question.getCountOfComment());
        questionDao.update(questionUpdated);

        return jspView("redirect:/qna/show?questionId=" + questionId);
    }
}
