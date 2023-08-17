package next.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface ValueSetter {
    void setValues(final PreparedStatement pstmt) throws SQLException;
}
