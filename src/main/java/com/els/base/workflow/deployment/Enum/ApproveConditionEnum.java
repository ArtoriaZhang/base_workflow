package com.els.base.workflow.deployment.Enum;

import com.els.base.workflow.deployment.Enum.ApproveConditionEnum;

public enum ApproveConditionEnum {
    EVERYONE(Integer.valueOf(0), "所有人"), ANYONE(Integer.valueOf(1), "任意一人");
    private String desc;
    private Integer value;

    ApproveConditionEnum(Integer value, String desc) {
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
