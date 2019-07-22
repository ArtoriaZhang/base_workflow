package com.els.base.workflow.common.web.controller;

import com.els.base.auth.entity.Role;
import com.els.base.auth.utils.SpringSecurityUtils;
import com.els.base.core.entity.PageView;
import com.els.base.core.entity.ResponseResult;
import com.els.base.core.utils.Assert;
import com.els.base.file.entity.FileData;
import com.els.base.utils.SpringContextHolder;
import com.els.base.utils.excel.ExcelUtils;
import com.els.base.utils.excel.TitleAndModelKey;
import com.els.base.workflow.common.entity.WorkOrderVo;
import com.els.base.workflow.common.service.WorkFlowService;
import com.els.base.workflow.common.utils.ExcelFileData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;
import org.activiti.engine.RepositoryService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Api("工作流")
@Controller
@RequestMapping({ "workFlow" })
public class WorkFlowController {
    @ApiOperation(httpMethod = "POST", value = "部署流程图")
    @RequestMapping({ "service/deployBpmnZip" })
    @ResponseBody
    public ResponseResult<String> deployBpmnZip(@RequestParam(required = true) MultipartFile file) throws IOException {
        RepositoryService repositoryService = (RepositoryService) SpringContextHolder
                .getOneBean(RepositoryService.class);

        repositoryService.createDeployment().addZipInputStream(new ZipInputStream(file.getInputStream())).deploy();
        return ResponseResult.success(String.valueOf(repositoryService.createProcessDefinitionQuery().count()));
    }

