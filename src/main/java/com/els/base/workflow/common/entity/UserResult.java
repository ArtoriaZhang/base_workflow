package com.els.base.workflow.common.entity;

import com.els.base.workflow.common.entity.UserResult;

public class UserResult {
    private String id;
    private String username;
    private String realName;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
