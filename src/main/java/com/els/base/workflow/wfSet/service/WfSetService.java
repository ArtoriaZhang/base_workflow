package com.els.base.workflow.wfSet.service;

import com.els.base.core.service.BaseService;
import com.els.base.workflow.wfSet.entity.WfSet;
import com.els.base.workflow.wfSet.entity.WfSetExample;
import com.els.base.workflow.wfSet.service.WfSetService;

public interface WfSetService extends BaseService<WfSet, WfSetExample, String> {
    void deleteByExample(WfSetExample paramWfSetExample);
}
