package com.els.base.workflow.common.entity;

import com.els.base.core.entity.user.User;
import com.els.base.workflow.common.entity.UserReponse;
import java.util.List;

public class UserReponse {
    private int totals;
    private List<User> users;

    public int getTotals() {
        return this.totals;
    }

    public void setTotals(int totals) {
        this.totals = totals;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
