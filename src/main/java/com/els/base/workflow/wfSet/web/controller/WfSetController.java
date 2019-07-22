package com.els.base.workflow.wfSet.web.controller;

import com.els.base.core.entity.PageView;
import com.els.base.core.entity.ResponseResult;
import com.els.base.core.utils.Assert;
import com.els.base.core.utils.CriteriaUtils;
import com.els.base.core.utils.query.QueryParamWapper;
import com.els.base.workflow.wfSet.entity.WfSet;
import com.els.base.workflow.wfSet.entity.WfSetExample;
import com.els.base.workflow.wfSet.service.WfSetService;
import com.els.base.workflow.wfSet.web.controller.WfSetController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Api("审批流设置")
@Controller
@RequestMapping({ "wfSet" })
public class WfSetController {
    @Resource
    protected WfSetService wfSetService;

    @ApiOperation(httpMethod = "POST", value = "创建审批流设置")
    @RequestMapping({ "service/create" })
    @ResponseBody
    public ResponseResult<String> create(@RequestBody WfSet wfSet) {
        this.wfSetService.addObj(wfSet);
        return ResponseResult.success();
    }

    @ApiOperation(httpMethod = "POST", value = "编辑审批流设置")
    @RequestMapping({ "service/edit" })
    @ResponseBody
    public ResponseResult<String> edit(@RequestBody WfSet wfSet) {
        Assert.isNotBlank(wfSet.getId(), "id 为空，保存失败");
        this.wfSetService.modifyObj(wfSet);
        return ResponseResult.success();
    }

    @ApiOperation(httpMethod = "POST", value = "删除审批流设置")
    @RequestMapping({ "service/deleteById" })
    @ResponseBody
    public ResponseResult<String> deleteById(@RequestParam(required = true) String id) {
        Assert.isNotBlank(id, "删除失败,id不能为空");
        this.wfSetService.deleteObjById(id);
        return ResponseResult.success();
    }

    @ApiOperation(httpMethod = "POST", value = "查询审批流设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", required = false, value = "所在页", paramType = "query", dataType = "String", defaultValue = "0"),
            @ApiImplicitParam(name = "pageSize", required = false, value = "每页数量", paramType = "query", dataType = "String", defaultValue = "10"),
            @ApiImplicitParam(name = "wapper", required = false, value = "查询条件,属性名请参考 WfSet", paramType = "body", dataType = "QueryParamWapper") })
    @RequestMapping({ "service/findByPage" })
    @ResponseBody
    public ResponseResult<PageView<WfSet>> findByPage(@RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize, @RequestBody(required = false) QueryParamWapper wapper) {
        WfSetExample example = new WfSetExample();
        example.setPageView(new PageView<>(pageNo, pageSize));

        WfSetExample.Criteria criteria = example.createCriteria();

        if (wapper != null) {
            CriteriaUtils.addExample(example, wapper);
        }

        PageView<WfSet> pageData = this.wfSetService.queryObjByPage(example);
        return ResponseResult.success(pageData);
    }
}
