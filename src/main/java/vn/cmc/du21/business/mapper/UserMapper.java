package vn.cmc.du21.business.mapper;

import vn.cmc.du21.business.model.User;
import vn.cmc.du21.common.DateTimeUtil;
import vn.cmc.du21.persistence.internal.entity.UserData;

import java.sql.Timestamp;

public class UserMapper {

    private UserMapper() {
        super();
    }

    public static UserData userToUserData(User user) {
        Timestamp createTime = user.getCreateTime() == null ? null : DateTimeUtil.localDateTimeToSqlTimestamp(user.getCreateTime());
        String createBy = user.getCreateBy() == null ? null : user.getCreateBy();
        return new UserData(user.getUserId(), user.getUserName(), user.getPassword(), user.getPasswordSalt(), user.getPasswordHashAlgorithm(),
                user.getFullName(), DateTimeUtil.localDateToSqlDate(user.getDob()), user.getGender(), user.getEmail(), user.getCellphone(),
                user.getAddress(), createTime, createBy,
                DateTimeUtil.localDateTimeToSqlTimestamp(user.getUpdateTime()), user.getUpdateBy());
    }

    public static User userDataToUser(UserData user) {
        return new User(user.getUserId(), user.getUserName(), user.getPassword(), user.getPasswordSalt(), user.getPasswordHashAlgorithm(),
                user.getFullName(), DateTimeUtil.sqlDateToLocalDate(user.getDob()), user.getGender(), user.getEmail(), user.getCellphone(),
                user.getAddress(),  DateTimeUtil.sqlTimestampToLocalDateTime(user.getCreateTime()),user.getCreateBy(),
                DateTimeUtil.sqlTimestampToLocalDateTime(user.getUpdateTime()), user.getUpdateBy());
    }
}
