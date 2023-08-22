package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateFormQuestionController extends AbstractController {

    private final QuestionDao questionDao = new QuestionDao();

    private static final Logger log = LoggerFactory.getLogger(UpdateFormQuestionController.class);

    @Override
    public ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        User user = UserSessionUtils.getUserFromSession(request.getSession());

        long questionId = Long.parseLong(request.getParameter("questionId"));
        log.debug("{} Update Form", questionId);
        Question question = questionDao.findById(questionId);
        if (!user.getName().equals(question.getWriter())) {
            throw new IllegalAccessException("다른 유저의 게시글을 수정할 수 없습니다.");
        }
        return jspView("/qna/form.jsp")
                .addObject("question", question)
                .addObject("name", user.getName());
    }
}
