package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface Mapper<T> {
    T map(final ResultSet rs) throws SQLException;
}
