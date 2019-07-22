package com.els.base.workflow.common.web.controller;

import com.els.base.core.entity.PageView;
import com.els.base.core.entity.ResponseResult;
import com.els.base.core.exception.CommonException;
import com.els.base.core.utils.Assert;
import com.els.base.workflow.common.entity.ModelResult;
import com.els.base.workflow.common.web.controller.ModelController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api("模型列表")
@RestController
@RequestMapping({ "models" })
public class ModelController {
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private ObjectMapper objectMapper;

    @ApiOperation(httpMethod = "POST", value = "查询模型列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", required = false, value = "所在页", paramType = "query", dataType = "String", defaultValue = "0"),
            @ApiImplicitParam(name = "pageSize", required = false, value = "每页数量", paramType = "query", dataType = "String", defaultValue = "10") })
    @RequestMapping({ "service/findByPage" })
    @ResponseBody
    public ResponseResult<PageView<ModelResult>> findByPage(@RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize, @RequestBody(required = false) ModelEntity modelEntity) {
        ModelQuery modelQuery = this.repositoryService.createModelQuery();

        if (modelEntity != null && StringUtils.isNotBlank(modelEntity.getName())) {
            modelQuery.modelNameLike("%" + modelEntity.getName() + "%");
        }
        List<Model> list = (modelQuery.orderByCreateTime().desc()).listPage(pageSize * (pageNo - 1), pageSize);
        long count = modelQuery.count();

        PageView<ModelResult> pageView = new PageView<ModelResult>(pageNo, pageSize);
        pageView.setRowCount((int) count);

        if (CollectionUtils.isNotEmpty(list)) {

            List<ModelResult> modelEntityList = list.stream().map(model -> {
                ModelResult modelResult = new ModelResult();
                BeanUtils.copyProperties(model, modelResult);
                return modelResult;
            }).collect(Collectors.toList());
            pageView.setQueryResult(modelEntityList);
        }

        return ResponseResult.success(pageView);
    }

    @ApiOperation(httpMethod = "POST", value = "创建模型")
    @RequestMapping({ "service/create" })
    @ResponseBody
    public ResponseResult<String> create(@RequestParam(required = false, defaultValue = "cgdd") String key)
            throws UnsupportedEncodingException {
        Model model = this.repositoryService.newModel();

        String name = "未命名";
        String description = "";
        int revision = 1;

        ObjectNode modelNode = this.objectMapper.createObjectNode();
        modelNode.put("name", name);
        modelNode.put("description", description);
        modelNode.put("revision", revision);

        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());

        this.repositoryService.saveModel(model);
        String id = model.getId();

        ObjectNode editorNode = this.objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = this.objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);

        this.repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));

        return ResponseResult.success("/resources/modeler.html?modelId=" + id);
    }

    @ApiOperation(httpMethod = "POST", value = "删除模型")
    @RequestMapping({ "service/deleteById" })
    @ResponseBody
    public ResponseResult<String> deleteById(@RequestParam(required = true) String id)
            throws UnsupportedEncodingException {
        Assert.isNotBlank(id, "删除失败,id不能为空");
        this.repositoryService.deleteModel(id);
        return ResponseResult.success();
    }

    @ApiOperation(httpMethod = "POST", value = "发布模型")
    @RequestMapping({ "service/deploymentById" })
    @ResponseBody
    public ResponseResult<String> deploymentById(@RequestParam(required = true) String id) throws IOException {
        Assert.isNotBlank(id, "发布失败,id不能为空");

        Model modelData = this.repositoryService.getModel(id);

        byte[] bytes = this.repositoryService.getModelEditorSource(modelData.getId());

        if (bytes == null) {
            throw new CommonException("模型数据为空，请先设计流程并成功保存，再进行发布。");
        }

        JsonNode modelNode = (new ObjectMapper()).readTree(bytes);

        BpmnModel model = (new BpmnJsonConverter()).convertToBpmnModel(modelNode);
        if (model.getProcesses().size() == 0) {
            throw new CommonException("数据模型不符要求，请至少设计一条主线流程。");
        }

        Process process = model.getMainProcess();
        if (process != null) {
            process.setId(modelData.getKey());
            process.setName(modelData.getName());
        }

        byte[] bpmnBytes = (new BpmnXMLConverter()).convertToXML(model);

        String processName = modelData.getName() + ".bpmn20.xml";

        Deployment deployment = this.repositoryService.createDeployment().name(modelData.getName())
                .addString(processName, new String(bpmnBytes, "UTF-8")).deploy();

        modelData.setDeploymentId(deployment.getId());

        this.repositoryService.saveModel(modelData);

        return ResponseResult.success();
    }

    @ApiOperation(httpMethod = "POST", value = "获取模型id")
    @RequestMapping({ "service/getModelIdByDeploymentId" })
    @ResponseBody
    public ResponseResult<String> getModelIdByDeploymentId(@RequestParam(required = true) String deploymentId)
            throws UnsupportedEncodingException {
        Model model = (Model) this.repositoryService.createModelQuery().deploymentId(deploymentId).list().get(0);
        Assert.isNotNull(model, "模型不存在");

        return ResponseResult.success(model.getId());
    }
}
