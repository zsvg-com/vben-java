package vben.base.auth.register;

import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vben.common.core.domain.R;
import vben.common.core.domain.model.RegisterBody;

/**
 * 认证
 *
 * @author Lion Li
 */
@Slf4j
@SaIgnore
@RequiredArgsConstructor
@RestController
public class AuthRegisterApi {

    /**
     * 注册
     */
    @PostMapping("/auth/register")
    public R<Void> register(@Validated @RequestBody RegisterBody user) {
        registerService.register(user);
        return R.ok();
    }

    private final SysRegisterService registerService;



}
