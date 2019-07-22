package com.els.base.workflow.user;

import com.els.base.workflow.user.CustomUserEntityManager;
import com.els.base.workflow.user.CustomUserManagerFactory;
import javax.annotation.Resource;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.springframework.stereotype.Service;

@Service
public class CustomUserManagerFactory implements SessionFactory {
    @Resource
    private CustomUserEntityManager customUserEntityManager;

    public Class<?> getSessionType() {
        return org.activiti.engine.impl.persistence.entity.UserIdentityManager.class;
    }

    public Session openSession() {
        return new CustomUserEntityManager();
    }

    public void setCustomUserEntityManager(CustomUserEntityManager customUserEntityManager) {
        this.customUserEntityManager = customUserEntityManager;
    }
}
