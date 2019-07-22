package com.els.base.workflow.user;

import com.els.base.auth.entity.Role;
import com.els.base.auth.service.RoleService;
import com.els.base.core.exception.CommonException;
import com.els.base.core.service.user.UserService;
import com.els.base.workflow.common.utils.ActivitiUserUtil;
import com.els.base.workflow.user.CustomUserEntityManager;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.Picture;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomUserEntityManager extends UserEntityManager {
    private static final Log logger = LogFactory.getLog(CustomUserEntityManager.class);

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    public User createNewUser(String userId) {
        throw new CommonException("不支持该方法");
    }

    public UserEntity findUserById(String userId) {
        UserEntity userEntity = new UserEntity();
        User user = (User) this.userService.queryObjById(userId);
        return ActivitiUserUtil.toActivitiUser(user);
    }

    public void deleteUser(String userId) {
    }

    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
        return null;
    }

    public long findUserCountByQueryCriteria(UserQueryImpl query) {
        return 0L;
    }

    public List<Group> findGroupsByUser(String userId) {
        List<Role> list = this.roleService.queryUserOwnRoles(userId);

        List gs = null;
        return ActivitiUserUtil.toActivitiGroups(list);
    }

    public UserQuery createNewUserQuery() {
        return null;
    }

    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId, String key) {
        return null;
    }

    public List<String> findUserInfoKeysByUserIdAndType(String userId, String type) {
        return null;
    }

    public Boolean checkPassword(String userId, String password) {
        return null;
    }

    public List<User> findPotentialStarterUsers(String proceDefId) {
        return null;
    }

    public List<User> findUsersByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
        return null;
    }

    public long findUserCountByNativeQuery(Map<String, Object> parameterMap) {
        return 0L;
    }

    public boolean isNewUser(User user) {
        return false;
    }

    public Picture getUserPicture(String userId) {
        return null;
    }

    public void setUserPicture(String userId, Picture picture) {
    }
}
