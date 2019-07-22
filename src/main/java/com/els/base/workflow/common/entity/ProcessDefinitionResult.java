package com.els.base.workflow.common.entity;

import com.els.base.workflow.common.entity.ProcessDefinitionResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import org.activiti.engine.repository.ProcessDefinition;

@ApiModel("流程实体")
public class ProcessDefinitionResult implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("版本")
    private String category;
    @ApiModelProperty("流程名称")
    private String name;
    @ApiModelProperty("key")
    private String key;
    private String description;
    private int version;
    @ApiModelProperty("资源名称")
    private String resourceName;
    @ApiModelProperty("部署id")
    private String deploymentId;
    @ApiModelProperty("流程图资源")
    private String diagramResourceName;
    private boolean hasStartFormKey;
    private boolean hasGraphicalNotation;
    private boolean isSuspended;
    private String tenantId;

    public ProcessDefinitionResult() {
    }

    public ProcessDefinitionResult(ProcessDefinition p) {
        this.id = p.getId();
        this.category = p.getCategory();
        this.name = p.getName();
        this.key = p.getKey();
        this.description = p.getDescription();
        this.version = p.getVersion();
        this.resourceName = p.getResourceName();
        this.deploymentId = p.getDeploymentId();
        this.diagramResourceName = p.getDiagramResourceName();
        this.hasStartFormKey = p.hasStartFormKey();
        this.hasGraphicalNotation = p.hasGraphicalNotation();
        this.isSuspended = p.isSuspended();
        this.tenantId = p.getTenantId();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getResourceName() {
        return this.resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getDeploymentId() {
        return this.deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getDiagramResourceName() {
        return this.diagramResourceName;
    }

    public void setDiagramResourceName(String diagramResourceName) {
        this.diagramResourceName = diagramResourceName;
    }

    public boolean isHasStartFormKey() {
        return this.hasStartFormKey;
    }

    public void setHasStartFormKey(boolean hasStartFormKey) {
        this.hasStartFormKey = hasStartFormKey;
    }

    public boolean isHasGraphicalNotation() {
        return this.hasGraphicalNotation;
    }

    public void setHasGraphicalNotation(boolean hasGraphicalNotation) {
        this.hasGraphicalNotation = hasGraphicalNotation;
    }

    public boolean isSuspended() {
        return this.isSuspended;
    }

    public void setSuspended(boolean isSuspended) {
        this.isSuspended = isSuspended;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
