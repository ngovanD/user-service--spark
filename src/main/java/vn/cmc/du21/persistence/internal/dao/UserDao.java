package vn.cmc.du21.persistence.internal.dao;

import vn.cmc.du21.persistence.internal.entity.UserData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<UserData> {
    private final Connection connection;
    private static final String USER_TABLE = "user";

    private static final String ID_COLUMN = "userId";
    private static final String USERNAME_COLUMN = "username";
    private static final String PASSWORD_COLUMN = "password";
    private static final String PASSWORDSALT_COLUMN = "passwordSalt";
    private static final String PASSWORDHASHALGORITHM_COLUMN = "passwordHashAlgorithm";
    private static final String FULLNAME_COLUMN = "fullName";
    private static final String DOB_COLUMN = "dob";
    private static final String GENDER_COLUMN = "gender";
    private static final String EMAIL_COLUMN = "email";
    private static final String CELLPHONE_COLUMN = "cellphone";
    private static final String ADDRESS_COLUMN = "address";
    private static final String CREATETIME_COLUMN = "createTime";
    private static final String CREATEBY_COLUMN = "createBy";
    private static final String UPDATETIME_COLUMN = "updateTime";
    private static final String UPDATEBY_COLUMN = "updateBy";
    private static final String COUNT_COLUMN = "COUNT(*)";

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<UserData> getAll() {
        String sql = String.format("select * from %s", USER_TABLE);
        List<UserData> result = new ArrayList<>();

        try (
                PreparedStatement st = connection.prepareStatement(sql);
                ResultSet rs = st.executeQuery()
        ) {
            while (rs.next()) {
                result.add(new UserData(rs.getLong(ID_COLUMN), rs.getString(USERNAME_COLUMN), rs.getString(PASSWORD_COLUMN),
                        rs.getString(PASSWORDSALT_COLUMN), rs.getString(PASSWORDHASHALGORITHM_COLUMN), rs.getString(FULLNAME_COLUMN),
                        rs.getDate(DOB_COLUMN), rs.getString(GENDER_COLUMN), rs.getString(EMAIL_COLUMN), rs.getString(CELLPHONE_COLUMN),
                        rs.getString(ADDRESS_COLUMN), rs.getTimestamp(CREATETIME_COLUMN), rs.getString(CREATEBY_COLUMN), rs.getTimestamp(UPDATETIME_COLUMN),
                        rs.getString(UPDATEBY_COLUMN)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<UserData> get(Long id) {
        String sql = String.format("select * from %s where %s = ? limit 1", USER_TABLE, ID_COLUMN);

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new UserData(rs.getLong(ID_COLUMN), rs.getString(USERNAME_COLUMN), rs.getString(PASSWORD_COLUMN),
                            rs.getString(PASSWORDSALT_COLUMN), rs.getString(PASSWORDHASHALGORITHM_COLUMN), rs.getString(FULLNAME_COLUMN),
                            rs.getDate(DOB_COLUMN), rs.getString(GENDER_COLUMN), rs.getString(EMAIL_COLUMN), rs.getString(CELLPHONE_COLUMN),
                            rs.getString(ADDRESS_COLUMN), rs.getTimestamp(CREATETIME_COLUMN), rs.getString(CREATEBY_COLUMN), rs.getTimestamp(UPDATETIME_COLUMN),
                            rs.getString(UPDATEBY_COLUMN)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
        String sql = String.format("delete from %s where %s = ?", USER_TABLE, ID_COLUMN);

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(UserData entity) {
        String sql = String.format("insert into %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                USER_TABLE, USERNAME_COLUMN, PASSWORD_COLUMN, PASSWORDSALT_COLUMN, PASSWORDHASHALGORITHM_COLUMN, FULLNAME_COLUMN, DOB_COLUMN,
                GENDER_COLUMN, EMAIL_COLUMN, CELLPHONE_COLUMN, ADDRESS_COLUMN, CREATETIME_COLUMN, CREATEBY_COLUMN, UPDATETIME_COLUMN, UPDATEBY_COLUMN);

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, entity.getUserName());
            st.setString(2, entity.getPassword());
            st.setString(3, entity.getPasswordSalt());
            st.setString(4, entity.getPasswordHashAlgorithm());
            st.setString(5, entity.getFullName());
            st.setDate(6, entity.getDob());
            st.setString(7, entity.getGender());
            st.setString(8, entity.getEmail());
            st.setString(9, entity.getCellphone());
            st.setString(10, entity.getAddress());
            st.setTimestamp(11, entity.getCreateTime());
            st.setString(12, entity.getCreateBy());
            st.setTimestamp(13, entity.getUpdateTime());
            st.setString(14, entity.getUpdateBy());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(UserData entity) {
        String sql = "update "+ USER_TABLE
                +" set "+ PASSWORD_COLUMN +" = '"+ entity.getPassword()
                +"', "+ FULLNAME_COLUMN +" = '"+ entity.getFullName() +"', " + DOB_COLUMN + " = '"+ entity.getDob()
                +"', "+ GENDER_COLUMN +" = '"+ entity.getGender() +"', " + EMAIL_COLUMN + " = '"+ entity.getEmail()
                +"', "+ CELLPHONE_COLUMN +" = '"+ entity.getCellphone() +"', " + ADDRESS_COLUMN + " = '"+ entity.getAddress()
                +"', "+ UPDATETIME_COLUMN +" = '"+ entity.getUpdateTime() +"', " + UPDATEBY_COLUMN + " = '"+ entity.getUpdateBy() +"' "
                +" where "+ ID_COLUMN +" = " + entity.getUserId();

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<UserData> checkUsernameAndPassword(String username, String password) {
        String sql = "select * from "+USER_TABLE+
                " where username = '"+username+"' and password = '"+password+"'";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            try (
                    ResultSet rs = st.executeQuery()
            ) {
                if (rs.next()) {
                    return Optional.of(new UserData(rs.getLong(ID_COLUMN), rs.getString(USERNAME_COLUMN), rs.getString(PASSWORD_COLUMN),
                            rs.getString(PASSWORDSALT_COLUMN), rs.getString(PASSWORDHASHALGORITHM_COLUMN), rs.getString(FULLNAME_COLUMN),
                            rs.getDate(DOB_COLUMN), rs.getString(GENDER_COLUMN), rs.getString(EMAIL_COLUMN), rs.getString(CELLPHONE_COLUMN),
                            rs.getString(ADDRESS_COLUMN), rs.getTimestamp(CREATETIME_COLUMN), rs.getString(CREATEBY_COLUMN), rs.getTimestamp(UPDATETIME_COLUMN),
                            rs.getString(UPDATEBY_COLUMN)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<UserData> get(String username) {
        String sql = "select * from "+USER_TABLE+
                " where username = '"+username+"'";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            try (
                    ResultSet rs = st.executeQuery()
            ) {
                if (rs.next()) {
                    return Optional.of(new UserData(rs.getLong(ID_COLUMN), rs.getString(USERNAME_COLUMN), rs.getString(PASSWORD_COLUMN),
                            rs.getString(PASSWORDSALT_COLUMN), rs.getString(PASSWORDHASHALGORITHM_COLUMN), rs.getString(FULLNAME_COLUMN),
                            rs.getDate(DOB_COLUMN), rs.getString(GENDER_COLUMN), rs.getString(EMAIL_COLUMN), rs.getString(CELLPHONE_COLUMN),
                            rs.getString(ADDRESS_COLUMN), rs.getTimestamp(CREATETIME_COLUMN), rs.getString(CREATEBY_COLUMN), rs.getTimestamp(UPDATETIME_COLUMN),
                            rs.getString(UPDATEBY_COLUMN)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<UserData> getList(String page, String size, String sort) {
        int pageInt = Integer.parseInt(page);
        int sizeInt = Integer.parseInt(size);
        int tgInt = (pageInt - 1) * sizeInt;
        String tgString = String.valueOf(tgInt);
        String sizeStr = String.valueOf(sizeInt);
        String sql = String.format("select * from %s order by "+ sort +" limit "+ tgString + "," + sizeStr, USER_TABLE);
        List<UserData> result = new ArrayList<>();

        try (
                PreparedStatement st = connection.prepareStatement(sql);
                ResultSet rs = st.executeQuery()
        ) {
            while (rs.next()) {
                result.add(new UserData(rs.getLong(ID_COLUMN), rs.getString(USERNAME_COLUMN), rs.getString(PASSWORD_COLUMN),
                        rs.getString(PASSWORDSALT_COLUMN), rs.getString(PASSWORDHASHALGORITHM_COLUMN), rs.getString(FULLNAME_COLUMN),
                        rs.getDate(DOB_COLUMN), rs.getString(GENDER_COLUMN), rs.getString(EMAIL_COLUMN), rs.getString(CELLPHONE_COLUMN),
                        rs.getString(ADDRESS_COLUMN), rs.getTimestamp(CREATETIME_COLUMN), rs.getString(CREATEBY_COLUMN), rs.getTimestamp(UPDATETIME_COLUMN),
                        rs.getString(UPDATEBY_COLUMN)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public String getTotalPage(String size){
        int sizeInt = Integer.parseInt(size);
        String sql = String.format("select COUNT(*) from %s", USER_TABLE);
        String totalRecord = new String();
        try (
                PreparedStatement st = connection.prepareStatement(sql);
                ResultSet rs = st.executeQuery()
        ) {
            while (rs.next()){
                totalRecord = rs.getString(COUNT_COLUMN);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        int totalRecordInt = Integer.parseInt(totalRecord);
        if ( totalRecordInt % sizeInt ==0){
            return String.valueOf(totalRecordInt/sizeInt);
        }else {
            return String.valueOf(totalRecordInt/sizeInt + 1);
        }
    }
    public String getTotalRecord() {
        String sql = String.format("select COUNT(*) from %s", USER_TABLE);
        String totalRecord = new String();
        try (
                PreparedStatement st = connection.prepareStatement(sql);
                ResultSet rs = st.executeQuery()
        ) {
            while (rs.next()) {
                totalRecord = rs.getString(COUNT_COLUMN);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRecord;
    }

    public String checkUserExisted(String username, String email, String phone){
        String sql = "select * from "+USER_TABLE+
                " where username = '"+username+"'";
        String sql1 = "select * from "+USER_TABLE+
                " where email = '"+email+"'";
        String sql2 = "select * from "+USER_TABLE+
                " where cellphone = '"+phone+"'";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            try (
                    ResultSet rs = st.executeQuery()
            ) {
                if (rs.next()) {
                    return "Username existed ";
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        try (PreparedStatement st1 = connection.prepareStatement(sql1)) {
            try (
                    ResultSet rs = st1.executeQuery()
            ) {
                if (rs.next()) {
                    return "Email existed ";
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        try (PreparedStatement st2 = connection.prepareStatement(sql2)) {
            try (
                    ResultSet rs = st2.executeQuery()
            ) {
                if (rs.next()) {
                    return "Cellphone existed ";
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String checkEmailAndPhoneExisted(Long userId, String email, String phone){
        String sql1 = "select * from "+USER_TABLE+
                " where email = '"+email+"' and "+ID_COLUMN+" != "+ userId;
        String sql2 = "select * from "+USER_TABLE+
                " where cellphone = '"+phone+"' and "+ID_COLUMN+" != "+ userId;

        try (PreparedStatement st1 = connection.prepareStatement(sql1)) {
            try (
                    ResultSet rs = st1.executeQuery()
            ) {
                if (rs.next()) {
                    return "Email existed ";
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        try (PreparedStatement st2 = connection.prepareStatement(sql2)) {
            try (
                    ResultSet rs = st2.executeQuery()
            ) {
                if (rs.next()) {
                    return "Cellphone existed ";
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