    @ApiOperation(httpMethod = "GET", value = "查找当前角色的已完成的任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", required = false, value = "所在页", paramType = "query", dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", required = false, value = "每页数量", paramType = "query", dataType = "String", defaultValue = "10"),
            @ApiImplicitParam(name = "title", required = false, value = "待办名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "businessKey", required = false, value = "业务编码", paramType = "query", dataType = "String") })
    @RequestMapping({ "service/findDoneTask" })
    @ResponseBody
    public ResponseResult<PageView<WorkOrderVo>> findDoneTask(@RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String title,
            @RequestParam(required = false) String businessKey, @RequestParam(required = false) String startUser) {
        List<Role> roleList = SpringSecurityUtils.getLoginUserRoleList();

        if (CollectionUtils.isEmpty(roleList)) {
            return ResponseResult.success(new PageView<>(pageNo, pageSize));
        }

        WorkFlowService workFlowService = (WorkFlowService) SpringContextHolder.getOneBean(WorkFlowService.class);

        PageView<WorkOrderVo> pageView = new PageView<WorkOrderVo>(pageNo, pageSize);
        return ResponseResult.success(workFlowService.findDoneTask(pageView, SpringSecurityUtils.getLoginUser(), title,
                businessKey, startUser));
    }

    @ApiOperation(httpMethod = "GET", value = "查找当前角色的任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", required = false, value = "所在页", paramType = "query", dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", required = false, value = "每页数量", paramType = "query", dataType = "String", defaultValue = "10"),
            @ApiImplicitParam(name = "title", required = false, value = "待办名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "businessKey", required = false, value = "业务编码", paramType = "query", dataType = "String") })
    @RequestMapping({ "service/findTodoTask" })
    @ResponseBody
    public ResponseResult<PageView<WorkOrderVo>> findTodoTask(@RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String title,
            @RequestParam(required = false) String businessKey, @RequestParam(required = false) String startUser) {
        List<Role> roleList = SpringSecurityUtils.getLoginUserRoleList();
        if (CollectionUtils.isEmpty(roleList)) {
            return ResponseResult.success(new PageView<>(pageNo, pageSize));
        }

        WorkFlowService workFlowService = (WorkFlowService) SpringContextHolder.getOneBean(WorkFlowService.class);

        PageView<WorkOrderVo> pageView = new PageView<WorkOrderVo>(pageNo, pageSize);
        return ResponseResult.success(workFlowService.findTodoTask(pageView, SpringSecurityUtils.getLoginUser(), title,
                businessKey, startUser));
    }

    @ApiOperation(value = "已办导出Excel", httpMethod = "POST")
    @RequestMapping({ "service/exportForDoneDownload" })
    @ResponseBody
    public ResponseResult<FileData> exportForPurCompanyVerDownload(@RequestBody(required = false) List<String> ids,
            String businessKey, String title) throws Exception {
        WorkFlowService workFlowService = (WorkFlowService) SpringContextHolder.getOneBean(WorkFlowService.class);
        List<WorkOrderVo> vos = workFlowService.findDoneTaskByExport(SpringSecurityUtils.getLoginUser(), ids,
                businessKey, title);
        for (WorkOrderVo workOrderVo : vos) {
            Map<String, Object> proinstMap = workOrderVo.getProinstMap();
            String startUser = (String) proinstMap.get("startUser");
            workOrderVo.setStartUser(startUser);
        }

        List<TitleAndModelKey> titleAndModelKeys = new ArrayList<TitleAndModelKey>();
        titleAndModelKeys.add(ExcelUtils.createTitleAndModelKey("已办名称", "title"));
        titleAndModelKeys.add(ExcelUtils.createTitleAndModelKey("业务编码", "businessKey"));
        titleAndModelKeys.add(ExcelUtils.createTitleAndModelKey("制单人", "startUser"));

        FileData fileData = ExcelFileData.createExcelFileOutputStream(titleAndModelKeys, vos, "已办审批", "已办审批", 0);

        return ResponseResult.success(fileData);
    }

    @ApiOperation(value = "待办导出Excel", httpMethod = "POST")
    @RequestMapping({ "service/exportForTodoTDownload" })
    @ResponseBody
    public ResponseResult<FileData> exportForTodoTDownload(@RequestBody(required = false) List<String> ids,
            String businessKey, String title) throws Exception {
        WorkFlowService workFlowService = (WorkFlowService) SpringContextHolder.getOneBean(WorkFlowService.class);
        List<WorkOrderVo> vos = workFlowService.findTodoTaskByExport(SpringSecurityUtils.getLoginUser(), ids,
                businessKey, title);
        for (WorkOrderVo workOrderVo : vos) {
            Map<String, Object> proinstMap = workOrderVo.getProinstMap();
            String startUser = (String) proinstMap.get("startUser");
            workOrderVo.setStartUser(startUser);
        }

        List<TitleAndModelKey> titleAndModelKeys = new ArrayList<TitleAndModelKey>();
        titleAndModelKeys.add(ExcelUtils.createTitleAndModelKey("待办名称", "title"));
        titleAndModelKeys.add(ExcelUtils.createTitleAndModelKey("业务编码", "businessKey"));
        titleAndModelKeys.add(ExcelUtils.createTitleAndModelKey("制单人", "startUser"));

        FileData fileData = ExcelFileData.createExcelFileOutputStream(titleAndModelKeys, vos, "已办审批", "已办审批", 0);

        return ResponseResult.success(fileData);
    }

    @ApiOperation(httpMethod = "POST", value = "审批指定的任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", required = true, value = "任务id", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "isPass", required = true, value = "是否审核通过，通过返回true，拒绝返回false", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "desc", required = true, value = "审批意见", paramType = "query", dataType = "String") })
    @RequestMapping({ "service/completePersonalTask" })
    @ResponseBody
    public ResponseResult<String> completePersonalTask(@RequestParam(required = true) String taskId,
            @RequestParam(required = true) String isPass, @RequestParam(required = true) String desc) {
        String userName = SpringSecurityUtils.getLoginUser().getNickName();

        WorkFlowService workFlowService = (WorkFlowService) SpringContextHolder.getOneBean(WorkFlowService.class);
        return ResponseResult.success(workFlowService
                .completePersonalTask(taskId, SpringSecurityUtils.getLoginUser(), isPass, desc).getDesc());
    }

    @ApiOperation(httpMethod = "POST", value = "批量审批指定的任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isPass", required = true, value = "是否审核通过，通过返回true，拒绝返回false", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "desc", required = true, value = "审批意见", paramType = "query", dataType = "String") })
    @RequestMapping({ "service/completePersonalTasks" })
    @ResponseBody
    public ResponseResult<String> completePersonalTasks(@RequestBody(required = true) List<String> taskIds,
            @RequestParam(required = true) String isPass, @RequestParam(required = true) String desc) {
        String userName = SpringSecurityUtils.getLoginUser().getNickName();
        WorkFlowService workFlowService = (WorkFlowService) SpringContextHolder.getOneBean(WorkFlowService.class);

        Assert.isNotEmpty(taskIds, "任务ID不能为空！");
        for (String taskId : taskIds) {
            Assert.isNotBlank(taskId, "任务ID不能为空！");
        }
        Assert.isNotBlank(isPass, "是否通过类型不能为空！");
        Assert.isNotBlank(desc, "审批意见不能为空！");

        for (String taskId : taskIds) {
            workFlowService.completePersonalTask(taskId, SpringSecurityUtils.getLoginUser(), isPass, desc).getDesc();
        }
        return ResponseResult.success();
    }

