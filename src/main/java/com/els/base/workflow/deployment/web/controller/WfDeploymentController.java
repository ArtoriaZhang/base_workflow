package com.els.base.workflow.deployment.web.controller;

import com.els.base.auth.utils.SpringSecurityUtils;
import com.els.base.core.entity.PageView;
import com.els.base.core.entity.ResponseResult;
import com.els.base.core.exception.CommonException;
import com.els.base.core.utils.Assert;
import com.els.base.core.utils.Constant;
import com.els.base.core.utils.CriteriaUtils;
import com.els.base.core.utils.query.QueryParamWapper;
import com.els.base.workflow.deployment.entity.WfDeployment;
import com.els.base.workflow.deployment.entity.WfDeploymentExample;
import com.els.base.workflow.deployment.service.WfDeploymentService;
import com.els.base.workflow.deployment.web.controller.WfDeploymentController;
import com.els.base.workflow.wfSet.entity.WfSet;
import com.els.base.workflow.wfSet.entity.WfSetExample;
import com.els.base.workflow.wfSet.service.WfSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Api("审批流")
@Controller
@RequestMapping({ "wfDeployment" })
public class WfDeploymentController {
    @Resource
    protected WfDeploymentService wfDeploymentService;
    @Resource
    protected WfSetService wfSetService;

    @ApiOperation(httpMethod = "POST", value = "创建审批流")
    @RequestMapping({ "service/create" })
    @ResponseBody
    public ResponseResult<String> create(@ModelAttribute WfDeployment wfDeployment, MultipartHttpServletRequest request)
            throws IOException {
        Map<String, MultipartFile> fileMap = request.getFileMap();
        if (MapUtils.isEmpty(fileMap)) {
            throw new CommonException("上传文件为空", "file_isNull");
        }

        Set<String> fileKeySet = fileMap.keySet();
        if (fileKeySet.size() > 1) {
            throw new CommonException("不接受多个文件上传", "file_upload_not_accepted");
        }

        MultipartFile file = null;

        Iterator<String> keyIterator = fileKeySet.iterator();
        while (keyIterator.hasNext()) {
            file = (MultipartFile) fileMap.get(keyIterator.next());
        }

        wfDeployment.setCreateUser(SpringSecurityUtils.getLoginUserName());
        this.wfDeploymentService.addObj(wfDeployment, file);

        return ResponseResult.success();
    }

    @ApiOperation(httpMethod = "POST", value = "编辑审批流")
    @RequestMapping({ "service/edit" })
    @ResponseBody
    public ResponseResult<String> edit(@ModelAttribute WfDeployment wfDeployment, MultipartHttpServletRequest request)
            throws IOException {
        if (StringUtils.isBlank(wfDeployment.getId())) {
            throw new CommonException("id 为空，保存失败");
        }
        Map<String, MultipartFile> fileMap = request.getFileMap();

        wfDeployment.setBusinessCode(null);
        wfDeployment.setName(null);
        wfDeployment.setUpdateUser(SpringSecurityUtils.getLoginUserName());
        this.wfDeploymentService.modifyObj(wfDeployment, fileMap);
        return ResponseResult.success();
    }

    @ApiOperation(httpMethod = "POST", value = "编辑审批流")
    @RequestMapping({ "service/editSet" })
    @ResponseBody
    public ResponseResult<String> edit(@RequestBody WfDeployment wfDeployment) throws IOException {
        if (StringUtils.isBlank(wfDeployment.getId())) {
            throw new CommonException("id 为空，保存失败");
        }

        wfDeployment.setUpdateUser(SpringSecurityUtils.getLoginUserName());
        this.wfDeploymentService.modifyObj(wfDeployment);
        return ResponseResult.success();
    }

    @ApiOperation(httpMethod = "POST", value = "批量删除审批流")
    @RequestMapping({ "service/deleteById" })
    @ResponseBody
    public ResponseResult<String> deleteById(@RequestBody List<String> ids) {
        Assert.isNotNull(ids, "删除失败,id不能为空");
        this.wfDeploymentService.deleteObjByIds(ids);
        return ResponseResult.success();
    }

