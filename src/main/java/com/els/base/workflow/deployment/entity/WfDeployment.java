package com.els.base.workflow.deployment.entity;

import com.els.base.workflow.deployment.entity.WfDeployment;
import com.els.base.workflow.wfSet.entity.WfSet;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel("审批流")
public class WfDeployment implements Serializable {
    @ApiModelProperty("审批流程的节点设置")
    private List<WfSet> wfSetList;
    @ApiModelProperty("序号")
    private String id;
    @ApiModelProperty("菜单编码")
    private String businessCode;

    public List<WfSet> getWfSetList() {
        return this.wfSetList;
    }

    @ApiModelProperty("菜单名称")
    private String businessName;
    @ApiModelProperty("审批流名称")
    private String name;
    @ApiModelProperty("是否启用审批流,是1,否0")
    private Integer isEnable;
    @ApiModelProperty("创建人")
    private String createUser;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("修改人")
    private String updateUser;

    public void setWfSetList(List<WfSet> wfSetList) {
        this.wfSetList = wfSetList;
    }

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("备注")
    private String remark;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = (id == null) ? null : id.trim();
    }

    public String getBusinessCode() {
        return this.businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = (businessCode == null) ? null : businessCode.trim();
    }

    public String getBusinessName() {
        return this.businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = (businessName == null) ? null : businessName.trim();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = (name == null) ? null : name.trim();
    }

    public Integer getIsEnable() {
        return this.isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = (createUser == null) ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = (updateUser == null) ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = (remark == null) ? "" : remark.trim();
    }
}
