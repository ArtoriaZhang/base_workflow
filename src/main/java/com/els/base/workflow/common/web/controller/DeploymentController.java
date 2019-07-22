package com.els.base.workflow.common.web.controller;

import com.els.base.core.entity.PageView;
import com.els.base.core.entity.ResponseResult;
import com.els.base.core.utils.Assert;
import com.els.base.workflow.common.entity.ProcessDefinitionResult;
import com.els.base.workflow.common.web.controller.DeploymentController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api("流程列表")
@RestController
@RequestMapping({ "deployments" })
public class DeploymentController {
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private RuntimeService runtimeService;

    @ApiOperation(httpMethod = "POST", value = "查询流程管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", required = false, value = "所在页", paramType = "query", dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", required = false, value = "每页数量", paramType = "query", dataType = "String", defaultValue = "10") })
    @RequestMapping({ "service/findByPage" })
    @ResponseBody
    public ResponseResult<PageView<ProcessDefinitionResult>> findByPage(@RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageView<ProcessDefinitionResult> pageView = new PageView<ProcessDefinitionResult>(pageNo, pageSize);

        ProcessDefinitionQuery processDefinitionQuery = this.repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.orderByDeploymentId().desc()
                .listPage(pageSize * (pageNo - 1), pageSize);
        if (CollectionUtils.isNotEmpty(processDefinitionList)) {

            List<ProcessDefinitionResult> result = processDefinitionList.stream()
                    .map(processDefinition -> new ProcessDefinitionResult(processDefinition))
                    .collect(Collectors.toList());
            pageView.setQueryResult(result);
        }

        long count = this.repositoryService.createDeploymentQuery().count();
        pageView.setRowCount((int) count);

        return ResponseResult.success(pageView);
    }

    @ApiOperation(httpMethod = "POST", value = "查询流程")
    @RequestMapping({ "service/findById" })
    @ResponseBody
    public ResponseResult<Deployment> findById(@RequestParam(required = true) String id) {
        Assert.isNotBlank(id, "查询失败,id不能为空");
        Deployment deployment = (Deployment) this.repositoryService.createDeploymentQuery().deploymentId(id)
                .singleResult();
        return ResponseResult.success(deployment);
    }

    @ApiOperation(httpMethod = "POST", value = "删除流程")
    @RequestMapping({ "service/deleteById" })
    @ResponseBody
    public ResponseResult<String> deleteById(@RequestParam(required = true) String id) {
        Assert.isNotBlank(id, "删除失败,id不能为空");
        this.repositoryService.deleteDeployment(id);
        return ResponseResult.success();
    }
}
