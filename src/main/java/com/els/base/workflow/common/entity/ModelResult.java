package com.els.base.workflow.common.entity;

import com.els.base.workflow.common.entity.ModelResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

@ApiModel("模型实体")
public class ModelResult implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("流程名称")
    private String name;
    @ApiModelProperty("键值")
    private String key;
    @ApiModelProperty("版本")
    private Integer version = Integer.valueOf(1);

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date lastUpdateTime;
    @ApiModelProperty("修改版本号")
    private int revision = 1;

    private String category;

    private String metaInfo;

    private String deploymentId;

    private String editorSourceValueId;

    private String editorSourceExtraValueId;

    private String tenantId = "";

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRevision() {
        return this.revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
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

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getMetaInfo() {
        return this.metaInfo;
    }

    public void setMetaInfo(String metaInfo) {
        this.metaInfo = metaInfo;
    }

    public String getDeploymentId() {
        return this.deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getEditorSourceValueId() {
        return this.editorSourceValueId;
    }

    public void setEditorSourceValueId(String editorSourceValueId) {
        this.editorSourceValueId = editorSourceValueId;
    }

    public String getEditorSourceExtraValueId() {
        return this.editorSourceExtraValueId;
    }

    public void setEditorSourceExtraValueId(String editorSourceExtraValueId) {
        this.editorSourceExtraValueId = editorSourceExtraValueId;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
