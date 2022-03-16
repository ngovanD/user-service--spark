package vn.cmc.du21.persistence.internal;

import org.apache.commons.dbcp2.BasicDataSource;
import vn.cmc.du21.common.Setting;

import java.sql.Connection;
import java.sql.SQLException;

public class DBHelper {
    private static final BasicDataSource dataSource;
    static {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(Setting.DB_DRIVER_CLASS);
        dataSource.setUrl(Setting.DB_URL);
        dataSource.setUsername(Setting.DB_USER);
        dataSource.setPassword(Setting.DB_PWD);
        dataSource.setInitialSize(Setting.DB_INITIAL_POOL_SIZE);
        dataSource.setMaxTotal(Setting.DB_MAX_POOL_SIZE);
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(true);
        return connection;
    }
}
