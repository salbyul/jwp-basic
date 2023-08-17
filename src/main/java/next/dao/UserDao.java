package next.dao;

import java.util.List;

import next.model.User;

public class UserDao {

    public void insert(User user) {
        JdbcTemplate<User> template = new JdbcTemplate<>();
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        template.update(sql, pstmt -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
        });
    }

    public void update(User user) {
        JdbcTemplate<User> template = new JdbcTemplate<>();
        String sql = "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?";
        template.update(sql, pstmt -> {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
        });
    }

    public List<User> findAll() {
        JdbcTemplate<User> template = new JdbcTemplate<>();
        String sql = "SELECT * FROM USERS";
        return template.selectList(sql, rs -> new User(
                rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email")), pstmt -> {
        });
    }

    public User findByUserId(String userId) {
        JdbcTemplate<User> template = new JdbcTemplate<>();
        String sql = "SELECT * FROM USERS WHERE userId = ?";
        return template.selectOne(sql, rs -> new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")),
                pstmt -> pstmt.setString(1, userId));
    }
}
