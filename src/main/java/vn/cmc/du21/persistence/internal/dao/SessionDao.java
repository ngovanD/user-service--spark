package vn.cmc.du21.persistence.internal.dao;

import vn.cmc.du21.persistence.internal.entity.SessionData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SessionDao implements Dao<SessionData>{
    private final Connection connection;
    private static final String SESSION_TABLE = "session";

    private static final String USERID_COLUMN = "userId";
    private static final String DEVICEID_COLUMN = "deviceId";
    private static final String TOKEN_COLUMN = "token";
    private static final String EXPIRETIME_COLUMN = "expireTime";
    private static final String STATUS_COLUMN = "status";

    public SessionDao(Connection connection) {
        this.connection = connection;
    }
    @Override
    public List<SessionData> getAll() {
        return null;
    }

    @Override
    public Optional<SessionData> get(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void save(SessionData entity) {
        String sql = String.format("insert into %s (%s, %s, %s, %s, %s) values(?, ?, ?, ?, ?)",
                SESSION_TABLE, USERID_COLUMN, DEVICEID_COLUMN, TOKEN_COLUMN, EXPIRETIME_COLUMN, STATUS_COLUMN);

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, entity.getUserId());
            st.setLong(2, entity.getDeviceId());
            st.setString(3, entity.getToken());
            st.setTimestamp(4, entity.getExpireTime());
            st.setString(5, entity.getStatus());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(SessionData entity) {
        String sql = "update "+ SESSION_TABLE
                +" set "+ TOKEN_COLUMN +" = '"+ entity.getToken()
                +"', "+ EXPIRETIME_COLUMN +" = '"+ entity.getExpireTime()
                +"', "+ STATUS_COLUMN +" = '"+ entity.getStatus() + "' "
                +" where "+ USERID_COLUMN +" = " + entity.getUserId() + " and " + DEVICEID_COLUMN + " = " + entity.getDeviceId();

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<SessionData> getByUserIdDeviceId(Long userId, Long deviceId) {

        String sql = String.format("select * from %s where %s = ? and %s = ? limit 1", SESSION_TABLE, USERID_COLUMN, DEVICEID_COLUMN);

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, userId);
            st.setLong(2, deviceId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new SessionData(rs.getLong(USERID_COLUMN), rs.getLong(DEVICEID_COLUMN), rs.getString(TOKEN_COLUMN),
                            rs.getTimestamp(EXPIRETIME_COLUMN), rs.getString(STATUS_COLUMN)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<SessionData> getActive(String token)
    {
        String sql = String.format("select * from %s where %s = ? and %s = ? and %s > now() limit 1", SESSION_TABLE, TOKEN_COLUMN, STATUS_COLUMN, EXPIRETIME_COLUMN);

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, token);
            st.setString(2, "Active");
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new SessionData(rs.getLong(USERID_COLUMN), rs.getLong(DEVICEID_COLUMN), rs.getString(TOKEN_COLUMN),
                            rs.getTimestamp(EXPIRETIME_COLUMN), rs.getString(STATUS_COLUMN)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    @Override
    public  List<SessionData> getList(String a, String b ,String c){
        return null;
    }
}
