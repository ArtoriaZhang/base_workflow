package com.els.base.workflow.common.entity;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import org.activiti.engine.repository.Deployment;

public class DeploymentResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编号")
    private String id;

    @ApiModelProperty("流程名称")
    private String name;

    @ApiModelProperty("部署时间")
    private Date deploymentTime;

    @ApiModelProperty("")
    private String category;

    @ApiModelProperty("")
    private String tenantId;

    public DeploymentResponse(Deployment deployment) {
        setId(deployment.getId());
        setName(deployment.getName());
        setDeploymentTime(deployment.getDeploymentTime());
        setCategory(deployment.getCategory());
        setTenantId(deployment.getTenantId());
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeploymentTime() {
        return this.deploymentTime;
    }

    public void setDeploymentTime(Date deploymentTime) {
        this.deploymentTime = deploymentTime;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantId() {
        return this.tenantId;
    }
}