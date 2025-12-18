package vben.base.sys.social;

import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vben.common.core.domain.R;

@Slf4j
@SaIgnore
@RequiredArgsConstructor
@RestController
@RequestMapping("sys/social")
public class SysSocialApi {

    private final ISysSocialService sysSocialService;

    @GetMapping("list")
    public R list() {
        return R.ok(sysSocialService.queryList(null));
    }
}
