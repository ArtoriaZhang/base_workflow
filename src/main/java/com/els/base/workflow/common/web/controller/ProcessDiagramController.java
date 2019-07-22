package com.els.base.workflow.common.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.HMProcessDiagramGenerator;
import org.activiti.spring.ProcessEngineFactoryBean;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("processDiagram")
public class ProcessDiagramController {

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private HistoryService historyService;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private ProcessEngineFactoryBean processEngineFactoryBean;

    @Resource
    private ProcessEngineConfiguration processEngineConfiguration;

    @ApiOperation(httpMethod = "POST", value = "获取流程图")
    @RequestMapping("service/getShineProcImage")
    @ResponseBody
    public ModelAndView getShineProcImage(@RequestParam(required = true) String processInstanceId,
            HttpServletResponse httpResponse) throws IOException {

        InputStream imageStream = generateStream(processInstanceId, true);

        httpResponse.setContentType("image/png");
        ServletOutputStream out = httpResponse.getOutputStream();

        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        out.write(imageByteArray);

        out.flush();
        out.close();

        return null;
    }

    private InputStream generateStream(String processInstanceId, boolean needCurrent) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        String processDefinitionId = null;
        List<String> executedActivityIdList = new ArrayList<String>();
        List<String> currentActivityIdList = new ArrayList<>();
        List<HistoricActivityInstance> historicActivityInstanceList = new ArrayList<>();
        if (processInstance != null) {
            processDefinitionId = processInstance.getProcessDefinitionId();
            if (needCurrent) {
                currentActivityIdList = this.runtimeService.getActiveActivityIds(processInstance.getId());
            }
        }
        if (historicProcessInstance != null) {
            processDefinitionId = historicProcessInstance.getProcessDefinitionId();
            historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(processInstanceId).orderByHistoricActivityInstanceId().asc().list();
            for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
                executedActivityIdList.add(activityInstance.getActivityId());
            }
        }

        if (StringUtils.isEmpty(processDefinitionId) || executedActivityIdList.isEmpty()) {
            return null;
        }

        // 高亮线路id集合
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) repositoryService
                .getProcessDefinition(processDefinitionId);
        List<String> highLightedFlows = getHighLightedFlows(definitionEntity, historicActivityInstanceList);

        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        processEngineConfiguration = processEngineFactoryBean.getProcessEngineConfiguration();

        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);

        HMProcessDiagramGenerator diagramGenerator = (HMProcessDiagramGenerator) processEngineConfiguration
                .getProcessDiagramGenerator();

        InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", executedActivityIdList,
                highLightedFlows, processEngineFactoryBean.getProcessEngineConfiguration().getActivityFontName(),
                processEngineFactoryBean.getProcessEngineConfiguration().getLabelFontName(), "宋体", null, 1.0,
                currentActivityIdList);

        return imageStream;
    }

    /**
     * 获取需要高亮的线
     * 
     * @param processDefinitionEntity
     * @param historicActivityInstances
     * @return
     */
    private List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity,
            List<HistoricActivityInstance> historicActivityInstances) {

        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {// 对历史流程节点进行遍历
            ActivityImpl activityImpl = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i).getActivityId());// 得到节点定义的详细信息
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
            ActivityImpl sameActivityImpl1 = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i + 1).getActivityId());
            // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);// 后续第一个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);// 后续第二个节点
                if (activityImpl1.getStartTime().equals(activityImpl2.getStartTime())) {
                    // 如果第一个节点和第二个节点开始时间相同保存
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity
                            .findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    // 有不相同跳出循环
                    break;
                }
            }
            List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();// 取出节点的所有出去的线
            for (PvmTransition pvmTransition : pvmTransitions) {
                // 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition.getDestination();
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    // highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }
}
