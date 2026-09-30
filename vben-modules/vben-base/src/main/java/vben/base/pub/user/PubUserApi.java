package vben.base.pub.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vben.base.sys.user.SysUser;
import vben.base.sys.user.SysUserDao;
import vben.common.core.domain.R;
import vben.common.core.domain.model.LoginUser;
import vben.common.satoken.utils.LoginHelper;

@RestController
@RequiredArgsConstructor
public class PubUserApi {

    private final SysUserDao userDao;

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/pub/user")
    public R<UserVo> info() {
        UserVo userVo = new UserVo();
        LoginUser loginUser = LoginHelper.getLoginUser();
        SysUser sysUser = userDao.findById(loginUser.getUserId()+"");
        User user = new User();
        user.setUseid(loginUser.getUserId());
        user.setUsena(loginUser.getUsername());
        user.setNicna(loginUser.getNickname());
        user.setOrgna(loginUser.getOrgna());
        user.setOrgid(loginUser.getOrgid());
        user.setAvatar(sysUser.getAvatar());
        user.setMonum(sysUser.getMonum());
        user.setGender(sysUser.getGender());
        userVo.setUser(user);
        userVo.setPerms(loginUser.getMenuPermission());
        userVo.setRoles(loginUser.getRolePermission());
        return R.ok(userVo);
    }

}