    @ApiOperation(httpMethod = "POST", value = "启用或禁用审批流，启用传1，禁用传0")
    @RequestMapping({ "service/setIsEnable" })
    @ResponseBody
    public ResponseResult<String> setIsEnable(@RequestBody WfDeployment wf) throws IOException {
        if (null == wf) {
            throw new CommonException();
        }

        if (StringUtils.isEmpty(wf.getId())) {
            throw new CommonException();
        }
        if (null == wf.getIsEnable()) {
            throw new CommonException();
        }

        Integer enable = ((WfDeployment) this.wfDeploymentService.queryObjById(wf.getId())).getIsEnable();
        if (!Constant.NO_INT.equals(wf.getIsEnable()) && !Constant.YES_INT.equals(wf.getIsEnable()))
            throw new CommonException("提交的状态有误!");
        if (wf.getIsEnable().equals(enable)) {
            throw new CommonException("状态未更改,请输入修改后的状态!");
        }

        this.wfDeploymentService.setIsEnable(wf);
        return ResponseResult.success();
    }

    @ApiOperation(httpMethod = "POST", value = "查询审批流")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", required = false, value = "所在页", paramType = "query", dataType = "String", defaultValue = "0"),
            @ApiImplicitParam(name = "pageSize", required = false, value = "每页数量", paramType = "query", dataType = "String", defaultValue = "10"),
            @ApiImplicitParam(name = "wapper", required = false, value = "查询条件,属性名请参考 WfDeployment", paramType = "body", dataType = "QueryParamWapper") })
    @RequestMapping({ "service/findByPage" })
    @ResponseBody
    public ResponseResult<PageView<WfDeployment>> findByPage(@RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize, @RequestBody(required = false) QueryParamWapper wapper) {
        WfDeploymentExample example = new WfDeploymentExample();
        example.setPageView(new PageView<>(pageNo, pageSize));
        example.setOrderByClause("CREATE_TIME DESC");

        WfDeploymentExample.Criteria criteria = example.createCriteria();
        if (wapper != null) {
            CriteriaUtils.addCriterion(criteria, wapper);
        }

        PageView<WfDeployment> pageData = this.wfDeploymentService.queryObjByPage(example);
        return ResponseResult.success(pageData);
    }

    @ApiOperation(httpMethod = "POST", value = "查看设置")
    @RequestMapping({ "service/findById" })
    @ResponseBody
    public ResponseResult<WfDeployment> findById(@RequestParam(required = true) String id) {
        Assert.isNotBlank(id, "查看详情,id不能为空");
        WfDeployment wfDeployment = (WfDeployment) this.wfDeploymentService.queryObjById(id);
        if (wfDeployment == null) {
            throw new CommonException("该单据为空");
        }
        WfSetExample example = new WfSetExample();
        example.createCriteria().andDeploymentIdEqualTo(id);
        List<WfSet> byExample = this.wfSetService.queryAllObjByExample(example);
        WfDeployment deployment = new WfDeployment();
        BeanUtils.copyProperties(wfDeployment, deployment);
        deployment.setWfSetList(byExample);
        return ResponseResult.success(deployment);
    }

    @ApiOperation(httpMethod = "POST", value = "提交流程节点配置")
    @RequestMapping({ "service/submitProcessSetting" })
    @ResponseBody
    public ResponseResult<String> submitProcessSetting(@RequestBody WfDeployment wfDeployment) throws IOException {
        Assert.isNotNull(wfDeployment, "提交内容不能为空");
        Assert.isNotEmpty(wfDeployment.getWfSetList(), "流程节点不能为空");
        wfDeployment.setCreateUser(SpringSecurityUtils.getLoginUserName());
        this.wfDeploymentService.changeProcess(wfDeployment);
        return ResponseResult.success();
    }
}
