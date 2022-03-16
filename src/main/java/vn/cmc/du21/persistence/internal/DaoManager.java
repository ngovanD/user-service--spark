package vn.cmc.du21.persistence.internal;

import vn.cmc.du21.persistence.internal.dao.RoleDao;
import vn.cmc.du21.persistence.internal.dao.SessionDao;
import vn.cmc.du21.persistence.internal.dao.UserDao;
import vn.cmc.du21.persistence.internal.dao.UserRoleDao;

import java.sql.Connection;
import java.sql.SQLException;

public class DaoManager implements AutoCloseable {
    private final Connection connection;

    //define DAO
    private UserDao userDao;
    private RoleDao roleDao;
    private SessionDao sessionDao;
    private UserRoleDao userRoleDao;


    public DaoManager() throws SQLException {
        this.connection = DBHelper.getConnection();
    }

    public void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void commitTransaction() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    public void rollbackTransaction() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
    }

    public UserDao getUserDAO() {
        if (userDao == null) {
            userDao = new UserDao(this.connection);
        }
        return userDao;
    }
    public RoleDao getRoleDAO() {
        if (roleDao == null) {
            roleDao = new RoleDao(this.connection);
        }
        return roleDao;
    }
    public SessionDao getSessionDAO() {
        if (sessionDao == null) {
            sessionDao = new SessionDao(this.connection);
        }
        return sessionDao;
    }
    public UserRoleDao getUserRoleDAO() {
        if (userRoleDao == null) {
            userRoleDao = new UserRoleDao(this.connection);
        }
        return userRoleDao;
    }



    @Override
    public void close() throws Exception {
        connection.close();
    }
}
