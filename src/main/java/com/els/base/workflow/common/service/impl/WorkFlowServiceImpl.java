package com.els.base.workflow.common.service.impl;

import com.els.base.auth.service.UserRoleService;
import com.els.base.auth.utils.SpringSecurityUtils;
import com.els.base.core.entity.PageView;
import com.els.base.core.entity.user.User;
import com.els.base.core.exception.CommonException;
import com.els.base.core.service.user.UserService;
import com.els.base.core.utils.Assert;
import com.els.base.msg.Message;
import com.els.base.msg.MessageSendUtils;
import com.els.base.utils.SpringContextHolder;
import com.els.base.workflow.common.entity.ProcessStartVO;
import com.els.base.workflow.common.entity.WorkOrderVo;
import com.els.base.workflow.common.event.TaskOperateEvent;
import com.els.base.workflow.common.event.TaskStartEvent;
import com.els.base.workflow.common.service.ITaskListener;
import com.els.base.workflow.common.service.WorkFlowService;
import com.els.base.workflow.common.service.impl.WorkFlowServiceImpl;
import com.els.base.workflow.common.utils.ApproveStatusEnum;
import com.els.base.workflow.common.utils.ProcessVariableNameEnum;
import com.els.base.workflow.common.utils.TaskVariableNameEnum;
import com.els.base.workflow.wfSet.service.WfSetService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.identity.Group;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class WorkFlowServiceImpl implements WorkFlowService {
    @Resource
    protected TaskService taskService;
    @Resource
    protected RuntimeService runtimeService;
    @Resource
    protected HistoryService historyService;
    @Resource
    protected IdentityService identityService;
    @Resource
    protected RepositoryService repositoryService;
    @Resource
    protected UserService userService;
    @Resource
    protected UserRoleService userRoleService;
    @Resource
    protected WfSetService wfSetService;

    @Transactional
    public ApproveStatusEnum completePersonalTask(String taskId, User user, String isPass, String desc) {
        Task task = queryTaskAndVaild(taskId);

        validUser(task, user);

        if (StringUtils.isBlank(task.getAssignee())) {
            this.taskService.claim(taskId, user.getId());
        }

        ProcessInstance currentProcess = (ProcessInstance) this.runtimeService.createProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId()).includeProcessVariables().singleResult();
        if (currentProcess == null || currentProcess.isEnded()) {
            throw new CommonException("该审批流程已经结束");
        }
        ApproveStatusEnum statusEnum = null;

        String processDefinitionKey = (String) currentProcess.getProcessVariables().get("processDefinitionKey");
        String businessKey = currentProcess.getBusinessKey();

        List<ProcessDefinition> processDefinitions = this.repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey).list();
        if (CollectionUtils.isEmpty(processDefinitions)) {
            throw new CommonException("未找到该模块编码,请检查数据!");
        }

        ProcessDefinition definition = (ProcessDefinition) this.repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey).list().get(processDefinitions.size() - 1);
        if (this.repositoryService.isProcessDefinitionSuspended(definition.getId())) {
            try {
                if (isPass.equals("true")) {
                    callListener(currentProcess, user, task, true, true);
                    statusEnum = ApproveStatusEnum.PASS;
                } else if (isPass.equals("false")) {
                    callListener(currentProcess, user, task, false, true);
                    statusEnum = ApproveStatusEnum.REJECT;
                }
            } catch (ClassNotFoundException e) {

                e.printStackTrace();
            }

            ProcessInstance processInstance = (ProcessInstance) this.runtimeService.createProcessInstanceQuery()
                    .processDefinitionKey(processDefinitionKey).processInstanceBusinessKey(businessKey).singleResult();
            this.runtimeService.deleteProcessInstance(processInstance.getId(), "审批流已停用");
            return statusEnum;
        }

        Map<String, Object> taskMap = new HashMap<String, Object>();
        taskMap.put(TaskVariableNameEnum.PASS_STATUS.getVarName(), isPass);
        taskMap.put(TaskVariableNameEnum.APPROVE_DESC.getVarName(), StringUtils.defaultIfBlank(desc, "无"));
        taskMap.put(TaskVariableNameEnum.USER_NAME.getVarName(), user.getNickName());
        taskMap.put(TaskVariableNameEnum.APPROVE_TIME.getVarName(), new Date());

        Map<String, Object> processMap = new HashMap<String, Object>();
        processMap.put(ProcessVariableNameEnum.PASS_STATUS.getVarName(), isPass);

        this.taskService.setVariablesLocal(taskId, taskMap);

        this.taskService.addComment(taskId, task.getProcessInstanceId(), desc);

        this.taskService.complete(taskId, processMap);

        try {
            statusEnum = getApproveResult(currentProcess, user, task, "true".equals(isPass));
        } catch (ClassNotFoundException e) {
            throw new CommonException("审批执行异常", e);
        }
        return statusEnum;
    }

    private ApproveStatusEnum getApproveResult(ProcessInstance currentProcess, User user, Task task, boolean isPass)
            throws ClassNotFoundException {
        ProcessInstance nextProcess = (ProcessInstance) this.runtimeService.createProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId()).includeProcessVariables().singleResult();

        ApproveStatusEnum statusEnum = null;
        boolean isFinish = false;
        if (currentProcess.getActivityId().contains("parallelGateway")) {

            if (nextProcess == null || nextProcess.isEnded()) {
                isFinish = true;
                if (isPass) {

                    statusEnum = ApproveStatusEnum.PASS;

                    callListener(currentProcess, user, task, isPass, isFinish);
                } else {

                    statusEnum = ApproveStatusEnum.REJECT;

                    callListener(currentProcess, user, task, isPass, isFinish);
                }
            } else if (isPass) {

                statusEnum = ApproveStatusEnum.APPLY;

                callListener(currentProcess, user, task, isPass, isFinish);

                String processInstanceId = task.getProcessInstanceId();

                sendNoticeMessage(processInstanceId);
            } else {

                callListener(currentProcess, user, task, false, true);
                statusEnum = ApproveStatusEnum.REJECT;
            }

        } else if (nextProcess == null || nextProcess.isEnded()) {
            isFinish = true;
            if (isPass) {

                statusEnum = ApproveStatusEnum.PASS;

                callListener(currentProcess, user, task, isPass, isFinish);
            } else {

                statusEnum = ApproveStatusEnum.REJECT;

                callListener(currentProcess, user, task, isPass, isFinish);
            }
        } else {

            statusEnum = ApproveStatusEnum.APPLY;

            callListener(currentProcess, user, task, isPass, isFinish);

            String processInstanceId = task.getProcessInstanceId();

            sendNoticeMessage(processInstanceId);
        }

        return statusEnum;
    }

    public void test(String processInstanceId) {
        sendNoticeMessage(processInstanceId);
    }

    private List<String> getAssigneeList(String procInstId) {
        List<Task> taskList = ((TaskQuery) ((TaskQuery) this.taskService.createTaskQuery()
                .processInstanceId(procInstId)).includeTaskLocalVariables()).active().list();

        return taskList.stream().filter(
                task -> (task.getTaskLocalVariables().get(TaskVariableNameEnum.PASS_STATUS.getVarName()) == null))
                .flatMap(task -> getUserIdByTask(task).stream())

                .filter(StringUtils::isNotBlank).distinct().collect(Collectors.toList());
    }

    private List<String> getUserIdByTask(Task task) {
        List<String> userIdList = new ArrayList<String>();
        userIdList.add(task.getAssignee());

        List<IdentityLink> identityLinks = this.taskService.getIdentityLinksForTask(task.getId());

        List<String> candidateUsers = identityLinks.stream().map(IdentityLink::getUserId).collect(Collectors.toList());

        userIdList.addAll(candidateUsers);

        List<String> userInGroups = identityLinks.stream().map(IdentityLink::getGroupId).filter(StringUtils::isNotBlank)
                .flatMap(groupId -> this.userRoleService.queryUserIdsForRoleId(groupId).stream())
                .collect(Collectors.toList());

        userIdList.addAll(userInGroups);
        return userIdList;
    }

    private void sendNoticeMessage(String processInstanceId) {
        Map<String, Object> processVariables = ((ProcessInstance) this.runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).includeProcessVariables().singleResult()).getProcessVariables();

        String businessTypeCode = "WE_CHAT_AUDIT_NOTICE";
        List<String> receiverIdList = getAssigneeList(processInstanceId);
        if (CollectionUtils.isEmpty(receiverIdList)) {
            return;
        }

        Message<Map<String, Object>> message = Message.init(processVariables).setBusinessTypeCode(businessTypeCode)
                .setSenderId("1").setCompanyCode("50000000").addReceiverIdList(receiverIdList);

        MessageSendUtils.sendMessage(message);
    }

    private void callListener(ProcessInstance currentProcess, User user, Task task, boolean isPass, boolean isFinish)
            throws ClassNotFoundException {
        Map<String, Object> processVariables = currentProcess.getProcessVariables();
        String className = (String) processVariables.get(ProcessVariableNameEnum.LISTENER_CLASS.getVarName());

        if (StringUtils.isBlank(className)) {
            return;
        }

        ITaskListener listener = (ITaskListener) SpringContextHolder.getOneBean(Class.forName(className));

        String businessKey = currentProcess.getBusinessKey();
        String processDefKey = currentProcess.getProcessDefinitionKey();
        String businessId = (String) processVariables.get(ProcessVariableNameEnum.BUSINESS_ID.getVarName());
        List<Comment> comments = this.taskService.getTaskComments(task.getId());
        String approveDesc = null;
        if (CollectionUtils.isEmpty(comments)) {
            approveDesc = null;
        } else {
            approveDesc = ((Comment) comments.get(0)).getFullMessage();
        }
        String assignee = user.getNickName();
        TaskOperateEvent event = new TaskOperateEvent(null, processDefKey, businessKey, isPass, isFinish, businessId,
                approveDesc, assignee);

        event.setCurrentProcess(currentProcess);
        event.setCurrentTask(task);
        event.setAssigneeUser(user);
        listener.listen(event);

        SpringContextHolder.getApplicationContext().publishEvent(event);
    }

    private void validUser(Task task, User user) {
        if (StringUtils.isEmpty(task.getAssignee())) {
            ArrayList<String> list = new ArrayList<String>();
            for (IdentityLink identityLink : this.taskService.getIdentityLinksForTask(task.getId())) {
                if (StringUtils.isNotEmpty(identityLink.getUserId())) {
                    list.add(identityLink.getUserId());
                }
            }
            if (!CollectionUtils.isEmpty(list)) {
                if (!list.contains(user.getId())) {
                    throw new CommonException("没有权限执行该审核任务");
                }
            } else {

                List<Group> groupList = this.identityService.createGroupQuery().groupMember(user.getId()).list();
                List<String> groupIdList = new ArrayList<String>();
                for (Group group : groupList) {
                    groupIdList.add(group.getId());
                }
                List<IdentityLink> links = this.taskService.getIdentityLinksForTask(task.getId());
                List<String> linkGroupIds = new ArrayList<String>();
                for (IdentityLink link : links) {
                    linkGroupIds.add(link.getGroupId());
                }

                if (!CollectionUtils.containsAny(groupIdList, linkGroupIds)) {
                    throw new CommonException("没有权限执行该审核任务");
                }
            }

        } else if (!user.getId().equals(task.getAssignee())) {

            throw new CommonException("没有权限执行该审核任务");
        }
    }

    private Task queryTaskAndVaild(String taskId) {
        TaskQuery taskQuery = (TaskQuery) ((TaskQuery) this.taskService.createTaskQuery().includeTaskLocalVariables())
                .taskId(taskId);

        Task task = (Task) taskQuery.singleResult();
        if (task == null) {
            throw new CommonException("该任务id不存在");
        }

        if (task.isSuspended()) {
            throw new CommonException("该审批任务已经暂停");
        }

        return task;
    }

    public ProcessInstance stopProcess(String processDefinitionKey, String businessKey) {
        if (StringUtils.isBlank(processDefinitionKey) || StringUtils.isBlank(businessKey)) {
            throw new CommonException("流程key，业务key，发起人不允许为空");
        }

        List<ProcessInstance> processInstances = this.runtimeService.createProcessInstanceQuery()
                .processDefinitionKey(processDefinitionKey).processInstanceBusinessKey(businessKey).list();
        if (CollectionUtils.isEmpty(processInstances)) {
            throw new CommonException("未找到该模块编码或业务编码,请检查数据!");
        }
        Iterator<ProcessInstance> iterator = processInstances.iterator();
        if (iterator.hasNext()) {
            ProcessInstance processInstance = (ProcessInstance) iterator.next();
            this.runtimeService.deleteProcessInstance(processInstance.getId(), "已作废");
            return processInstance;
        }

        return null;
    }

    public ProcessInstance startProcess(String processDefinitionKey, String businessKey,
            Map<String, Object> processVariable) {
        if (StringUtils.isBlank(processDefinitionKey) || StringUtils.isBlank(businessKey)) {
            throw new CommonException("流程key，业务key，发起人不允许为空");
        }
        ProcessInstance processInstance = null;

        List<ProcessDefinition> processDefinitions = this.repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey).list();
        if (CollectionUtils.isEmpty(processDefinitions)) {
            throw new CommonException("未找到该模块编码,请检查数据!");
        }

        ProcessDefinition definition = (ProcessDefinition) this.repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey).list().get(processDefinitions.size() - 1);
        if (this.repositoryService.isProcessDefinitionSuspended(definition.getId())) {
            complete(processDefinitionKey, businessKey, processVariable);
        } else {

            ProcessInstance currentProcess = (ProcessInstance) this.runtimeService.createProcessInstanceQuery()
                    .processDefinitionKey(processDefinitionKey).processInstanceBusinessKey(businessKey).singleResult();

            if (currentProcess != null) {
                throw new CommonException("业务流程[" + processDefinitionKey + "][" + businessKey + "]，已经启动，无法重复启动");
            }
            processVariable.put(ProcessVariableNameEnum.START_TIME.getVarName(), new Date());
            processVariable.put(ProcessVariableNameEnum.START_USER.getVarName(),
                    ((User) this.userService.queryObjById(SpringSecurityUtils.getLoginUserId())).getNickName());
            processInstance = this.runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey,
                    processVariable);

            List<Task> list = ((TaskQuery) this.taskService.createTaskQuery()
                    .processInstanceId(processInstance.getId())).list();

            TaskStartEvent startEvent = new TaskStartEvent("start process");
            startEvent.setBusinessKey(businessKey);
            startEvent.setProcessDefinitionKey(processDefinitionKey);
            startEvent.setCurrentTask((Task) list.get(0));
            startEvent.setCurrentProcess(processInstance);

            SpringContextHolder.getApplicationContext().publishEvent(startEvent);

            String processInstanceId = processInstance.getId();
            sendNoticeMessage(processInstanceId);
        }
        return processInstance;
    }

    public void complete(String processDefinitionKey, String businessKey, Map<String, Object> processVariable) {
        String className = (String) processVariable.get(ProcessVariableNameEnum.LISTENER_CLASS.getVarName());
        if (StringUtils.isBlank(className)) {
            return;
        }
        ITaskListener listener = null;
        try {
            listener = (ITaskListener) SpringContextHolder.getOneBean(Class.forName(className));
        } catch (ClassNotFoundException e) {

            throw new CommonException("审批执行异常", e);
        }
        String businessId = (String) processVariable.get(ProcessVariableNameEnum.BUSINESS_ID.getVarName());
        TaskOperateEvent event = new TaskOperateEvent(null, processDefinitionKey, businessKey, true, true, businessId,
                null, null);

        listener.listen(event);
        SpringContextHolder.getApplicationContext().publishEvent(event);
    }

    public ProcessInstance startProcess(ProcessStartVO processStartVO) {
        Map<String, Object> processVariable = processStartVO.getProcessVariable();
        if (processVariable == null) {
            processVariable = new HashMap<String, Object>();
        }
        if (processStartVO.getListenerClass() != null) {
            processVariable.put(ProcessVariableNameEnum.LISTENER_CLASS.getVarName(),
                    processStartVO.getListenerClass().getName());
        }
        String processDefinitionKey = processStartVO.getProcessDefinitionKey();
        String businessId = processStartVO.getBusinessId();
        String displayPage = processStartVO.getDisplayPage();
        String businessKey = processStartVO.getBusinessKey();

        processVariable.put("businessKey", businessKey);
        processVariable.put("businessId", businessId);
        processVariable.put("displayPage", displayPage);
        processVariable.put("processDefinitionKey", processDefinitionKey);
        processStartVO.setProcessVariable(processVariable);
        return startProcess(processStartVO.getProcessDefinitionKey(), processStartVO.getBusinessKey(),
                processStartVO.getProcessVariable());
    }

    @Transactional
    public List<WorkOrderVo> findTaskListForProcess(String processDefinitionKey, String businessKey) {
        List<HistoricProcessInstance> pis = this.historyService.createHistoricProcessInstanceQuery()
                .processDefinitionKey(processDefinitionKey).processInstanceBusinessKey(businessKey)
                .includeProcessVariables().list();
        if (CollectionUtils.isEmpty(pis)) {
            throw new CommonException("未找到该模块编码,请检查数据!");
        }
        List<WorkOrderVo> workOrderVoList = new ArrayList<WorkOrderVo>();
        for (HistoricProcessInstance hisProcessInstance : pis) {

            if (hisProcessInstance != null) {
                Map<String, Object> hisProcessMap = hisProcessInstance.getProcessVariables();
                List<HistoricTaskInstance> hisTaskList = findHisTaskByProcessId(hisProcessInstance.getId());
                for (int i = 0; CollectionUtils.isNotEmpty(hisTaskList) && i < hisTaskList.size(); i++) {
                    Map<String, Object> hisTaskVarMap = ((HistoricTaskInstance) hisTaskList.get(i))
                            .getTaskLocalVariables();
                    WorkOrderVo workOrderVo = new WorkOrderVo((HistoricTaskInstance) hisTaskList.get(i),
                            hisProcessInstance, hisProcessMap, hisTaskVarMap);

                    workOrderVoList.add(workOrderVo);
                }
            }
        }

        ProcessInstance currentProcess = (ProcessInstance) this.runtimeService.createProcessInstanceQuery()
                .processDefinitionKey(processDefinitionKey).processInstanceBusinessKey(businessKey)
                .includeProcessVariables().singleResult();

        if (currentProcess == null) {
            return workOrderVoList;
        }

        Map<String, Object> processMap = currentProcess.getProcessVariables();

        TaskQuery taskQuery = (TaskQuery) ((TaskQuery) ((TaskQuery) ((TaskQuery) this.taskService.createTaskQuery()
                .processInstanceBusinessKey(currentProcess.getId())).includeTaskLocalVariables()).orderByTaskId())
                        .asc();

        List<Task> taskList = taskQuery.list();
        for (int i = 0; CollectionUtils.isNotEmpty(taskList) && i < taskList.size(); i++) {
            Map<String, Object> taskMap = ((Task) taskList.get(i)).getTaskLocalVariables();
            WorkOrderVo workOrderVo = new WorkOrderVo((Task) taskList.get(i), currentProcess, processMap, taskMap);
            workOrderVoList.add(workOrderVo);
        }
        return workOrderVoList;
    }

    private List<HistoricTaskInstance> findHisTaskByProcessId(String processInstanceId) {
        HistoricTaskInstanceQuery hisTaskQuery = (HistoricTaskInstanceQuery) ((HistoricTaskInstanceQuery) ((HistoricTaskInstanceQuery) ((HistoricTaskInstanceQuery) this.historyService
                .createHistoricTaskInstanceQuery().processInstanceId(processInstanceId)).includeTaskLocalVariables())
                        .orderByTaskCreateTime()).asc();
        return hisTaskQuery.list();
    }

    public PageView<WorkOrderVo> findTodoTask(PageView<WorkOrderVo> pageView, User user, String title,
            String businessKey, String startUser) {
        Assert.isNotNull(pageView, "分页信息为空");

        List<Group> groupList = this.identityService.createGroupQuery().groupMember(user.getId()).list();
        if (CollectionUtils.isEmpty(groupList)) {
            pageView.setPageNo(1);
            return pageView;
        }

        List<Task> allTask = queryAllTodoTask(user, groupList, title, businessKey, startUser);
        if (CollectionUtils.isEmpty(allTask)) {
            return pageView;
        }

        allTask = distinctAndSort(allTask);

        List<WorkOrderVo> workOrderVoList = completeVo(allTask);

        if (CollectionUtils.isEmpty(workOrderVoList)) {
            return pageView;
        }

        pageView.setRowCount(workOrderVoList.size());
        workOrderVoList = workOrderVoList.subList(pageView.getStartRowNo(), pageView.getEndRowNo());
        pageView.setQueryResult(workOrderVoList);
        return pageView;
    }

    private List<WorkOrderVo> completeVo(List<Task> allTask) {
        if (CollectionUtils.isEmpty(allTask)) {
            return null;
        }

        Set<String> processInstanceIdList = allTask.stream().map(TaskInfo::getProcessInstanceId)
                .collect(Collectors.toSet());

        List<ProcessInstance> processInstanceList = this.runtimeService.createProcessInstanceQuery()
                .processInstanceIds(processInstanceIdList).includeProcessVariables().list();

        return allTask.stream().map(task -> {

            ProcessInstance processInstance = processInstanceList.stream()
                    .filter(tmp -> tmp.getId().equals(task.getParentTaskId())).findAny().orElse(null);

            Map<String, Object> proinstMap = null;
            if (processInstance != null) {
                proinstMap = processInstance.getProcessVariables();
            }

            WorkOrderVo workOrderVo = new WorkOrderVo(task, processInstance, proinstMap, task.getTaskLocalVariables());
            Map<String, Object> map = workOrderVo.getProinstMap();
            if (map != null) {
                Date startTime = (Date) map.get("startTime");
                workOrderVo.setStartTime(startTime);
            }
            return workOrderVo;
        }).collect(Collectors.toList());
    }

    private List<Task> queryAllTodoTask(User user, List<Group> groupList, String title, String businessKey,
            String startUser) {
        List<String> groupNameList = groupList.stream().map(Group::getId).collect(Collectors.toList());

        TaskQuery taskQuery = (TaskQuery) this.taskService.createTaskQuery().taskAssignee(user.getId());

        TaskQuery query = (TaskQuery) this.taskService.createTaskQuery().taskCandidateGroupIn(groupNameList);

        TaskQuery tQuery = (TaskQuery) this.taskService.createTaskQuery().taskCandidateUser(user.getId());
        if (StringUtils.isNotBlank(businessKey)) {
            taskQuery = (TaskQuery) taskQuery.processInstanceBusinessKeyLike("%" + businessKey + "%");
            query = (TaskQuery) query.processInstanceBusinessKeyLike("%" + businessKey + "%");
            tQuery = (TaskQuery) tQuery.processInstanceBusinessKeyLike("%" + businessKey + "%");
        }

        if (StringUtils.isNotBlank(title)) {
            taskQuery = (TaskQuery) taskQuery.processDefinitionKeyLike("%" + title + "%");
            query = (TaskQuery) query.processDefinitionKeyLike("%" + title + "%");
            tQuery = (TaskQuery) tQuery.processDefinitionKeyLike("%" + title + "%");
        }

        if (StringUtils.isNotBlank(startUser)) {
            taskQuery = (TaskQuery) taskQuery.processVariableValueLike("startUser", "%" + startUser + "%");
            query = (TaskQuery) query.processVariableValueLike("startUser", "%" + startUser + "%");
            tQuery = (TaskQuery) tQuery.processVariableValueLike("startUser", "%" + startUser + "%");
        }

        taskQuery = ((TaskQuery) taskQuery.includeTaskLocalVariables()).active();
        query = ((TaskQuery) query.includeTaskLocalVariables()).active();
        tQuery = ((TaskQuery) tQuery.includeTaskLocalVariables()).active();

        List<Task> allTask = new ArrayList<Task>();
        allTask.addAll(taskQuery.list());
        allTask.addAll(query.list());
        allTask.addAll(tQuery.list());
        return allTask;
    }

    private List<Task> distinctAndSort(List<Task> allTask) {
        Set<Task> taskSet = new TreeSet<Task>((t1, t2) -> t1.getId().compareTo(t2.getId()));

        taskSet.addAll(allTask);

        allTask.clear();
        allTask.addAll(taskSet);

        allTask.sort((t1, t2) -> {

            if (t1.getCreateTime() == null) {
                return -1;
            }

            if (t2.getCreateTime() == null) {
                return 1;
            }
            return t2.getCreateTime().compareTo(t1.getCreateTime());
        });
        return allTask;
    }

    public PageView<WorkOrderVo> findMyAppliedTask(PageView<WorkOrderVo> pageView, User user) {
        if (pageView == null) {
            throw new CommonException("分页信息为空");
        }

        List<HistoricTaskInstance> allTask = new ArrayList<HistoricTaskInstance>();

        HistoricTaskInstanceQuery query = (HistoricTaskInstanceQuery) ((HistoricTaskInstanceQuery) ((HistoricTaskInstanceQuery) ((HistoricTaskInstanceQuery) this.historyService
                .createHistoricTaskInstanceQuery().taskAssignee(user.getId())).includeTaskLocalVariables())
                        .orderByTaskCreateTime()).desc();
        List<HistoricTaskInstance> list = query.list();

        allTask.addAll(list);

        pageView.setRowCount(allTask.size());
        List<WorkOrderVo> subTaskList = new ArrayList<WorkOrderVo>(pageView.getPageSize());
        for (HistoricTaskInstance task : allTask.subList(pageView.getStartRowNo(), pageView.getEndRowNo())) {

            HistoricProcessInstance processInstance = (HistoricProcessInstance) this.historyService
                    .createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId())
                    .includeProcessVariables().singleResult();
            Map<String, Object> proinstMap = processInstance.getProcessVariables();

            WorkOrderVo workOrderVo = new WorkOrderVo(task, processInstance, proinstMap, task.getTaskLocalVariables());

            subTaskList.add(workOrderVo);
        }

        pageView.setQueryResult(subTaskList);
        return pageView;
    }

    @Transactional
    public PageView<WorkOrderVo> findDoneTask(PageView<WorkOrderVo> pageView, User user, String title,
            String businessKey, String startUser) {
        if (pageView == null) {
            throw new CommonException("分页信息为空");
        }
        List<Group> groupList = this.identityService.createGroupQuery().groupMember(user.getId()).list();

        if (CollectionUtils.isEmpty(groupList)) {
            pageView.setPageNo(1);
            return pageView;
        }

        HistoricTaskInstanceQuery query = (HistoricTaskInstanceQuery) this.historyService
                .createHistoricTaskInstanceQuery().taskAssignee(user.getId());
        if (StringUtils.isNotBlank(businessKey)) {
            query = (HistoricTaskInstanceQuery) query.processInstanceBusinessKeyLike("%" + businessKey + "%");
        }
        if (StringUtils.isNotBlank(startUser)) {
            query = (HistoricTaskInstanceQuery) query.processVariableValueLike("startUser", "%" + startUser + "%");
        }

        List<HistoricTaskInstance> list = ((HistoricTaskInstanceQuery) ((HistoricTaskInstanceQuery) ((HistoricTaskInstanceQuery) query
                .finished().includeTaskLocalVariables()).orderByTaskCreateTime()).desc()).list();
        List<HistoricTaskInstance> allTask = new ArrayList<HistoricTaskInstance>();
        allTask.addAll(list);
        pageView.setRowCount(allTask.size());

        List<WorkOrderVo> subTaskList = new ArrayList<WorkOrderVo>(pageView.getPageSize());
        if (StringUtils.isNotBlank(title)) {
            for (HistoricTaskInstance task : allTask) {
                subTaskList = completeTask(task, subTaskList);
            }

            subTaskList = subTaskList.stream().filter(i -> i.getTitle().contains(title)).collect(Collectors.toList());
            pageView.setRowCount(subTaskList.size());

            List<WorkOrderVo> sTaskList = new ArrayList<WorkOrderVo>(pageView.getPageSize());
            for (WorkOrderVo task : subTaskList.subList(pageView.getStartRowNo(), pageView.getEndRowNo())) {
                sTaskList.add(task);
            }
            pageView.setQueryResult(sTaskList);
        } else {
            for (HistoricTaskInstance task : allTask.subList(pageView.getStartRowNo(), pageView.getEndRowNo())) {
                subTaskList = completeTask(task, subTaskList);
            }
            pageView.setQueryResult(subTaskList);
        }
        return pageView;
    }

    private List<WorkOrderVo> completeTask(HistoricTaskInstance task, List<WorkOrderVo> subTaskList) {
        HistoricProcessInstance processInstance = (HistoricProcessInstance) this.historyService
                .createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId())
                .includeProcessVariables().singleResult();
        Map<String, Object> proinstMap = processInstance.getProcessVariables();

        WorkOrderVo workOrderVo = new WorkOrderVo(task, processInstance, proinstMap, task.getTaskLocalVariables());
        subTaskList.add(workOrderVo);
        return subTaskList;
    }

    @Transactional
    public List<WorkOrderVo> findDoneTaskByExport(User user, List<String> ids, String businessKey, String title) {
        List<Group> groupList = this.identityService.createGroupQuery().groupMember(user.getId()).list();

        if (CollectionUtils.isEmpty(groupList)) {
            throw new CommonException("导出的数据为空！");
        }

        HistoricTaskInstanceQuery query = (HistoricTaskInstanceQuery) this.historyService
                .createHistoricTaskInstanceQuery().taskAssignee(user.getId());
        if (StringUtils.isNotBlank(businessKey)) {
            query = (HistoricTaskInstanceQuery) query.processInstanceBusinessKeyLike("%" + businessKey + "%");
        }

        List<HistoricTaskInstance> list = ((HistoricTaskInstanceQuery) ((HistoricTaskInstanceQuery) ((HistoricTaskInstanceQuery) query
                .finished().includeTaskLocalVariables()).orderByTaskCreateTime()).desc()).list();
        List<HistoricTaskInstance> allTask = new ArrayList<HistoricTaskInstance>();
        allTask.addAll(list);

        List<WorkOrderVo> subTaskList = new ArrayList<WorkOrderVo>();
        for (HistoricTaskInstance task : allTask) {

            HistoricProcessInstance processInstance = (HistoricProcessInstance) this.historyService
                    .createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId())
                    .includeProcessVariables().singleResult();
            Map<String, Object> proinstMap = processInstance.getProcessVariables();

            WorkOrderVo workOrderVo = new WorkOrderVo(task, processInstance, proinstMap, task.getTaskLocalVariables());
            subTaskList.add(workOrderVo);
        }
        if (CollectionUtils.isNotEmpty(ids)) {
            subTaskList = subTaskList.stream().filter(i -> ids.contains(i.getTaskId())).collect(Collectors.toList());
        }
        if (StringUtils.isNotEmpty(title)) {
            subTaskList = subTaskList.stream().filter(i -> i.getTitle().contains(title)).collect(Collectors.toList());
        }
        return subTaskList;
    }

    @Transactional
    public List<WorkOrderVo> findTodoTaskByExport(User user, List<String> ids, String businessKey, String title) {
        List<Group> groupList = this.identityService.createGroupQuery().groupMember(user.getId()).list();
        if (CollectionUtils.isEmpty(groupList)) {
            throw new CommonException("导出的数据为空！");
        }

        List<String> groupNameList = groupList.stream().map(Group::getId).collect(Collectors.toList());

        TaskQuery taskQuery = (TaskQuery) this.taskService.createTaskQuery().taskAssignee(user.getId());

        TaskQuery query = (TaskQuery) this.taskService.createTaskQuery().taskCandidateGroupIn(groupNameList);

        TaskQuery tQuery = (TaskQuery) this.taskService.createTaskQuery().taskCandidateUser(user.getId());
        if (StringUtils.isNotBlank(businessKey)) {
            taskQuery = (TaskQuery) taskQuery.processInstanceBusinessKeyLike("%" + businessKey + "%");
            query = (TaskQuery) query.processInstanceBusinessKeyLike("%" + businessKey + "%");
            tQuery = (TaskQuery) tQuery.processInstanceBusinessKeyLike("%" + businessKey + "%");
        }
        if (StringUtils.isNotBlank(title)) {
            taskQuery = (TaskQuery) taskQuery.processDefinitionKeyLike("%" + title + "%");
            query = (TaskQuery) query.processDefinitionKeyLike("%" + title + "%");
            tQuery = (TaskQuery) tQuery.processDefinitionKeyLike("%" + title + "%");
        }

        taskQuery = ((TaskQuery) taskQuery.includeTaskLocalVariables()).active();
        query = ((TaskQuery) query.includeTaskLocalVariables()).active();
        tQuery = ((TaskQuery) tQuery.includeTaskLocalVariables()).active();

        List<Task> allTask = new ArrayList<Task>();
        allTask.addAll(taskQuery.list());
        allTask.addAll(query.list());
        allTask.addAll(tQuery.list());

        allTask = distinctAndSort(allTask);

        List<WorkOrderVo> subTaskList = new ArrayList<WorkOrderVo>();

        for (Task task : allTask) {

            ProcessInstance processInstance = (ProcessInstance) this.runtimeService.createProcessInstanceQuery()
                    .processInstanceId(task.getProcessInstanceId()).includeProcessVariables().singleResult();
            Map<String, Object> proinstMap = processInstance.getProcessVariables();

            WorkOrderVo workOrderVo = new WorkOrderVo(task, processInstance, proinstMap, task.getTaskLocalVariables());
            Map<String, Object> map = workOrderVo.getProinstMap();
            Date startTime = (Date) map.get("startTime");
            workOrderVo.setStartTime(startTime);
            subTaskList.add(workOrderVo);
        }
        if (CollectionUtils.isNotEmpty(ids)) {
            subTaskList = subTaskList.stream().filter(i -> ids.contains(i.getTaskId())).collect(Collectors.toList());
        }
        return subTaskList;
    }

    public List<WorkOrderVo> findTaskListForProcess(String processInstanceId) {
        List<HistoricProcessInstance> pis = this.historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId).includeProcessVariables().list();
        if (CollectionUtils.isEmpty(pis)) {
            throw new CommonException("未找到该模块编码,请检查数据!");
        }
        List<WorkOrderVo> workOrderVoList = new ArrayList<WorkOrderVo>();
        for (HistoricProcessInstance hisProcessInstance : pis) {

            if (hisProcessInstance != null) {
                Map<String, Object> hisProcessMap = hisProcessInstance.getProcessVariables();
                List<HistoricTaskInstance> hisTaskList = findHisTaskByProcessId(hisProcessInstance.getId());
                for (int i = 0; CollectionUtils.isNotEmpty(hisTaskList) && i < hisTaskList.size(); i++) {
                    Map<String, Object> hisTaskVarMap = ((HistoricTaskInstance) hisTaskList.get(i))
                            .getTaskLocalVariables();
                    WorkOrderVo workOrderVo = new WorkOrderVo((HistoricTaskInstance) hisTaskList.get(i),
                            hisProcessInstance, hisProcessMap, hisTaskVarMap);

                    workOrderVoList.add(workOrderVo);
                }
            }
        }

        ProcessInstance currentProcess = (ProcessInstance) this.runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).includeProcessVariables().singleResult();

        if (currentProcess == null) {
            return workOrderVoList;
        }

        Map<String, Object> processMap = currentProcess.getProcessVariables();

        TaskQuery taskQuery = (TaskQuery) ((TaskQuery) ((TaskQuery) ((TaskQuery) this.taskService.createTaskQuery()
                .processInstanceBusinessKey(currentProcess.getId())).includeTaskLocalVariables()).orderByTaskId())
                        .asc();

        List<Task> taskList = taskQuery.list();
        for (int i = 0; CollectionUtils.isNotEmpty(taskList) && i < taskList.size(); i++) {
            Map<String, Object> taskMap = ((Task) taskList.get(i)).getTaskLocalVariables();
            WorkOrderVo workOrderVo = new WorkOrderVo((Task) taskList.get(i), currentProcess, processMap, taskMap);
            workOrderVoList.add(workOrderVo);
        }
        return workOrderVoList;
    }
}
