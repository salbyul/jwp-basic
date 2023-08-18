package next.controller.answer;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.Controller;
import next.dao.AnswerDao;
import next.model.Answer;
import next.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class DeleteAnswerController implements Controller {

    private static final Logger log = LoggerFactory.getLogger(DeleteAnswerController.class);

    @Override
    public String execute(final HttpServletRequest req, final HttpServletResponse resp) throws Exception {
        long answerId = Long.parseLong(req.getParameter("answerId"));
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter out = resp.getWriter();
        log.debug("answerId: {}", answerId);
        AnswerDao answerDao = new AnswerDao();
        Answer found = answerDao.findById(answerId);
        if (found == null) {
            String fail = mapper.writeValueAsString(Result.fail("Not Exist!"));
            out.print(fail);
            return null;
        }
        answerDao.delete(answerId);
        String ok = mapper.writeValueAsString(Result.ok());
        out.print(ok);
        return null;
    }
}
