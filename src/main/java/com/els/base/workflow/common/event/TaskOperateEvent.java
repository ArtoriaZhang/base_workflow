package com.els.base.workflow.common.event;

import com.els.base.core.entity.user.User;
import com.els.base.core.event.BaseEvent;
import com.els.base.workflow.common.event.TaskOperateEvent;
import java.text.MessageFormat;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public class TaskOperateEvent extends BaseEvent {
    private static final long serialVersionUID = 1L;
    private String processDefinitionKey;
    private String businessKey;
    private ProcessInstance currentProcess;
    private Task currentTask;
    private boolean isPass;
    private boolean isFinished;
    private String businessId;
    private String approveDesc;
    private String assignee;
    private User assigneeUser;

    public String getAssignee() {
        return this.assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getApproveDesc() {
        return this.approveDesc;
    }

    public void setApproveDesc(String approveDesc) {
        this.approveDesc = approveDesc;
    }

    public String getBusinessId() {
        return this.businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getProcessDefinitionKey() {
        return this.processDefinitionKey;
    }

    public String getBusinessKey() {
        return this.businessKey;
    }

    public boolean isPass() {
        return this.isPass;
    }

    public ProcessInstance getCurrentProcess() {
        return this.currentProcess;
    }

    public void setCurrentProcess(ProcessInstance currentProcess) {
        this.currentProcess = currentProcess;
    }

    public boolean isFinished() {
        return this.isFinished;
    }

    public Task getCurrentTask() {
        return this.currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }

    public User getAssigneeUser() {
        return this.assigneeUser;
    }

    public void setAssigneeUser(User assigneeUser) {
        this.assigneeUser = assigneeUser;
    }

    public TaskOperateEvent(Object source, String processDefinitionKey, String businessKey, boolean isPass,
            boolean isFinished, String businessId, String approveDesc, String assignee) {
        super(MessageFormat.format("流程[{0}],业务key[{1}]", new Object[] { processDefinitionKey, businessKey }));
        this.processDefinitionKey = processDefinitionKey;
        this.businessKey = businessKey;
        this.isPass = isPass;
        this.isFinished = isFinished;
        this.businessId = businessId;
        this.approveDesc = approveDesc;
        this.assignee = assignee;
    }

    public TaskOperateEvent(Object source, String processDefinitionKey, String businessKey, boolean isPass,
            boolean isFinished) {
        super(MessageFormat.format("流程[{0}],业务key[{1}]", new Object[] { processDefinitionKey, businessKey }));
        this.isPass = isPass;
        this.isFinished = isFinished;
    }
}
