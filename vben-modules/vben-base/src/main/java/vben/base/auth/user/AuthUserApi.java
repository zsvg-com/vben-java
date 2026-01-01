package vben.base.auth.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vben.base.sys.user.SysUser;
import vben.base.sys.user.SysUserDao;
import vben.common.core.domain.R;
import vben.common.core.domain.model.LoginUser;
import vben.common.satoken.utils.LoginHelper;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthUserApi {

    private final SysUserDao userDao;

    private final SysAuthService authService;

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/system/user/getInfo")
    public R<UserInfoVo> getInfo() {
        UserInfoVo userInfoVo = new UserInfoVo();
        LoginUser loginUser = LoginHelper.getLoginUser();
        SysUser user = userDao.findById(loginUser.getUserId()+"");
        AuthUserVo userVo = new AuthUserVo();
        userVo.setUserId(loginUser.getUserId());
        userVo.setUserName(loginUser.getUsername());
        userVo.setNickName(loginUser.getNickname());
        userVo.setDeptName(loginUser.getDeptName());
        userVo.setDeptId(loginUser.getDeptId());
        userVo.setAvatar(user.getAvatar());
        userVo.setPhonenumber(user.getMonum());
        userVo.setGender(user.getGender());
        userInfoVo.setUser(userVo);
        userInfoVo.setPermissions(loginUser.getMenuPermission());
        userInfoVo.setRoles(loginUser.getRolePermission());
        return R.ok(userInfoVo);
    }

    @GetMapping("/system/menu/getRouters")
    public R<List<RouterVo>> getRouters() {
        return R.ok(authService.getRouters(LoginHelper.getUserId()));
    }
}
