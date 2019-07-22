package com.els.base.workflow.common.service;

import com.els.base.core.entity.PageView;
import com.els.base.core.entity.user.User;
import com.els.base.workflow.common.entity.ProcessStartVO;
import com.els.base.workflow.common.entity.WorkOrderVo;
import com.els.base.workflow.common.utils.ApproveStatusEnum;
import java.util.List;
import java.util.Map;
import org.activiti.engine.runtime.ProcessInstance;

public interface WorkFlowService {
    ProcessInstance startProcess(String paramString1, String paramString2, Map<String, Object> paramMap);

    ProcessInstance startProcess(ProcessStartVO paramProcessStartVO);

    ProcessInstance stopProcess(String paramString1, String paramString2);

    ApproveStatusEnum completePersonalTask(String paramString1, User paramUser, String paramString2,
            String paramString3);

    List<WorkOrderVo> findTaskListForProcess(String paramString1, String paramString2);

    PageView<WorkOrderVo> findTodoTask(PageView<WorkOrderVo> paramPageView, User paramUser, String paramString1,
            String paramString2, String paramString3);

    PageView<WorkOrderVo> findDoneTask(PageView<WorkOrderVo> paramPageView, User paramUser, String paramString1,
            String paramString2, String paramString3);

    PageView<WorkOrderVo> findMyAppliedTask(PageView<WorkOrderVo> paramPageView, User paramUser);

    void test(String paramString);

    List<WorkOrderVo> findDoneTaskByExport(User paramUser, List<String> paramList, String paramString1,
            String paramString2);

    List<WorkOrderVo> findTodoTaskByExport(User paramUser, List<String> paramList, String paramString1,
            String paramString2);

    List<WorkOrderVo> findTaskListForProcess(String paramString);
}
