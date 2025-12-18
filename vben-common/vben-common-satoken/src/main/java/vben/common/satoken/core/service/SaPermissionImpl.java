package vben.common.satoken.core.service;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import vben.common.core.domain.model.LoginUser;
import vben.common.core.enums.UserType;
import vben.common.core.exception.ServiceException;
import vben.common.core.service.PermissionService;
import vben.common.core.utils.ObjectUtils;
import vben.common.core.utils.SpringUtils;
import vben.common.core.utils.StrUtils;
import vben.common.satoken.utils.LoginHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * sa-token 权限管理实现类
 *
 * @author Lion Li
 */
public class SaPermissionImpl implements StpInterface {

    /**
     * 获取菜单权限列表
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        if (ObjectUtils.isNull(loginUser) || !loginUser.getLoginId().equals(loginId)) {
            PermissionService permissionService = getPermissionService();
            if (ObjectUtils.isNotNull(permissionService)) {
                List<String> list = StrUtils.splitList(loginId.toString(), ":");
                return new ArrayList<>(permissionService.getMenuPermission(Long.parseLong(list.get(1))));
            } else {
                throw new ServiceException("PermissionService 实现类不存在");
            }
        }
        UserType userType = UserType.getUserType(loginUser.getUserType());
        if (userType == UserType.APP_USER) {
            // 其他端 自行根据业务编写
        }
        if (CollUtil.isNotEmpty(loginUser.getMenuPermission())) {
            // SYS_USER 默认返回权限
            return new ArrayList<>(loginUser.getMenuPermission());
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 获取角色权限列表
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        if (ObjectUtils.isNull(loginUser) || !loginUser.getLoginId().equals(loginId)) {
            PermissionService permissionService = getPermissionService();
            if (ObjectUtils.isNotNull(permissionService)) {
                List<String> list = StrUtils.splitList(loginId.toString(), ":");
                return new ArrayList<>(permissionService.getRolePermission(Long.parseLong(list.get(1))));
            } else {
                throw new ServiceException("PermissionService 实现类不存在");
            }
        }
        UserType userType = UserType.getUserType(loginUser.getUserType());
        if (userType == UserType.APP_USER) {
            // 其他端 自行根据业务编写
        }
        if (CollUtil.isNotEmpty(loginUser.getRolePermission())) {
            // SYS_USER 默认返回权限
            return new ArrayList<>(loginUser.getRolePermission());
        } else {
            return new ArrayList<>();
        }
    }

    private PermissionService getPermissionService() {
        try {
            return SpringUtils.getBean(PermissionService.class);
        } catch (Exception e) {
            return null;
        }
    }

}
