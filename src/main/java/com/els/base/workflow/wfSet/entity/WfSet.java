package com.els.base.workflow.wfSet.entity;

import com.els.base.workflow.wfSet.entity.WfSet;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel("审批流设置")
public class WfSet implements Serializable {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("当前审批")
    private String currentProcess;
    @ApiModelProperty("条件设置(0=所有人,1=任意一人)")
    private Integer conditionSetUp;
    @ApiModelProperty("审批设置")
    private String condition;
    @ApiModelProperty("审批角色组")
    private String approveGroup;
    @ApiModelProperty("审批人")
    private String approveAssignee;
    @ApiModelProperty("审批角色组ID")
    private String groupId;
    @ApiModelProperty("审批人ID")
    private String assigneeId;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("部署关联id")
    private String deploymentId;
    private static final long serialVersionUID = 1L;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = (id == null) ? null : id.trim();
    }

    public String getCurrentProcess() {
        return this.currentProcess;
    }

    public void setCurrentProcess(String currentProcess) {
        this.currentProcess = (currentProcess == null) ? null : currentProcess.trim();
    }

    public Integer getConditionSetUp() {
        return this.conditionSetUp;
    }

    public void setConditionSetUp(Integer conditionSetUp) {
        this.conditionSetUp = conditionSetUp;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = (condition == null) ? null : condition.trim();
    }

    public String getApproveGroup() {
        return this.approveGroup;
    }

    public void setApproveGroup(String approveGroup) {
        this.approveGroup = (approveGroup == null) ? null : approveGroup.trim();
    }

    public String getApproveAssignee() {
        return this.approveAssignee;
    }

    public void setApproveAssignee(String approveAssignee) {
        this.approveAssignee = (approveAssignee == null) ? null : approveAssignee.trim();
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = (groupId == null) ? null : groupId.trim();
    }

    public String getAssigneeId() {
        return this.assigneeId;
    }

    public void setAssigneeId(String assigneeId) {
        this.assigneeId = (assigneeId == null) ? null : assigneeId.trim();
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = (remark == null) ? null : remark.trim();
    }

    public String getDeploymentId() {
        return this.deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = (deploymentId == null) ? null : deploymentId.trim();
    }
}
