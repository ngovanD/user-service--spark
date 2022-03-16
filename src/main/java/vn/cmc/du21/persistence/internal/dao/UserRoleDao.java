package vn.cmc.du21.persistence.internal.dao;

import vn.cmc.du21.persistence.internal.entity.UserData;
import vn.cmc.du21.persistence.internal.entity.UserRoleData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserRoleDao implements Dao<UserRoleData>{
    private final Connection connection;
    private static final String USERROLE_TABLE = "userRole";

    private static final String USERID_COLUMN = "userId";

    private static final String ROLEID_COLUMN = "roleId";

    public UserRoleDao(Connection connection) {
        this.connection = connection;
    }
    @Override
    public List<UserRoleData> getAll() {
        return null;
    }

    @Override
    public Optional<UserRoleData> get(Long userId) {
        String sql = String.format("select * from "+USERROLE_TABLE+" where "+USERID_COLUMN+" = "+userId);

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            try (
                    ResultSet rs = st.executeQuery()
            ) {
                if (rs.next()) {
                    return Optional.of(new UserRoleData(rs.getLong(USERID_COLUMN), rs.getLong(ROLEID_COLUMN)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void save(UserRoleData entity) {
        String sql = String.format("insert into %s (%s, %s) values(?, ?)",
                USERROLE_TABLE, USERID_COLUMN, ROLEID_COLUMN);

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, entity.getUserId());
            st.setLong(2, entity.getRoleId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(UserRoleData entity) {

    }
    @Override
    public  List<UserRoleData> getList(String a, String b , String c){
        return null;
    }
}
