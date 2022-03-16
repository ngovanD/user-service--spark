package vn.cmc.du21.presentation.external.mapper;

import vn.cmc.du21.business.model.User;
import vn.cmc.du21.common.DateTimeUtil;
import vn.cmc.du21.presentation.external.request.UserUpsertRequest;
import vn.cmc.du21.presentation.external.response.UserDetailDataResponse;
import vn.cmc.du21.presentation.external.response.UserDetailResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserMapper {

    private UserMapper() {
        super();
    }

    public static User userUpsertRequestToUser(UserUpsertRequest userUpsertRequest) {
        LocalDate dob = DateTimeUtil.stringToLocalDate(userUpsertRequest.getDob());
        LocalDateTime createTime = userUpsertRequest.getCreateTime() == null? null: DateTimeUtil.stringToLocalDateTime(userUpsertRequest.getCreateTime());
        LocalDateTime updateTime = userUpsertRequest.getUpdateTime() == null? null: DateTimeUtil.stringToLocalDateTime(userUpsertRequest.getUpdateTime());
        return new User(userUpsertRequest.getUserId(), userUpsertRequest.getUserName(), userUpsertRequest.getPassword(), userUpsertRequest.getPasswordSalt(), userUpsertRequest.getGetPasswordHashAlgorithm(),
                userUpsertRequest.getFullName(), dob, userUpsertRequest.getGender(), userUpsertRequest.getEmail(), userUpsertRequest.getCellphone(),
                userUpsertRequest.getAddress(),  createTime,userUpsertRequest.getCreateBy(),
                updateTime, userUpsertRequest.getUpdateBy());
    }

    public static UserDetailResponse  userToUserDetailResponse(User user) {

        String dob = DateTimeUtil.localDateToString(user.getDob());
        String createTime = DateTimeUtil.localDateTimeToString(user.getCreateTime());
        String updateTime = DateTimeUtil.localDateTimeToString(user.getUpdateTime());
        return new UserDetailResponse(user.getUserId(), user.getUserName(),
                user.getFullName(), dob, user.getGender(), user.getEmail(), user.getCellphone(),
                user.getAddress(),  createTime,user.getCreateBy(),
                updateTime, user.getUpdateBy());
    }
    public  static UserDetailDataResponse userDetailDataResponse(User user){

        String createTime = DateTimeUtil.localDateTimeToString(user.getCreateTime());
        String updateTime = DateTimeUtil.localDateTimeToString(user.getUpdateTime());

        return new UserDetailDataResponse(user.getUserId(), user.getFullName(),createTime, user.getCreateBy(), updateTime, user.getUpdateBy());
    }
}
