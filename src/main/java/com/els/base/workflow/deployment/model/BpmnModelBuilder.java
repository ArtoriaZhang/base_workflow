package com.els.base.workflow.deployment.model;

import com.els.base.auth.service.UserRoleService;
import com.els.base.core.entity.user.User;
import com.els.base.core.entity.user.UserExample;
import com.els.base.core.exception.CommonException;
import com.els.base.core.service.user.UserService;
import com.els.base.utils.SpringContextHolder;
import com.els.base.workflow.deployment.Enum.ApproveConditionEnum;
import com.els.base.workflow.deployment.entity.WfDeployment;
import com.els.base.workflow.deployment.model.BpmnModelBuilder;
import com.els.base.workflow.wfSet.entity.WfSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.ParallelGateway;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class BpmnModelBuilder {
    public BpmnModel build(WfDeployment wfDeployment) {
        UserService userService = (UserService) SpringContextHolder.getOneBean(UserService.class);

        BpmnModel model = new BpmnModel();
        Process process = new Process();
        model.addProcess(process);

        String PROCESSID = wfDeployment.getBusinessCode();
        String PROCESSNAME = wfDeployment.getName();
        process.setId(PROCESSID);
        process.setName(PROCESSNAME);
        List<WfSet> wfSetList = wfDeployment.getWfSetList();

        int size = wfSetList.size();

        process.addFlowElement(createStartEvent());

        for (int i = 0; i < size; i++) {
            WfSet wfSet = (WfSet) wfSetList.get(i);
            List<String> userList = new ArrayList<String>();
            String s = wfSet.getAssigneeId();

            if (StringUtils.isBlank(s) && StringUtils.isBlank(wfSet.getGroupId())) {
                throw new CommonException("办理人或办理组不能同时为空!");
            }

            if (wfSet.getConditionSetUp().equals(ApproveConditionEnum.EVERYONE.getValue())) {

                process.addFlowElement(createParallelGateway("parallelGateway-fork" + i, ""));

                if (StringUtils.isBlank(wfSet.getGroupId())) {

                    if (s.contains(",")) {
                        List<String> list = Arrays.asList(s.split(","));
                        for (int j = 0; j < list.size(); j++) {
                            userList.add(list.get(j));
                        }
                        for (int u = 0; u < userList.size(); u++) {
                            String userName = "";
                            if (StringUtils.isNotBlank((String) list.get(u))) {
                                UserExample example = new UserExample();
                                example.createCriteria().andIdEqualTo((String) list.get(u));
                                List<User> users = userService.queryAllObjByExample(example);
                                if (CollectionUtils.isNotEmpty(users)) {
                                    userName = ((User) users.get(0)).getLoginName() + "_"
                                            + ((User) users.get(0)).getNickName();
                                }
                            }

                            process.addFlowElement(
                                    createUserTask("userTask" + i + u, userName, (String) userList.get(u)));
                        }

                        process.addFlowElement(createParallelGateway("parallelGateway-join" + i, ""));

                    } else if (!s.contains(",")) {
                        throw new CommonException("所有人任务下,办理人必须大于或等于2个!");
                    }
                } else if (StringUtils.isBlank(s)) {

                    UserRoleService userRoleService = (UserRoleService) SpringContextHolder
                            .getOneBean(UserRoleService.class);
                    List<String> list = userRoleService.queryUserIdsForRoleId(wfSet.getGroupId());
                    if (CollectionUtils.isEmpty(list)) {
                        throw new CommonException("角色下用户为空!");
                    }
                    if (list.size() > 1) {
                        for (int u = 0; u < list.size(); u++) {

                            String userName = "";
                            if (StringUtils.isNotBlank((String) list.get(u))) {
                                UserExample example = new UserExample();
                                example.createCriteria().andIdEqualTo((String) list.get(u));
                                List<User> users = userService.queryAllObjByExample(example);
                                if (CollectionUtils.isNotEmpty(users)) {
                                    userName = ((User) users.get(0)).getLoginName() + "_"
                                            + ((User) users.get(0)).getNickName();
                                }
                            }

                            process.addFlowElement(createUserTask("userTask" + i + u, userName, (String) list.get(u)));
                        }

                        process.addFlowElement(createParallelGateway("parallelGateway-join" + i, ""));
                    } else if (list.size() == 1) {
                        throw new CommonException("所有人办理下,角色下的办理人必须大于或等于2个!");
                    }

                }

            } else if (StringUtils.isBlank(s)) {

                process.addFlowElement(createGroupTask("task" + i, wfSet.getGroupId(), wfSet.getGroupId()));
            } else if (StringUtils.isBlank(wfSet.getGroupId())) {

                String userName = "";

                if (!s.contains(",")) {

                    UserExample example = new UserExample();
                    example.createCriteria().andIdEqualTo(wfSet.getAssigneeId());
                    List<User> users = userService.queryAllObjByExample(example);
                    if (CollectionUtils.isNotEmpty(users)) {
                        userName = ((User) users.get(0)).getLoginName() + "_" + ((User) users.get(0)).getNickName();
                    }

                    process.addFlowElement(createUserTask("task" + i, userName, wfSet.getAssigneeId()));
                } else if (s.contains(",")) {

                    String[] strs = wfSet.getAssigneeId().split(",");
                    List<String> ids = new ArrayList<String>();
                    for (String str : strs) {
                        ids.add(str);
                    }

                    UserExample example = new UserExample();
                    example.createCriteria().andIdIn(ids);
                    List<User> users = userService.queryAllObjByExample(example);
                    if (CollectionUtils.isNotEmpty(users)) {
                        for (User user : users) {
                            userName = userName + user.getNickName() + ",";
                        }
                        userName = userName.substring(0, userName.length() - 1);
                    }

                    process.addFlowElement(createUsersTask("task" + i, userName, wfSet.getAssigneeId()));
                }
            }
        }

        process.addFlowElement(createEndEvent());

        for (int y = 0; y < size; y++) {
            WfSet wfSet = (WfSet) wfSetList.get(y);

            if (wfSet.getConditionSetUp().equals(ApproveConditionEnum.EVERYONE.getValue())) {

                if (y == 0) {

                    process.addFlowElement(createSequenceFlow("startEvent", "parallelGateway-fork" + y, "", ""));

                } else if (((WfSet) wfSetList.get(y - 1)).getConditionSetUp()
                        .equals(ApproveConditionEnum.EVERYONE.getValue())) {
                    process.addFlowElement(
                            createSequenceFlow("parallelGateway-join" + (y - 1), "parallelGateway-fork" + y, "", ""));
                } else {

                    process.addFlowElement(createSequenceFlow("task" + (y - 1), "parallelGateway-fork" + y, "",
                            "${passOrNotAll=='true'}"));

                    process.addFlowElement(
                            createSequenceFlow("task" + (y - 1), "endEvent", "", "${passOrNotAll=='false'}"));
                }

                List<String> userList = new ArrayList<String>();
                String s = wfSet.getAssigneeId();
                if (StringUtils.isBlank(wfSet.getGroupId())) {
                    if (s.contains(",")) {
                        List<String> list = Arrays.asList(s.split(","));
                        for (int j = 0; j < list.size(); j++) {
                            userList.add(list.get(j));
                        }
                        for (int u = 0; u < userList.size(); u++) {
                            process.addFlowElement(
                                    createSequenceFlow("parallelGateway-fork" + y, "userTask" + y + u, "", ""));

                            process.addFlowElement(createSequenceFlow("userTask" + y + u, "parallelGateway-join" + y,
                                    "", "${passOrNotAll=='true'}"));

                            process.addFlowElement(
                                    createSequenceFlow("userTask" + y + u, "endEvent", "", "${passOrNotAll=='false'}"));

                        }

                    }

                } else if (StringUtils.isBlank(s)) {

                    UserRoleService userRoleService = (UserRoleService) SpringContextHolder
                            .getOneBean(UserRoleService.class);
                    List<String> list = userRoleService.queryUserIdsForRoleId(wfSet.getGroupId());
                    if (CollectionUtils.isEmpty(list)) {
                        throw new CommonException("角色下办理人为空!");
                    }
                    if (list.size() > 1) {
                        for (int u = 0; u < list.size(); u++) {
                            process.addFlowElement(
                                    createSequenceFlow("parallelGateway-fork" + y, "userTask" + y + u, "", ""));

                            process.addFlowElement(createSequenceFlow("userTask" + y + u, "parallelGateway-join" + y,
                                    "", "${passOrNotAll=='true'}"));

                            process.addFlowElement(
                                    createSequenceFlow("userTask" + y + u, "endEvent", "", "${passOrNotAll=='false'}"));
                        }
                    }
                }

                if (y == size - 1) {
                    process.addFlowElement(createSequenceFlow("parallelGateway-join" + y, "endEvent", "", ""));

                }

            } else {

                if (y == 0) {

                    process.addFlowElement(createSequenceFlow("startEvent", "task" + y, "", ""));

                } else if (((WfSet) wfSetList.get(y - 1)).getConditionSetUp()
                        .equals(ApproveConditionEnum.EVERYONE.getValue())) {

                    process.addFlowElement(createSequenceFlow("parallelGateway-join" + (y - 1), "task" + y, "", ""));
                } else {

                    process.addFlowElement(
                            createSequenceFlow("task" + (y - 1), "task" + y, "", "${passOrNotAll=='true'}"));
                    process.addFlowElement(
                            createSequenceFlow("task" + (y - 1), "endEvent", "", "${passOrNotAll=='false'}"));
                }

                if (y == size - 1) {

                    process.addFlowElement(createSequenceFlow("task" + y, "endEvent", "", "${passOrNotAll=='true'}"));

                    process.addFlowElement(createSequenceFlow("task" + y, "endEvent", "", "${passOrNotAll=='false'}"));
                }
            }
        }
        return model;
    }

    protected static UserTask createGroupTask(String id, String name, String candidateGroup) {
        List<String> candidateGroups = new ArrayList<String>();
        candidateGroups.add(candidateGroup);
        UserTask userTask = new UserTask();
        userTask.setName(name);
        userTask.setId(id);
        userTask.setCandidateGroups(candidateGroups);
        return userTask;
    }

    protected static UserTask createUserTask(String id, String name, String assignee) {
        UserTask userTask = new UserTask();
        userTask.setName(name);
        userTask.setAssignee(assignee);
        userTask.setId(id);
        return userTask;
    }

    protected static UserTask createUsersTask(String id, String name, String candidateUser) {
        List<String> candidateUsers = new ArrayList<String>();
        candidateUsers.add(candidateUser);
        UserTask userTask = new UserTask();
        userTask.setName(name);
        userTask.setId(id);
        userTask.setCandidateUsers(candidateUsers);
        return userTask;
    }

    protected static SequenceFlow createSequenceFlow(String from, String to, String name, String conditionExpression) {
        SequenceFlow flow = new SequenceFlow();
        flow.setSourceRef(from);
        flow.setTargetRef(to);
        flow.setName(name);
        if (StringUtils.isNotEmpty(conditionExpression)) {
            flow.setConditionExpression(conditionExpression);
        }
        return flow;
    }

    protected static ExclusiveGateway createExclusiveGateway(String id, String name) {
        ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
        exclusiveGateway.setId(id);
        exclusiveGateway.setName(name);
        return exclusiveGateway;
    }

    protected static ParallelGateway createParallelGateway(String id, String name) {
        ParallelGateway gateway = new ParallelGateway();
        gateway.setId(id);
        gateway.setName(name);
        return gateway;
    }

    protected static StartEvent createStartEvent() {
        StartEvent startEvent = new StartEvent();
        startEvent.setId("startEvent");
        return startEvent;
    }

    protected static EndEvent createEndEvent() {
        EndEvent endEvent = new EndEvent();
        endEvent.setId("endEvent");
        return endEvent;
    }
}
