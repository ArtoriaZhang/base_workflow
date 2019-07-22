package com.els.base.workflow.common.event;

import com.els.base.core.event.BaseEvent;
import com.els.base.workflow.common.event.TaskStartEvent;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public class TaskStartEvent extends BaseEvent {
    private static final long serialVersionUID = 1L;
    private String processDefinitionKey;
    private String businessKey;
    private ProcessInstance currentProcess;
    private Task currentTask;

    public String getProcessDefinitionKey() {
        return this.processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public String getBusinessKey() {
        return this.businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public ProcessInstance getCurrentProcess() {
        return this.currentProcess;
    }

    public void setCurrentProcess(ProcessInstance currentProcess) {
        this.currentProcess = currentProcess;
    }

    public Task getCurrentTask() {
        return this.currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }

    public TaskStartEvent(Object source) {
        super(source);
    }
}
