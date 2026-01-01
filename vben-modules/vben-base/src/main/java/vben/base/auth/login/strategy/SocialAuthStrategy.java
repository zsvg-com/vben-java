package vben.base.auth.login.strategy;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.stp.parameter.SaLoginParameter;
import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.stereotype.Service;
import vben.base.sys.user.SysUser;
import vben.base.sys.user.SysUserDao;
import vben.common.core.constant.SystemConstants;
import vben.common.core.domain.model.LoginUser;
import vben.common.core.domain.model.SocialLoginBody;
import vben.common.core.exception.ServiceException;
import vben.common.core.exception.user.UserException;
import vben.common.core.utils.ObjectUtils;
import vben.common.core.utils.StreamUtils;
import vben.common.core.utils.ValidatorUtils;
import vben.common.json.utils.JsonUtils;
import vben.common.satoken.utils.LoginHelper;
import vben.common.social.config.properties.SocialProperties;
import vben.common.social.utils.SocialUtils;
import vben.base.sys.social.ISysSocialService;
import vben.base.sys.social.SysSocialVo;
import vben.base.auth.login.vo.LoginVo;
import vben.base.auth.login.vo.SysClientVo;
import vben.base.auth.user.AuthUserVo;
import vben.base.auth.login.AuthLoginService;

import java.util.List;
import java.util.Optional;

/**
 * 第三方授权策略
 *
 * @author thiszhc is 三三
 */
@Slf4j
@Service("social" + IAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class SocialAuthStrategy implements IAuthStrategy {

    private final SocialProperties socialProperties;
    private final ISysSocialService sysSocialService;
    private final SysUserDao userDao;
    private final AuthLoginService loginService;

    /**
     * 登录-第三方授权登录
     *
     * @param body     登录信息
     * @param client   客户端信息
     */
    @Override
    public LoginVo login(String body, SysClientVo client) {
        SocialLoginBody loginBody = JsonUtils.parseObject(body, SocialLoginBody.class);
        ValidatorUtils.validate(loginBody);
        AuthResponse<AuthUser> response = SocialUtils.loginAuth(
                loginBody.getSource(), loginBody.getSocialCode(),
                loginBody.getSocialState(), socialProperties);
        if (!response.ok()) {
            throw new ServiceException(response.getMsg());
        }
        AuthUser authUserData = response.getData();

        List<SysSocialVo> list = sysSocialService.selectByAuthId(authUserData.getSource() + authUserData.getUuid());
        if (CollUtil.isEmpty(list)) {
            throw new ServiceException("你还没有绑定第三方账号，绑定后才可以登录！");
        }
        SysSocialVo social;
        if (false) {
            Optional<SysSocialVo> opt = StreamUtils.findAny(list, x -> x.getTenantId().equals(loginBody.getTenantId()));
            if (opt.isEmpty()) {
                throw new ServiceException("对不起，你没有权限登录当前租户！");
            }
            social = opt.get();
        } else {
            social = list.get(0);
        }
//        LoginUser loginUser = TenantHelper.dynamic(social.getTenantId(), () -> {
//            SysUserVo user = loadUser(social.getUserId());
//            // 此处可根据登录用户的数据不同 自行创建 loginUser 属性不够用继承扩展就行了
//            return loginService.buildLoginUser(user);
//        });

        AuthUserVo user = loadUser(social.getUserId());
        // 此处可根据登录用户的数据不同 自行创建 loginUser 属性不够用继承扩展就行了
        LoginUser loginUser = loginService.buildLoginUser(user);

        loginUser.setClientKey(client.getClientKey());
        loginUser.setDeviceType(client.getDeviceType());
        SaLoginParameter model = new SaLoginParameter();
        model.setDeviceType(client.getDeviceType());
        // 自定义分配 不同用户体系 不同 token 授权时间 不设置默认走全局 yml 配置
        // 例如: 后台用户30分钟过期 app用户1天过期
        model.setTimeout(client.getTimeout());
        model.setActiveTimeout(client.getActiveTimeout());
        model.setExtra(LoginHelper.CLIENT_KEY, client.getClientKey());
        // 生成token
        LoginHelper.login(loginUser, model);

        LoginVo loginVo = new LoginVo();
        loginVo.setAccessToken(StpUtil.getTokenValue());
        loginVo.setExpireIn(StpUtil.getTokenTimeout());
        loginVo.setClientId(client.getClientId());
        return loginVo;
    }

    private AuthUserVo loadUser(String userId) {
        SysUser orgUser = userDao.findById(userId);
        AuthUserVo user=new AuthUserVo();
        user.setUserName(orgUser.getUsername());
        user.setUserId(orgUser.getId());
        user.setPassword(orgUser.getPassword());
        user.setNickName(orgUser.getName());
        user.setDeptId(orgUser.getDepid());
        user.setDeptName(orgUser.getDepna());
        user.setUserType("sys_user");

        if (ObjectUtils.isNull(user)) {
            log.info("登录用户：{} 不存在.", "");
            throw new UserException("user.not.exists", "");
        } else if (SystemConstants.DISABLE.equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", "");
            throw new UserException("user.blocked", "");
        }
        return user;
    }

}
