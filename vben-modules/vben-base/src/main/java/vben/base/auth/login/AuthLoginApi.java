package vben.base.auth.login;

import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import vben.base.auth.login.strategy.IAuthStrategy;
import vben.common.core.domain.R;
import vben.common.core.domain.model.LoginBody;
import vben.common.core.utils.ValidatorUtils;
import vben.common.json.utils.JsonUtils;
import vben.common.satoken.utils.LoginHelper;
import vben.common.sse.dto.SseMessageDto;
import vben.common.sse.utils.SseMessageUtils;
import vben.base.auth.login.vo.LoginVo;
import vben.base.auth.login.vo.SysClientVo;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 认证
 *
 * @author Lion Li
 */
@Slf4j
@SaIgnore
@RequiredArgsConstructor
@RestController
public class AuthLoginApi {

    /**
     * 登录方法
     * 打开ApiEncrypt加密传输时，需要前端配合，目前.net后端暂未实现，为保持统一暂时java后端也不启用
     * @param body 登录信息
     * @return 结果
     */
    //@ApiEncrypt
    @PostMapping("/auth/login")
    public R<LoginVo> login(@RequestBody String body) {

        LoginBody loginBody = JsonUtils.parseObject(body, LoginBody.class);
        ValidatorUtils.validate(loginBody);
        // 授权类型和客户端id
        String clientId = loginBody.getClientId();
        String grantType = loginBody.getGrantType();
        SysClientVo client=new SysClientVo();
        client.setTimeout(604800L);
//        client.setTimeout(30L);
//        client.setActiveTimeout(1800L);//30分钟
        client.setActiveTimeout(36000L);//10个小时
//        client.setActiveTimeout(30L);
        client.setId(1L);
        client.setDeviceType("pc");
        client.setClientKey("e5cd7e4891bf95d1d19206ce24a7b32e");
        // 登录
        LoginVo loginVo = IAuthStrategy.login(body, client, grantType);

        String userId = LoginHelper.getUserId();
        scheduledExecutorService.schedule(() -> {
            SseMessageDto dto = new SseMessageDto();
            dto.setMessage("欢迎登录Vben Admin后台管理系统");
            dto.setUserIds(List.of(userId));
            SseMessageUtils.publishMessage(dto);
        }, 5, TimeUnit.SECONDS);
        return R.ok(loginVo);
    }


    /**
     * 退出登录
     */
    @PostMapping("/auth/logout")
    public R<Void> logout() {
        loginService.logout();
        return R.ok("退出成功");
    }

    private final AuthLoginService loginService;

    private final ScheduledExecutorService scheduledExecutorService;


}
