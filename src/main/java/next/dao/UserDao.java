package next.dao;

import java.util.List;

import next.model.User;
import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;

public class UserDao {

    private final JdbcTemplate template = JdbcTemplate.getInstance();

    public void insert(User user) {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        template.update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public User findByUserId(String userId) {
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";

        RowMapper<User> rm = rs -> new User(
                rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email"));

        return template.queryForObject(sql, rm, userId);
    }

    public List<User> findAll() {
        String sql = "SELECT userId, password, name, email FROM USERS";

        RowMapper<User> rm = rs -> new User(
                rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email"));

        return template.query(sql, rm);
    }

    public void update(User user) {
        String sql = "UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?";
        template.update(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }
}
