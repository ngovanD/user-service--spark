package vn.cmc.du21.persistence.internal.entity;

public class UserRoleData {
    private long userId;
    private long roleId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public UserRoleData(long userId, long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
