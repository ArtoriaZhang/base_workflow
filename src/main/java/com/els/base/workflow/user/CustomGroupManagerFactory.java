package com.els.base.workflow.user;

import com.els.base.workflow.user.CustomGroupEntityManager;
import com.els.base.workflow.user.CustomGroupManagerFactory;
import javax.annotation.Resource;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.springframework.stereotype.Service;

@Service
public class CustomGroupManagerFactory implements SessionFactory {
    @Resource
    private CustomGroupEntityManager customGroupEntityManager;

    public Class<?> getSessionType() {
        return org.activiti.engine.impl.persistence.entity.GroupIdentityManager.class;
    }

    public Session openSession() {
        return new CustomGroupEntityManager();
    }

    public void setCustomGroupEntityManager(CustomGroupEntityManager customGroupEntityManager) {
        this.customGroupEntityManager = customGroupEntityManager;
    }
}
