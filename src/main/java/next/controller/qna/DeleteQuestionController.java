package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DeleteQuestionController extends AbstractController {


    private static final Logger log = LoggerFactory.getLogger(DeleteQuestionController.class);

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        long questionId = Long.parseLong(request.getParameter("questionId"));
        if (canDelete(questionId, new QuestionDao(), new AnswerDao())) {
            return jspView("redirect:/");
        }
        throw new IllegalAccessException("삭제할 수 없습니다.");
    }

    public static boolean canDelete(final long questionId, final QuestionDao questionDao, final AnswerDao answerDao) {
        Question question = questionDao.findById(questionId);
        if (question == null) {
            return false;
        }
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);
        if (answers.size() == 0) {
            questionDao.delete(questionId);
            log.debug("{} is deleted!", questionId);
            return true;
        }
        String questionWriter = question.getWriter();
        boolean exist = answers.stream()
                .anyMatch(answer -> !answer.getWriter().equals(questionWriter));
        if (!exist) {
            questionDao.delete(questionId);
            log.debug("{} is deleted!", questionId);
            return true;
        }
        return false;
    }
}
