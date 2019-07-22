package com.els.base.workflow.common.entity;

import com.els.base.workflow.common.entity.HistoricProcessInstanceResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import org.activiti.engine.history.HistoricProcessInstance;
import org.springframework.beans.BeanUtils;

@ApiModel("流程实例实体")
public class HistoricProcessInstanceResult implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("流程实例编号")
    private String processInstanceId;
    @ApiModelProperty("流程定义编号")
    private String processDefinitionId;
    @ApiModelProperty("业务主键")
    private String businessKey;
    @ApiModelProperty("开始时间")
    private Date startTime;
    @ApiModelProperty("结束时间")
    private Date endTime;

    public HistoricProcessInstanceResult(HistoricProcessInstance hpi) {
        BeanUtils.copyProperties(hpi, this);
    }

    public String getProcessInstanceId() {
        return this.processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessDefinitionId() {
        return this.processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getBusinessKey() {
        return this.businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
