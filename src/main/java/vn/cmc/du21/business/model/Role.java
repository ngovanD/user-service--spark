package vn.cmc.du21.business.model;

public class Role {
    private long roleId;
    private String nameRole;

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    public Role(long roleId, String nameRole) {
        this.roleId = roleId;
        this.nameRole = nameRole;
    }
}
