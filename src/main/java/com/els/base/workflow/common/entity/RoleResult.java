package com.els.base.workflow.common.entity;

import com.els.base.auth.entity.Role;
import com.els.base.workflow.common.entity.RoleResult;

public class RoleResult {
    private String id;
    private String remark;
    private String roleName;

    public RoleResult(Role role) {
        this.id = role.getId();
        this.remark = role.getRoleName();
        this.roleName = role.getRoleCode();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
