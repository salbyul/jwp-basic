package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.Answer;

import java.util.List;

public class AnswerDao {

    public List<Answer> findAllById(final long questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT * FROM ANSWERS WHERE questionId = ?";

        RowMapper<Answer> rm = rs -> new Answer(
                rs.getLong("answerId"),
                rs.getString("writer"),
                rs.getString("contents"),
                rs.getTimestamp("createdDate").toLocalDateTime(),
                rs.getLong("questionId")
        );

        return jdbcTemplate.query(sql, rm, pstmt ->
                pstmt.setLong(1, questionId));
    }
}
