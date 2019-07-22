package com.els.base.workflow.test;

import com.els.base.core.entity.ResponseResult;
import com.els.base.workflow.test.DeployController;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;
import javax.servlet.http.HttpServletResponse;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;









@Controller
@RequestMapping({"actDeploy"})
public class DeployController
{
  @Autowired
  private TaskService taskService;
  @Autowired
  private RepositoryService repositoryService;
  @Autowired
  private RuntimeService runtimeService;
  @Autowired
  private ProcessEngine processEngine;
  
  @RequestMapping({"front/deployBpmn"})
  @ResponseBody
  public ResponseResult<String> deployBpmn(@RequestParam(required = true) MultipartFile file, String resourceName) throws IOException {
    Deployment deployment = this.repositoryService.createDeployment().addInputStream(resourceName, file.getInputStream()).deploy();
    return ResponseResult.success(String.valueOf(this.repositoryService.createProcessDefinitionQuery().count()));
  }

  
  @RequestMapping({"front/deployBpmnZip"})
  @ResponseBody
  public ResponseResult<String> deployBpmnZip(@RequestParam(required = true) MultipartFile file, String resourceName) throws IOException {
    this.repositoryService.createDeployment()
      .addZipInputStream(new ZipInputStream(file.getInputStream())).deploy();
    return ResponseResult.success(String.valueOf(this.repositoryService.createProcessDefinitionQuery().count()));
  }
  
  @RequestMapping({"front/queryProcess"})
  @ResponseBody
  public ResponseResult<List<String>> queryProcess() {
    ProcessDefinitionQuery pdq = this.repositoryService.createProcessDefinitionQuery();
    List<ProcessDefinition> list = pdq.list();
    List<String> strList = new ArrayList<String>();
    for (ProcessDefinition pd : list) {
      strList.add(MessageFormat.format("key[{0}], id[{1}], resourceName[{2}], diagramResourceName[{3}]", new Object[] { pd.getKey(), pd.getId(), pd.getResourceName(), pd.getDiagramResourceName() }));
    } 
    
    return ResponseResult.success(strList);
  }
  
  @RequestMapping({"front/startProcess"})
  @ResponseBody
  public ResponseResult<String> startProcess(String key) {
    this.runtimeService.startProcessInstanceByKey(key);
    
    return ResponseResult.success();
  }













  
  @RequestMapping({"front/getResoucesName"})
  @ResponseBody
  public ResponseResult<String> getResoucesName(String deploymentId, String resourceName, HttpServletResponse response) {
    List<String> names = this.repositoryService.getDeploymentResourceNames(deploymentId);
    String imageName = null;
    String nameList = "";
    for (String name : names) {
      if (name.indexOf(".png") >= 0) {
        imageName = name;
      }
      nameList = nameList + name;
    } 
    
    return ResponseResult.success(StringUtils.defaultIfBlank(imageName, nameList));
  }

  
  @RequestMapping({"front/queryResources"})
  @ResponseBody
  public void queryResources(String deploymentId, String resourceName, HttpServletResponse response) throws IOException {
    InputStream resourceStream = this.repositoryService.getResourceAsStream(deploymentId, resourceName);
    byte[] b = new byte[1024];
    while (resourceStream.read(b, 0, 1024) != -1) {
      response.getOutputStream().write(b, 0, 1024);
    }
  }

  
  @RequestMapping({"front/getProcessImage"})
  public void getProcessImage(String pid, HttpServletResponse response) throws IOException {
    BpmnModel bpmnModel = this.repositoryService.getBpmnModel(pid);



    
    InputStream resourceStream = this.processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator().generatePngDiagram(bpmnModel);
    
    byte[] b = new byte[1024];
    while (resourceStream.read(b, 0, 1024) != -1) {
      response.getOutputStream().write(b, 0, 1024);
    }
  }
  
  @RequestMapping({"front/deleteDeployment"})
  @ResponseBody
  public ResponseResult<String> deleteDeployment(String deploymentId) {
    this.repositoryService.deleteDeployment(deploymentId, true);
    
    return ResponseResult.success();
  }
  
  @RequestMapping({"front/queryDeployment"})
  @ResponseBody
  public ResponseResult<List<String>> queryDeployment() {
    DeploymentQuery dq = this.repositoryService.createDeploymentQuery();
    List<Deployment> list = dq.list();
    List<String> strList = new ArrayList<String>();
    for (Deployment deployment : list)
    {
      strList.add(deployment.getId() + " " + deployment.getName() + " " + deployment.getTenantId());
    }
    
    return ResponseResult.success(strList);
  }
}


