package com.els.base.workflow.common.utils;

import com.els.base.workflow.common.utils.ApproveStatusEnum;

public enum ApproveStatusEnum {
    APPLY(Integer.valueOf(100), "申请中"),

    PASS(Integer.valueOf(200), "已通过"),

    REJECT(Integer.valueOf(300), "已拒绝");

    private Integer value;
    private String desc;

    ApproveStatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return this.value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
