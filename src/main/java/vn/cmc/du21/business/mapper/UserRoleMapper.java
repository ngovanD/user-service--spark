package vn.cmc.du21.business.mapper;

import vn.cmc.du21.business.model.Role;
import vn.cmc.du21.business.model.UserRole;
import vn.cmc.du21.persistence.internal.entity.RoleData;
import vn.cmc.du21.persistence.internal.entity.UserRoleData;

public class UserRoleMapper {
    private UserRoleMapper() {
        super();
    }

    public static UserRoleData userRoleToUserRoleData(UserRole userRole) {
        return new UserRoleData(userRole.getUserId(), userRole.getRoleId());
    }

    public static UserRole userRoleDataToUserRole(UserRoleData userRoleData) {
        return new UserRole(userRoleData.getUserId(), userRoleData.getRoleId());
    }
}
