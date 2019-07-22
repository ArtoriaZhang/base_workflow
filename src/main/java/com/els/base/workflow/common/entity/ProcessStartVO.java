package com.els.base.workflow.common.entity;

import com.els.base.core.utils.Assert;
import com.els.base.workflow.common.entity.ProcessStartVO;
import com.els.base.workflow.common.service.ITaskListener;
import java.util.Map;

public class ProcessStartVO {
    String processDefinitionKey;
    String businessKey;
    Map<String, Object> processVariable;
    Class<? extends ITaskListener> listenerClass;
    String displayPage;
    String businessId;

    public String getBusinessId() {
        return this.businessId;
    }

    public ProcessStartVO setBusinessId(String businessId) {
        this.businessId = businessId;
        return this;
    }

    public String getProcessDefinitionKey() {
        return this.processDefinitionKey;
    }

    public String getBusinessKey() {
        return this.businessKey;
    }

    public Map<String, Object> getProcessVariable() {
        return this.processVariable;
    }

    public ProcessStartVO setProcessVariable(Map<String, Object> processVariable) {
        this.processVariable = processVariable;
        return this;
    }

    public Class<? extends ITaskListener> getListenerClass() {
        return this.listenerClass;
    }

    public ProcessStartVO setListenerClass(Class<? extends ITaskListener> listenerClass) {
        this.listenerClass = listenerClass;
        return this;
    }

    public String getDisplayPage() {
        return this.displayPage;
    }

    public ProcessStartVO setDisplayPage(String displayPage) {
        this.displayPage = displayPage;
        return this;
    }

    private ProcessStartVO() {
    }

    private ProcessStartVO(String processDefinitionKey, String businessKey) {
        this.processDefinitionKey = processDefinitionKey;
        this.businessKey = businessKey;
    }

    public static ProcessStartVO newInstance(String processDefinitionKey, String businessKey) {
        Assert.isNotBlank(processDefinitionKey, "流程key不能为空");
        Assert.isNotBlank(businessKey, "业务key不能为空");

        return new ProcessStartVO(processDefinitionKey, businessKey);
    }

    public static ProcessStartVO newInstance(String processDefinitionKey, String businessKey, String businessId) {
        return newInstance(processDefinitionKey, businessKey).setBusinessId(businessId);
    }

    public static ProcessStartVO newInstance(String processDefinitionKey, String businessKey, String businessId,
            String displayPage) {
        return newInstance(processDefinitionKey, businessKey).setBusinessId(businessId).setDisplayPage(displayPage);
    }

    public static void main(String[] args) {
        ProcessStartVO vo = newInstance(null, null).setDisplayPage(null).setListenerClass(null).setProcessVariable(null)
                .setBusinessId(null);
    }
}
