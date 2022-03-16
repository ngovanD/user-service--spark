package vn.cmc.du21.persistence.internal.dao;

import vn.cmc.du21.business.model.Role;
import vn.cmc.du21.persistence.internal.entity.RoleData;
import vn.cmc.du21.persistence.internal.entity.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RoleDao implements Dao<RoleData>{
    private final Connection connection;
    private static final String ROLE_TABLE = "role";

    private static final String ROLE_ID_COLUMN = "roleId";

    private static final String NAMEROLE_COLUMN = "nameRole";

    public RoleDao(Connection connection) {
        this.connection = connection;
    }
    @Override
    public List<RoleData> getAll() {
        return null;
    }

    @Override
    public Optional<RoleData> get(Long roleId) {
        String sql = "select * from "+ROLE_TABLE+
                " where "+ROLE_ID_COLUMN+" = '"+roleId+"'";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            try (
                    ResultSet rs = st.executeQuery()
            ) {
                if (rs.next()) {
                    return Optional.of(new RoleData(rs.getLong(ROLE_ID_COLUMN),rs.getString(NAMEROLE_COLUMN)));
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
    public void save(RoleData entity) {

    }

    @Override
    public void update(RoleData entity) {

    }
    @Override
    public  List<RoleData> getList(String a, String b , String c){
        return null;
    }

    public Optional<RoleData> get(String roleName) {
        String sql = "select * from "+ROLE_TABLE+
                " where "+NAMEROLE_COLUMN+" = '"+roleName+"'";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            try (
                    ResultSet rs = st.executeQuery()
            ) {
                if (rs.next()) {
                    return Optional.of(new RoleData(rs.getLong(ROLE_ID_COLUMN),rs.getString(NAMEROLE_COLUMN)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
