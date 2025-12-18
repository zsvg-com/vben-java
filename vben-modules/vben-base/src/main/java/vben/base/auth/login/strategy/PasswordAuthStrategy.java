package vben.base.auth.login.strategy;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.stp.parameter.SaLoginParameter;
import cn.hutool.crypto.digest.BCrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vben.base.sys.org.user.SysOrgUser;
import vben.base.sys.org.user.SysOrgUserDao;
import vben.common.core.constant.Constants;
import vben.common.core.constant.GlobalConstants;
import vben.common.core.constant.SystemConstants;
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
import vben.base.auth.login.vo.LoginVo;
import vben.base.auth.login.vo.SysClientVo;
import vben.base.auth.user.AuthUserVo;
import vben.base.auth.login.AuthLoginService;

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
    private final SysOrgUserDao userDao;

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
        AuthUserVo user = loadUserByUsername(username);
        loginService.checkLogin(LoginType.PASSWORD, tenantId, username, () -> !BCrypt.checkpw(password, user.getPassword()));
        LoginUser loginUser = loginService.buildLoginUser(user);

        loginUser.setClientKey(client.getClientKey());
        loginUser.setDeviceType(client.getDeviceType());
        loginUser.setUserType("sys_user");
        loginUser.setUserId(user.getUserId());
        loginUser.setDeptName(user.getDeptName());
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

    private AuthUserVo loadUserByUsername(String username) {
//        SysUserVo user = userService.findById().selectVoOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, username));
        SysOrgUser orgUser = userDao.findByUsnam(username);
        AuthUserVo user=new AuthUserVo();
        user.setUserId(orgUser.getId());
        user.setUserName(orgUser.getUsnam());
        user.setPassword(orgUser.getPacod());
        user.setNickName(orgUser.getNinam());
        user.setDeptId(orgUser.getDepid());
        user.setDeptName(orgUser.getDepna());
        user.setStatus(orgUser.getAvtag()?"0":"1");
        user.setAvatar(orgUser.getAvatar());
        //user.set(orgUser.getUsnam());

        if (ObjectUtils.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new UserException("user.not.exists", username);
        } else if (SystemConstants.DISABLE.equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new UserException("user.blocked", username);
        }
        return user;
    }

}
