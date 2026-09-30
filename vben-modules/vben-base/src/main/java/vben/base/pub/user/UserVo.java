package vben.base.pub.user;

import lombok.Data;

import java.util.Set;

/**
 * 登录用户信息
 *
 * @author Michelle.Chung
 */
@Data
public class UserVo {

    /**
     * 用户基本信息
     */
    private User user;

    /**
     * 菜单权限
     */
    private Set<String> perms;

    /**
     * 角色权限
     */
    private Set<String> roles;

}
