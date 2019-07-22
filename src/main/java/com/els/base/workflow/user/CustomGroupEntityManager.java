package com.els.base.workflow.user;

import com.els.base.auth.entity.Role;
import com.els.base.auth.entity.RoleExample;
import com.els.base.auth.service.RoleService;
import com.els.base.auth.utils.SpringSecurityUtils;
import com.els.base.utils.SpringContextHolder;
import com.els.base.workflow.user.CustomGroupEntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class CustomGroupEntityManager extends GroupEntityManager {
    @Resource
    private RoleService roleService;

    public Group createNewGroup(String groupId) {
        return super.createNewGroup(groupId);
    }

    public void insertGroup(Group group) {
        super.insertGroup(group);
    }

    public void updateGroup(Group updatedGroup) {
        super.updateGroup(updatedGroup);
    }

    public void deleteGroup(String groupId) {
        super.deleteGroup(groupId);
    }

    public GroupQuery createNewGroupQuery() {
        return super.createNewGroupQuery();
    }

    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        RoleService roleService = (RoleService) SpringContextHolder.getOneBean(RoleService.class);
        if (StringUtils.isNotBlank(query.getId())) {
            criteria.andIdEqualTo(query.getId());
        }

        if (StringUtils.isNotBlank(query.getName())) {
            criteria.andRoleNameEqualTo(query.getName());
        }

        if (StringUtils.isNotBlank(query.getNameLike())) {
            criteria.andRoleNameLike(query.getNameLike());
        }

        if (StringUtils.isNotBlank(query.getUserId())) {
            List<Role> roles = roleService.queryUserOwnRoles(query.getUserId());

            List<String> roleId = new ArrayList<String>();
            for (int i = 0; CollectionUtils.isNotEmpty(roles) && i < roles.size(); i++) {
                roleId.add(((Role) roles.get(i)).getId());
            }

            if (CollectionUtils.isNotEmpty(roleId))
                ;

            criteria.andIdIn(roleId);
        }

        List<Role> roles = roleService.queryAllObjByExample(roleExample);
        List<Group> groups = new ArrayList<Group>();
        GroupEntity groupEntity = null;
        for (Role role : roles) {
            groupEntity = new GroupEntity();
            groupEntity.setRevision(1);
            groupEntity.setType("assignment");
            groupEntity.setId(role.getId());
            groupEntity.setName(role.getRoleCode());
            groups.add(groupEntity);
        }
        return groups;
    }

    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
        return super.findGroupCountByQueryCriteria(query);
    }

    public List<Group> findGroupsByUser(String userId) {
        List<Role> roles = SpringSecurityUtils.getLoginUserRoleList();
        List<Group> groups = new ArrayList<Group>();
        GroupEntity groupEntity = null;
        for (Role role : roles) {
            groupEntity = new GroupEntity();
            groupEntity.setRevision(1);
            groupEntity.setType("assignment");
            groupEntity.setId(role.getId());
            groupEntity.setName(role.getRoleCode());
            groups.add(groupEntity);
        }
        return groups;
    }

    public List<Group> findGroupsByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
        return super.findGroupsByNativeQuery(parameterMap, firstResult, maxResults);
    }

    public long findGroupCountByNativeQuery(Map<String, Object> parameterMap) {
        return super.findGroupCountByNativeQuery(parameterMap);
    }

    public boolean isNewGroup(Group group) {
        return super.isNewGroup(group);
    }
}
