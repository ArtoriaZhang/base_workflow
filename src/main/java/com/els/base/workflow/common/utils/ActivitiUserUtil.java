package com.els.base.workflow.common.utils;

import com.els.base.auth.entity.Role;
import com.els.base.workflow.common.utils.ActivitiUserUtil;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;

public class ActivitiUserUtil {
    public static UserEntity toActivitiUser(User bUser) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(bUser.getId().toString());
        userEntity.setFirstName(bUser.getFirstName());
        userEntity.setLastName(bUser.getLastName());
        userEntity.setPassword(bUser.getPassword());
        userEntity.setEmail(bUser.getEmail());
        userEntity.setRevision(1);
        return userEntity;
    }

    public static GroupEntity toActivitiGroup(Role role) {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setRevision(1);
        groupEntity.setType("assignment");
        groupEntity.setId(role.getId());
        groupEntity.setName(role.getRoleName());
        return groupEntity;
    }

    public static List<Group> toActivitiGroups(List<Role> roles) {
        List<Group> groupEntitys = new ArrayList<Group>();
        for (Role role : roles) {
            GroupEntity groupEntity = toActivitiGroup(role);
            groupEntitys.add(groupEntity);
        }
        return groupEntitys;
    }
}
