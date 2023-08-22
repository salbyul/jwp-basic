package next.controller.qna;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.*;

import static org.hamcrest.core.Is.is;

public class DeleteQuestionControllerTest {

    private static final Map<Long, Question> questionMap = new HashMap<>();
    private static final Map<Long, Answer> answerMap = new HashMap<>();
    private final MockQuestionDao questionDao = new MockQuestionDao();
    private final MockAnswerDao answerDao = new MockAnswerDao();

    @Before
    public void setUp() throws Exception {
        questionMap.put(1L, new Question(1, "자바지기", "좋은 제목", "좋은 내용", Date.from(Instant.now()), 2));
        questionMap.put(2L, new Question(2, "자바지기", "좋은 제목", "좋은 내용", Date.from(Instant.now()), 1));
        questionMap.put(3L, new Question(3, "자바지기", "좋은 제목", "좋은 내용", Date.from(Instant.now()), 0));
        answerMap.put(1L, new Answer(1, "자바지기", "rntwk", Date.from(Instant.now()), 1));
        answerMap.put(2L, new Answer(2, "살별", "rntwk", Date.from(Instant.now()), 1));
        answerMap.put(3L, new Answer(3, "자바지기", "rntwk", Date.from(Instant.now()), 2));
    }

    public static class MockQuestionDao extends QuestionDao {

        @Override
        public Question findById(final long questionId) {
            return questionMap.get(questionId);
        }

        @Override
        public void delete(final Long questionId) {
            questionMap.remove(questionId);
        }
    }

    public static class MockAnswerDao extends AnswerDao {

        @Override
        public List<Answer> findAllByQuestionId(final long questionId) {
            List<Answer> result = new ArrayList<>();
            for (Long key : answerMap.keySet()) {
                Answer answer = answerMap.get(key);
                if (answer.getQuestionId() == questionId) {
                    result.add(answer);
                }
            }
            return result;
        }

        @Override
        public void delete(final Long answerId) {
            answerMap.remove(answerId);
        }
    }

    @Test
    public void deleteFailed() {
        boolean canDelete = DeleteQuestionController.canDelete(1, questionDao, answerDao);
        Assert.assertThat(canDelete, is(false));
    }

    @Test
    public void deleteNoAnswer() {
        boolean canDelete = DeleteQuestionController.canDelete(3, questionDao, answerDao);
        Assert.assertThat(canDelete, is(true));
    }

    @Test
    public void deleteHasOwnAnswer() {
        boolean canDelete = DeleteQuestionController.canDelete(2, questionDao, answerDao);
        Assert.assertThat(canDelete, is(true));
    }
}