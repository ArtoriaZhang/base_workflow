package com.els.base.workflow.user;

import com.els.base.workflow.user.CustomMembershipEntityManager;
import org.activiti.engine.impl.persistence.entity.MembershipEntityManager;

public class CustomMembershipEntityManager extends MembershipEntityManager {
    public void createMembership(String userId, String groupId) {
        super.createMembership(userId, groupId);
    }

    public void deleteMembership(String userId, String groupId) {
        super.deleteMembership(userId, groupId);
    }
}
