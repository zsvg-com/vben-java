package vben.base.auth.login;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vben.base.sys.org.user.SysOrgUserDao;
import vben.base.sys.perm.api.SysPermApiDao;
import vben.common.core.constant.CacheConstants;
import vben.common.core.constant.Constants;
import vben.common.core.domain.model.LoginUser;
import vben.common.core.enums.LoginType;
import vben.common.core.exception.user.UserException;
import vben.common.core.utils.MessageUtils;
import vben.common.core.utils.ObjectUtils;
import vben.common.core.utils.ServletUtils;
import vben.common.core.utils.SpringUtils;
import vben.common.log.event.LogininforEvent;
import vben.common.redis.utils.RedisUtils;
import vben.common.satoken.utils.LoginHelper;
import vben.base.auth.user.AuthUserVo;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Supplier;

/**
 * 登录校验方法
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class AuthLoginService {

    @Value("${user.password.maxRetryCount}")
    private Integer maxRetryCount;

    @Value("${user.password.lockTime}")
    private Integer lockTime;

    private final SysPermApiDao apiDao;

    private final SysOrgUserDao userDao;


    /**
     * 退出登录
     */
    public void logout() {
        try {
            LoginUser loginUser = LoginHelper.getLoginUser();
            if (ObjectUtils.isNull(loginUser)) {
                return;
            }
        } catch (NotLoginException ignored) {
        } finally {
            try {
                StpUtil.logout();
            } catch (NotLoginException ignored) {
            }
        }
    }

    /**
     * 构建登录用户
     */
    public LoginUser buildLoginUser(AuthUserVo user) {
        LoginUser loginUser = new LoginUser();
        String userId = user.getUserId();
        loginUser.setTenantId(user.getTenantId());
        loginUser.setUserId(user.getUserId());
        loginUser.setDeptId(user.getDeptId());
        loginUser.setUsername(user.getUserName());
        loginUser.setNickname(user.getNickName());
        loginUser.setUserType(user.getUserType());

        String oids = RedisUtils.getCacheObject("oids:" + userId);
        if(oids == null){
            oids = userDao.findOrgs(userId+"");
            RedisUtils.setCacheObject("oids:" + userId, oids);
        }
        if("u1".equals(userId)){
            loginUser.setMenuPermission(new HashSet<>(Arrays.asList("*:*:*")));
            loginUser.setRolePermission(new HashSet<>(Arrays.asList("superadmin")));
        }else{
            loginUser.setMenuPermission(apiDao.findSetByOids(oids));
            loginUser.setRolePermission(new HashSet<>());
        }
        return loginUser;
    }


    /**
     * 登录校验
     */
    public void checkLogin(LoginType loginType, String tenantId, String username, Supplier<Boolean> supplier) {
        String errorKey = CacheConstants.PWD_ERR_CNT_KEY + username;
        String loginFail = Constants.LOGIN_FAIL;

        // 获取用户登录错误次数，默认为0 (可自定义限制策略 例如: key + username + ip)
        int errorNumber = ObjectUtils.defaultIfNull(RedisUtils.getCacheObject(errorKey), 0);
        // 锁定时间内登录 则踢出
//        if (errorNumber >= maxRetryCount) {
//            recordLogininfor(tenantId, username, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime));
//            throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
//        }

        if (supplier.get()) {
            // 错误次数递增
            errorNumber++;
            RedisUtils.setCacheObject(errorKey, errorNumber, Duration.ofMinutes(lockTime));
            // 达到规定错误次数 则锁定登录
            if (errorNumber >= maxRetryCount) {
                recordLogininfor(tenantId, username, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime));
                throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
            } else {
                // 未达到规定错误次数
                recordLogininfor(tenantId, username, loginFail, MessageUtils.message(loginType.getRetryLimitCount(), errorNumber));
                throw new UserException(loginType.getRetryLimitCount(), errorNumber);
            }
        }

        // 登录成功 清空错误次数
        RedisUtils.deleteObject(errorKey);
    }

    /**
     * 记录登录信息
     *
     * @param tenantId 租户ID
     * @param username 用户名
     * @param status   状态
     * @param message  消息内容
     */
    public void recordLogininfor(String tenantId, String username, String status, String message) {
        LogininforEvent logininforEvent = new LogininforEvent();
        logininforEvent.setTenantId(tenantId);
        logininforEvent.setUsername(username);
        logininforEvent.setStatus(status);
        logininforEvent.setMessage(message);
        logininforEvent.setRequest(ServletUtils.getRequest());
        SpringUtils.context().publishEvent(logininforEvent);
    }

}