    @ApiOperation(httpMethod = "POST", value = "根据流程与业务编码，查询任务情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processDefinitionKey", required = true, value = "流程定义code", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "businessKey", required = true, value = "业务编码", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "approveStatus", required = true, value = "审批状态", paramType = "query", dataType = "String") })
    @RequestMapping({ "service/findDetailForProcess" })
    @ResponseBody
    public ResponseResult<List<WorkOrderVo>> findTaskListForProcess(
            @RequestParam(required = true) String processDefinitionKey,
            @RequestParam(required = true) String businessKey) {
        WorkFlowService workFlowService = (WorkFlowService) SpringContextHolder.getOneBean(WorkFlowService.class);
        return ResponseResult.success(workFlowService.findTaskListForProcess(processDefinitionKey, businessKey));
    }

    @ApiOperation(httpMethod = "POST", value = "根据流程与业务编码，查询任务情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processDefinitionKey", required = true, value = "流程定义code", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "businessKey", required = true, value = "业务编码", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "approveStatus", required = true, value = "审批状态", paramType = "query", dataType = "String") })
    @RequestMapping({ "service/findDetailForProcessById" })
    @ResponseBody
    public ResponseResult<List<WorkOrderVo>> findTaskListForProcess(
            @RequestParam(required = true) String processInstanceId) {
        WorkFlowService workFlowService = (WorkFlowService) SpringContextHolder.getOneBean(WorkFlowService.class);
        return ResponseResult.success(workFlowService.findTaskListForProcess(processInstanceId));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "processDefinitionKey", required = true, value = "流程定义code", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "businessKey", required = true, value = "业务编码", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "approveStatus", required = true, value = "审批状态", paramType = "query", dataType = "String") })
    @RequestMapping({ "service/findMyAppliedTask" })
    @ResponseBody
    public ResponseResult<PageView<WorkOrderVo>> findMyAppliedTask(@RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) throws Exception {
        WorkFlowService workFlowService = (WorkFlowService) SpringContextHolder.getOneBean(WorkFlowService.class);
        PageView<WorkOrderVo> pageView = new PageView<WorkOrderVo>(pageNo, pageSize);

        return ResponseResult.success(workFlowService.findMyAppliedTask(pageView, SpringSecurityUtils.getLoginUser()));
    }

    @RequestMapping({ "service/startProcess" })
    @ResponseBody
    public ResponseResult<String> startProcess(@RequestParam(required = true) String processDefinitionKey,
            @RequestParam(required = true) String businessKey,
            @RequestParam(required = true) Map<String, Object> processVariable) {
        WorkFlowService workFlowService = (WorkFlowService) SpringContextHolder.getOneBean(WorkFlowService.class);
        workFlowService.startProcess(processDefinitionKey, businessKey, processVariable);
        return ResponseResult.success();
    }

    @RequestMapping({ "service/stopProcess" })
    @ResponseBody
    public ResponseResult<String> stopProcess(@RequestParam(required = true) String processDefinitionKey,
            @RequestParam(required = true) String businessKey) {
        WorkFlowService workFlowService = (WorkFlowService) SpringContextHolder.getOneBean(WorkFlowService.class);
        workFlowService.stopProcess(processDefinitionKey, businessKey);
        return ResponseResult.success();
    }

    @RequestMapping({ "service/printNextTaskDefinition" })
    @ResponseBody
    public ResponseResult<String> printNextTaskDefinition(@RequestParam(required = true) String procInstId) {
        WorkFlowService workFlowService = (WorkFlowService) SpringContextHolder.getOneBean(WorkFlowService.class);
        workFlowService.test(procInstId);
        return ResponseResult.success();
    }
}
