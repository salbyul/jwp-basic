package next.dao;

import core.jdbc.ConnectionManager;
import next.exception.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate<T> {

    public void update(final String query, final ValueSetter setter) {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            setter.setValues(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void update(final String query, final Object... values) {
        update(query, pstmt -> {
            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]);
            }
        });
    }

    @SuppressWarnings("ThrowFromFinallyBlock")
    public T selectOne(final String query, final Mapper<T> mapper, final ValueSetter setter) {
        T result = null;
        ResultSet rs = null;
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            setter.setValues(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = mapper.map(rs);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DataAccessException(e.getMessage());
                }
            }
        }
        return result;
    }

    public T selectOne(final String query, final Mapper<T> mapper, final Object... values) {
        return selectOne(query, mapper, pstmt -> {
            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]);
            }
        });
    }

    @SuppressWarnings("ThrowFromFinallyBlock")
    public List<T> selectList(final String query, final Mapper<T> mapper, final ValueSetter setter) {
        ResultSet rs = null;
        List<T> result = new ArrayList<>();
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query);) {
            setter.setValues(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                T t = mapper.map(rs);
                result.add(t);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DataAccessException(e.getMessage());
                }
            }
        }
        return result;
    }

    public List<T> selectList(final String query, final Mapper<T> mapper, final Object... values) {
        return selectList(query, mapper, pstmt -> {
            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]);
            }
        });
    }
}
