package vben.base.mon.login.log;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;

/**
 * 登录日志
 */
@RestController
@RequestMapping("mon/login/log")
@RequiredArgsConstructor
public class MonLoginLogApi {

    private final MonLoginLogService service;

    /**
     * 登录日志分页查询
     */
    @SaCheckPermission("mon:login:query")
    @GetMapping
    public R<PageData> get() {
        Sqler sqler = new Sqler("t.id,t.usnam,t.clkey,t.loip,t.loloc,t.browser,t.os,t.sutag,t.himsg,t.lotim","mon_login_log");
        sqler.addDescOrder("t.lotim");
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 登录日志详情查询
     */
    @SaCheckPermission("mon:login:query")
    @GetMapping("info/{id}")
    public R<MonLoginLog> info(@PathVariable Long id) {
        MonLoginLog main = service.findById(id);
        return R.ok(main);
    }

    /**
     * 登录日志删除
     */
    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @SaCheckPermission("mon:login:delete")
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable Long[] ids) {
        return R.ok(service.delete(ids));
    }

}
