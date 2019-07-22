package com.els.base.workflow.common.service;

import com.els.base.workflow.common.event.TaskOperateEvent;

public interface ITaskListener {
    void listen(TaskOperateEvent paramTaskOperateEvent);
}
