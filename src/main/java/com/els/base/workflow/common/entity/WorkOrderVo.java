package com.els.base.workflow.common.entity;

import com.els.base.workflow.common.entity.WorkOrderVo;
import com.els.base.workflow.common.utils.ProcessVariableNameEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;

@ApiModel("工作流的内容")
public class WorkOrderVo implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("流程定义key")
    String processDefinitionKey;
    @ApiModelProperty("流程实例id")
    String processInstanceId;
    @ApiModelProperty("当前任务环节key")
    String taskDefinitionKey;
    @ApiModelProperty("当前任务环节名称")
    String taskDefinitionName;
    @ApiModelProperty("工作流流程")
    private ProcessInstance processInstanc;
    @ApiModelProperty("其他流程实例变量")
    Map<String, Object> proinstMap;
    @ApiModelProperty("其他任务变量")
    Map<String, Object> taskMap;
    @ApiModelProperty("工作流任务")
    private Task task;
    @ApiModelProperty("工单标题")
    String title;
    @ApiModelProperty("任务id")
    String taskId;
    @ApiModelProperty("业务编号")
    String businessKey;
    @ApiModelProperty("发起人，一般为用户唯一标识")
    String startUser;
    @ApiModelProperty("发起时间")
    Date startTime;
    @ApiModelProperty("当前审批人")
    String assignee;
    @ApiModelProperty("企业名称")
    String companyName;

    public WorkOrderVo() {
    }

    public WorkOrderVo(Task task, ProcessInstance processInstance, Map<String, Object> processMap,
            Map<String, Object> taskMap) {
        this.processDefinitionKey = processInstance.getProcessDefinitionKey();
        this.processInstanceId = processInstance.getId();
        this.businessKey = processInstance.getBusinessKey();
        this.title = processInstance.getProcessDefinitionName();
        this.taskId = task.getId();
        this.assignee = task.getAssignee();
        this.taskDefinitionKey = task.getTaskDefinitionKey();
        this.taskDefinitionName = task.getName();
        this.taskMap = taskMap;
        this.proinstMap = processMap;
        this.companyName = StringUtils
                .defaultIfBlank((String) processMap.get(ProcessVariableNameEnum.COMPANY_NAME.getVarName()), "");
    }

    public WorkOrderVo(HistoricTaskInstance task, HistoricProcessInstance processInstance,
            Map<String, Object> processMap, Map<String, Object> taskMap) {
        this.processDefinitionKey = processInstance.getProcessDefinitionKey();
        this.processInstanceId = processInstance.getId();
        this.businessKey = processInstance.getBusinessKey();
        this.title = processInstance.getProcessDefinitionName();
        this.taskId = task.getId();
        this.assignee = task.getAssignee();
        this.taskDefinitionKey = task.getTaskDefinitionKey();
        this.taskDefinitionName = task.getName();
        this.taskMap = taskMap;
        this.proinstMap = processMap;
        this.companyName = StringUtils
                .defaultIfBlank((String) processMap.get(ProcessVariableNameEnum.COMPANY_NAME.getVarName()), "");
    }

    public String getProcessDefinitionKey() {
        return this.processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public String getProcessInstanceId() {
        return this.processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getBusinessKey() {
        return this.businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getStartUser() {
        return this.startUser;
    }

    public void setStartUser(String startUser) {
        this.startUser = startUser;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getAssignee() {
        return this.assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getTaskDefinitionKey() {
        return this.taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public String getTaskDefinitionName() {
        return this.taskDefinitionName;
    }

    public void setTaskDefinitionName(String taskDefinitionName) {
        this.taskDefinitionName = taskDefinitionName;
    }

    public Map<String, Object> getProinstMap() {
        return this.proinstMap;
    }

    public void setProinstMap(Map<String, Object> proinstMap) {
        this.proinstMap = proinstMap;
    }

    public Map<String, Object> getTaskMap() {
        return this.taskMap;
    }

    public void setTaskMap(Map<String, Object> taskMap) {
        this.taskMap = taskMap;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
