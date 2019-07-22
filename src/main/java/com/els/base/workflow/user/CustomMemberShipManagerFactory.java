package com.els.base.workflow.user;

import com.els.base.workflow.user.CustomMembershipEntityManager;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;

public class CustomMemberShipManagerFactory implements SessionFactory {
    public Class<?> getSessionType() {
        return org.activiti.engine.impl.persistence.entity.MembershipEntityManager.class;
    }

    public Session openSession() {
        return new CustomMembershipEntityManager();
    }
}
