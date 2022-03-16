package vn.cmc.du21.business.mapper;

import vn.cmc.du21.business.model.Role;
import vn.cmc.du21.business.model.User;
import vn.cmc.du21.common.DateTimeUtil;
import vn.cmc.du21.persistence.internal.entity.RoleData;
import vn.cmc.du21.persistence.internal.entity.UserData;

import java.sql.Timestamp;

public class RoleMapper {
    private RoleMapper() {
        super();
    }

    public static RoleData roleToRoleData(Role role) {
        return new RoleData(role.getRoleId(), role.getNameRole());
    }

    public static Role roleDataToRole(RoleData roleData) {
        return new Role(roleData.getRoleId(), roleData.getNameRole());
    }

}
