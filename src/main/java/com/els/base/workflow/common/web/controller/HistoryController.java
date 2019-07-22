package com.els.base.workflow.common.web.controller;

import com.els.base.core.entity.PageView;
import com.els.base.core.entity.ResponseResult;
import com.els.base.workflow.common.entity.HistoricProcessInstanceResult;
import com.els.base.workflow.common.web.controller.HistoryController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api("历史模块")
@RestController
@RequestMapping({ "histories" })
public class HistoryController {
    @Resource
    private HistoryService historyService;

    @ApiOperation(httpMethod = "POST", value = "查询实例流程列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", required = false, value = "所在页", paramType = "query", dataType = "String", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", required = false, value = "每页数量", paramType = "query", dataType = "String", defaultValue = "10") })
    @RequestMapping({ "service/findByPage" })
    @ResponseBody
    public ResponseResult<PageView<HistoricProcessInstanceResult>> findByPage(
            @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize) {
        HistoricProcessInstanceQuery historicProcessInstanceQuery = this.historyService
                .createHistoricProcessInstanceQuery();
        List<HistoricProcessInstance> hpiList = historicProcessInstanceQuery.orderByProcessInstanceStartTime().desc()
                .listPage(pageSize * (pageNo - 1), pageSize);
        List<HistoricProcessInstanceResult> result = hpiList.stream().map(hpi -> new HistoricProcessInstanceResult(hpi))
                .collect(Collectors.toList());

        PageView<HistoricProcessInstanceResult> pageView = new PageView<HistoricProcessInstanceResult>(pageNo,
                pageSize);
        pageView.setQueryResult(result);

        long totalSize = historicProcessInstanceQuery.count();
        pageView.setRowCount((int) totalSize);

        return ResponseResult.success(pageView);
    }
}
