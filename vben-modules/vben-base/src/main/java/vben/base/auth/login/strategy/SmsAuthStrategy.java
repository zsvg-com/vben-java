package vben.base.auth.login.strategy;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.stp.parameter.SaLoginParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vben.base.auth.login.AuthLoginService;
import vben.base.auth.login.vo.LoginVo;
import vben.base.auth.login.vo.SysClientVo;
import vben.base.auth.user.AuthUserVo;
import vben.base.sys.user.SysUser;
import vben.base.sys.user.SysUserDao;
import vben.common.core.constant.Constants;
import vben.common.core.constant.SystemConstants;
import vben.common.core.domain.model.LoginUser;
import vben.common.core.domain.model.SmsLoginBody;
import vben.common.core.enums.LoginType;
import vben.common.core.exception.user.CaptchaExpireException;
import vben.common.core.exception.user.UserException;
import vben.common.core.utils.MessageUtils;
import vben.common.core.utils.ObjectUtils;
import vben.common.core.utils.StrUtils;
import vben.common.core.utils.ValidatorUtils;
import vben.common.json.utils.JsonUtils;
import vben.common.satoken.utils.LoginHelper;

/**
 * 短信认证策略
 *
 * @author Michelle.Chung
 */
@Slf4j
@Service("sms" + IAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class SmsAuthStrategy implements IAuthStrategy {

    private final SysUserDao userDao;

    private final AuthLoginService loginService;

    @Override
    public LoginVo login(String body, SysClientVo client) {
        SmsLoginBody loginBody = JsonUtils.parseObject(body, SmsLoginBody.class);
        ValidatorUtils.validate(loginBody);
        String tenantId = loginBody.getTenantId();
        String phonenumber = loginBody.getPhonenumber();
        String smsCode = loginBody.getSmsCode();
        AuthUserVo user = loadUserByPhonenumber(phonenumber);
        loginService.checkLogin(LoginType.PASSWORD, tenantId, user.getUserName(), () -> !validateSmsCode(tenantId, phonenumber, smsCode));
        LoginUser loginUser=loginService.buildLoginUser(user);
        loginUser.setUserType("sys_user");
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
        loginVo.setClientId(client.getClientKey());
        return loginVo;
    }

    /**
     * 校验短信验证码
     */
    private boolean validateSmsCode(String tenantId, String phonenumber, String smsCode) {
//        String code = RedisUtils.getCacheObject(GlobalConstants.CAPTCHA_CODE_KEY + phonenumber);
        //演示环境专用
        String code = "1234";
        if (StrUtils.isBlank(code)) {
            loginService.recordLogininfor(tenantId, phonenumber, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new CaptchaExpireException();
        }
        return code.equals(smsCode);
    }


    private AuthUserVo loadUserByPhonenumber(String phonenumber) {
        SysUser orgUser = userDao.findByMonum(phonenumber);
        AuthUserVo user=new AuthUserVo();
        user.setUserId(orgUser.getId());
        user.setUserName(orgUser.getUsername());
        user.setPassword(orgUser.getPassword());
        user.setNickName(orgUser.getName());
        user.setDeptId(orgUser.getDepid());
        user.setDeptName(orgUser.getDepna());
        user.setStatus(orgUser.getAvtag()?"0":"1");
        user.setAvatar(orgUser.getAvatar());
        //user.set(orgUser.getUsername());

        if (ObjectUtils.isNull(user)) {
            log.info("登录用户：{} 不存在.", phonenumber);
            throw new UserException("user.not.exists", phonenumber);
        } else if (SystemConstants.DISABLE.equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", phonenumber);
            throw new UserException("user.blocked", phonenumber);
        }
        return user;
    }

}
