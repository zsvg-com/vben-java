package vben.base.auth.login.strategy;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.stp.parameter.SaLoginParameter;
import cn.hutool.crypto.digest.BCrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vben.base.auth.login.AuthLoginService;
import vben.base.auth.login.vo.LoginVo;
import vben.base.auth.login.vo.SysClientVo;
import vben.base.pub.user.User;
import vben.base.sys.user.SysUser;
import vben.base.sys.user.SysUserDao;
import vben.common.core.constant.Constants;
import vben.common.core.constant.GlobalConstants;
import vben.common.core.domain.model.LoginUser;
import vben.common.core.domain.model.PasswordLoginBody;
import vben.common.core.enums.LoginType;
import vben.common.core.exception.user.CaptchaException;
import vben.common.core.exception.user.CaptchaExpireException;
import vben.common.core.exception.user.UserException;
import vben.common.core.utils.MessageUtils;
import vben.common.core.utils.ObjectUtils;
import vben.common.core.utils.StrUtils;
import vben.common.core.utils.ValidatorUtils;
import vben.common.json.utils.JsonUtils;
import vben.common.redis.utils.RedisUtils;
import vben.common.satoken.utils.LoginHelper;
import vben.common.web.config.properties.CaptchaProperties;

/**
 * 密码认证策略
 *
 * @author Michelle.Chung
 */
@Slf4j
@Service("password" + IAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class PasswordAuthStrategy implements IAuthStrategy {

    private final CaptchaProperties captchaProperties;
    private final AuthLoginService loginService;
    private final SysUserDao userDao;

//    @Override
//    public LoginVo login(String body, SysClientVo client) {
//        PasswordLoginBody loginBody = JsonUtils.parseObject(body, PasswordLoginBody.class);
//        ValidatorUtils.validate(loginBody);
//        String tenantId = loginBody.getTenantId();
//        String username = loginBody.getUsername();
//        String password = loginBody.getPassword();
//        String code = loginBody.getCode();
//        String uuid = loginBody.getUuid();
//
//        boolean captchaEnabled = captchaProperties.getEnable();
//        // 验证码开关
//        if (captchaEnabled) {
//        }
//        SysUserVo user = loadUserByUsername(username);
//        LoginUser loginUser = loginService.buildLoginUser(user);
//        loginUser.setClientKey(client.getClientKey());
//        loginUser.setDeviceType(client.getDeviceType());
//        loginUser.setUserType("sys_user");
//        loginUser.setUserId(user.getUserId());
//        SaLoginParameter model = new SaLoginParameter();
//        model.setDeviceType(client.getDeviceType());
//        // 自定义分配 不同用户体系 不同 token 授权时间 不设置默认走全局 yml 配置
//        // 例如: 后台用户30分钟过期 app用户1天过期
//        model.setTimeout(client.getTimeout());
//        model.setActiveTimeout(client.getActiveTimeout());
//        model.setExtra(LoginHelper.CLIENT_KEY, client.getClientId());
//        // 生成token
//        LoginHelper.login(loginUser, model);
//
//        LoginVo loginVo = new LoginVo();
//        loginVo.setAccessToken(StpUtil.getTokenValue());
//        loginVo.setExpireIn(StpUtil.getTokenTimeout());
//        loginVo.setClientId(client.getClientId());
//        return loginVo;
//    }

    @Override
    public LoginVo login(String body, SysClientVo client) {
        PasswordLoginBody loginBody = JsonUtils.parseObject(body, PasswordLoginBody.class);
        ValidatorUtils.validate(loginBody);
        String tenantId = loginBody.getTenantId();
        String username = loginBody.getUsername();
        String password = loginBody.getPassword();
        String code = loginBody.getCode();
        String uuid = loginBody.getUuid();

        boolean captchaEnabled = captchaProperties.getEnable();
        // 验证码开关
        if (captchaEnabled) {
            validateCaptcha(tenantId, username, code, uuid);
        }
        User user = loadUserByUsername(username);
        loginService.checkLogin(LoginType.PASSWORD, tenantId, username, () -> !BCrypt.checkpw(password, user.getPwd()));
        LoginUser loginUser = loginService.buildLoginUser(user);

        loginUser.setClientKey(client.getClientKey());
        loginUser.setDeviceType(client.getDeviceType());
        loginUser.setUserType("sys_user");
//        loginUser.setUserId(user.getUseid());
//        loginUser.setOrgna(user.getOrgna());
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

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     */
    private void validateCaptcha(String tenantId, String username, String code, String uuid) {
        String verifyKey = GlobalConstants.CAPTCHA_CODE_KEY + StrUtils.blankToDefault(uuid, "");
        String captcha = RedisUtils.getCacheObject(verifyKey);
        RedisUtils.deleteObject(verifyKey);
        if (captcha == null) {
            loginService.recordLogininfor(tenantId, username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new CaptchaExpireException();
        }
        if (!StrUtils.equalsIgnoreCase(code, captcha)) {
            loginService.recordLogininfor(tenantId, username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error"));
            throw new CaptchaException();
        }
    }

    private User loadUserByUsername(String username) {
//        SysUserVo user = userService.findById().selectVoOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, username));
        SysUser orgUser = userDao.findByUsername(username);
        User user=new User();
        user.setUseid(orgUser.getId());
        user.setUsena(orgUser.getUsername());
        user.setPwd(orgUser.getPassword());
        user.setNicna(orgUser.getName());
        user.setOrgid(orgUser.getOrgid());
        user.setOrgna(orgUser.getOrgna());
        user.setAvtag(orgUser.getAvtag());
        user.setAvatar(orgUser.getAvatar());

        if (ObjectUtils.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new UserException("user.not.exists", username);
        } else if (!user.getAvtag()) {
            log.info("登录用户：{} 已被停用.", username);
            throw new UserException("user.blocked", username);
        }
        return user;
    }

}
