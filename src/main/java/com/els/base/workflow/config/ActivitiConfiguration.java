package com.els.base.workflow.config;

import com.els.base.workflow.config.ActivitiConfiguration;
import com.els.base.workflow.user.CustomGroupManagerFactory;
import com.els.base.workflow.user.CustomUserManagerFactory;
import java.util.Arrays;
import javax.sql.DataSource;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public class ActivitiConfiguration {
    @Autowired
    DataSource dataSource;
    @Autowired
    DataSourceTransactionManager transactionManager;
    @Autowired
    CustomUserManagerFactory userManagerFactory;
    @Autowired
    CustomGroupManagerFactory groupManagerFactory;

    @Bean({ "processEngineConfiguration" })
    public SpringProcessEngineConfiguration getProcessEngineConfiguration() {
        SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();
        config.setDataSource(this.dataSource);
        config.setTransactionManager(this.transactionManager);
        config.setDatabaseSchemaUpdate("false");
        config.setJobExecutorActivate(false);
        config.setCreateDiagramOnDeploy(true);
        config.setDatabaseTablePrefix("T_");

        config.setCustomSessionFactories(
                Arrays.asList(new SessionFactory[] { this.groupManagerFactory, this.userManagerFactory }));
        return config;
    }

    @Autowired
    @Bean({ "processEngine" })
    public ProcessEngineFactoryBean getProcessEngine(ProcessEngineConfigurationImpl processEngineConfiguration) {
        ProcessEngineFactoryBean processEngine = new ProcessEngineFactoryBean();
        processEngine.setProcessEngineConfiguration(processEngineConfiguration);
        return processEngine;
    }

    @Autowired
    @Bean({ "identityService" })
    public IdentityService getIdentityService(ProcessEngine processEngine) {
        return processEngine.getIdentityService();
    }

    @Autowired
    @Bean({ "formService" })
    public FormService getFormService(ProcessEngine processEngine) {
        return processEngine.getFormService();
    }

    @Autowired
    @Bean({ "repositoryService" })
    public RepositoryService getRepositoryService(ProcessEngine processEngine) {
        return processEngine.getRepositoryService();
    }

    @Autowired
    @Bean({ "runtimeService" })
    public RuntimeService getRuntimeService(ProcessEngine processEngine) {
        return processEngine.getRuntimeService();
    }

    @Autowired
    @Bean({ "taskService" })
    public TaskService getTaskService(ProcessEngine processEngine) {
        return processEngine.getTaskService();
    }

    @Autowired
    @Bean({ "historyService" })
    public HistoryService getHistoryService(ProcessEngine processEngine) {
        return processEngine.getHistoryService();
    }

    @Autowired
    @Bean({ "managementService" })
    public ManagementService getManagementService(ProcessEngine processEngine) {
        return processEngine.getManagementService();
    }
}
