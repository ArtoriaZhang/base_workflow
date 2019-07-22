package com.els.base.workflow.common.web.controller;

import com.els.base.auth.entity.Role;
import com.els.base.auth.entity.RoleExample;
import com.els.base.auth.service.RoleService;
import com.els.base.auth.service.UserRoleService;
import com.els.base.core.entity.PageView;
import com.els.base.core.entity.user.User;
import com.els.base.core.entity.user.UserExample;
import com.els.base.core.service.user.UserService;
import com.els.base.core.utils.Assert;
import com.els.base.workflow.common.entity.RoleResult;
import com.els.base.workflow.common.entity.UserReponse;
import com.els.base.workflow.common.web.controller.ActivitiUserController;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({ "actuser" })
public class ActivitiUserController {
    @Resource
    private RoleService roleService;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private UserService userService;

    @ApiOperation(httpMethod = "POST", value = "获取所有角色")
    @RequestMapping({ "service/getAllRole" })
    @ResponseBody
    public List<RoleResult> getAllRole() throws IOException {
        RoleExample example = new RoleExample();
        example.createCriteria();
        List<Role> roleList = this.roleService.queryAllObjByExample(example);

        if (CollectionUtils.isEmpty(roleList)) {
            return Collections.emptyList();
        }

        return roleList.stream().map(role -> new RoleResult(role)).collect(Collectors.toList());
    }

    @ApiOperation(httpMethod = "POST", value = "根据用户id获取用户信息")
    @RequestMapping({ "service/getUserListByRoleId" })
    @ResponseBody
    public UserReponse getUserListByRoleId(@RequestParam(required = true) String roleId,
            @RequestParam(required = true) int pageNo, int pageSize, HttpServletResponse httpServletResponse)
            throws IOException {
        List<String> userIds = this.userRoleService.queryUserIdsForRoleId(roleId);

        if (CollectionUtils.isEmpty(userIds)) {
            httpServletResponse.setStatus(503);
            Assert.isNotEmpty(userIds, "该角色下的用户为空");
        }

        UserExample example = new UserExample();
        example.createCriteria().andIdIn(userIds);
        example.setPageView(new PageView<>(pageNo, pageSize));

        PageView<User> pageView = this.userService.queryObjByPage(example);

        List<User> queryResult = pageView.getQueryResult();

        UserReponse userReponse = new UserReponse();
        userReponse.setTotals(pageView.getiTotalRecords());
        userReponse.setUsers(queryResult);

        return userReponse;
    }
}
