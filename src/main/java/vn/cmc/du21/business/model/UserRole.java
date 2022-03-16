package vn.cmc.du21.business.model;

public class UserRole {
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

    public UserRole(long userId, long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
