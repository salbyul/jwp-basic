package next.dao;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

import core.jdbc.JdbcTemplate;
import core.jdbc.KeyHolder;
import core.jdbc.PreparedStatementCreator;
import core.jdbc.RowMapper;
import next.model.Question;

public class QuestionDao {

    private final JdbcTemplate template = JdbcTemplate.getInstance();

    public Question insert(Question question) {
        String sql = "INSERT INTO QUESTIONS " +
                "(writer, title, contents, createdDate) " +
                " VALUES (?, ?, ?, ?)";
        PreparedStatementCreator psc = con -> {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, question.getWriter());
            pstmt.setString(2, question.getTitle());
            pstmt.setString(3, question.getContents());
            pstmt.setTimestamp(4, new Timestamp(question.getTimeFromCreateDate()));
            return pstmt;
        };

        KeyHolder keyHolder = new KeyHolder();
        template.update(psc, keyHolder);
        return findById(keyHolder.getId());
    }

    public List<Question> findAll() {
        String sql = "SELECT questionId, writer, title, createdDate, countOfAnswer FROM QUESTIONS "
                + "order by questionId desc";

        RowMapper<Question> rm = rs -> new Question(
                rs.getLong("questionId"),
                rs.getString("writer"),
                rs.getString("title"),
                null,
                rs.getTimestamp("createdDate"),
                rs.getInt("countOfAnswer"));

        return template.query(sql, rm);
    }

    public Question findById(long questionId) {
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS "
                + "WHERE questionId = ?";

        RowMapper<Question> rm = rs -> new Question(
                rs.getLong("questionId"),
                rs.getString("writer"),
                rs.getString("title"),
                rs.getString("contents"),
                rs.getTimestamp("createdDate"),
                rs.getInt("countOfAnswer"));

        return template.queryForObject(sql, rm, questionId);
    }

    public void addCountsOfAnswerById(final long questionId) {
        String sql = "UPDATE QUESTIONS SET countOfAnswer = countOfAnswer + 1 WHERE questionId = ?";
        template.update(sql, pstmt -> pstmt.setLong(1, questionId));
    }

    public void update(final Question question) {
        String sql = "UPDATE QUESTIONS SET title = ?, contents = ? WHERE questionId = ?";
        template.update(sql, pstmt -> {
            pstmt.setString(1, question.getTitle());
            pstmt.setString(2, question.getContents());
            pstmt.setLong(3, question.getQuestionId());
        });
    }

    public void delete(final Long questionId) {
        String sql = "DELETE FROM QUESTIONS WHERE questionId = ?";
        template.update(sql, questionId);
    }
}
