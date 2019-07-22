package com.els.base.workflow.common.utils;

import com.els.base.workflow.common.utils.TaskVariableNameEnum;

public enum TaskVariableNameEnum {
    APPROVE_TIME("approveTime"),

    APPROVE_DESC("approveDesc"),

    PASS_STATUS("passOrNot"),

    USER_NAME("userName");

    private String varName;

    public String getVarName() {
        return this.varName;
    }

    TaskVariableNameEnum(String varName) {
        this.varName = varName;
    }
}
