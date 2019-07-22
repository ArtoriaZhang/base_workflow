package com.els.base.workflow.common.utils;

import com.els.base.workflow.common.utils.ProcessVariableNameEnum;

public enum ProcessVariableNameEnum {
    START_TIME("startTime"),

    START_USER("startUser"),

    BUSINESS_ID("businessId"),

    BUSINESS_KEY("businessKey"),

    COMPANY_NAME("companyName"),

    PASS_STATUS("passOrNotAll"),

    LISTENER_CLASS("listenerClass"),

    TITLE("title");

    private String varName;

    public String getVarName() {
        return this.varName;
    }

    ProcessVariableNameEnum(String varName) {
        this.varName = varName;
    }
}
