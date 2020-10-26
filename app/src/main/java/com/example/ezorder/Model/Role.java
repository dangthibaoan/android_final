package com.example.ezorder.Model;

public class Role {
    private int roleID;
    private String roleName;

    public Role() {
    }

    public Role(int roleID) {
        this.roleID = roleID;
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role(int roleID, String roleName) {
        this.roleID = roleID;
        this.roleName = roleName;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public String toString() {
        return roleName;
    }

}
