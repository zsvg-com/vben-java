package vben.base.mon.oper.log;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;

/**
 * 操作日志管理
 */
@RestController
@RequestMapping("mon/oper/log")
@RequiredArgsConstructor
public class MonOperLogApi {

    private final MonOperLogService service;

    /**
     * 操作日志分页查询
     * @return 日志分页对象
     */
    @SaCheckPermission("mon:oper:query")
    @GetMapping
    public R<PageData> get() {
        Sqler sqler = new Sqler("t.id,t.opmod,t.butyp,t.opuna,t.opip,t.oploc,t.sutag,t.optim,t.cotim","mon_oper_log");
        sqler.addDescOrder("t.optim");
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 操作日志详情查询
     * @param id 日志ID
     * @return 日志对象
     */
    @SaCheckPermission("mon:oper:query")
    @GetMapping("info/{id}")
    public R<MonOperLog> info(@PathVariable Long id) {
        MonOperLog main = service.findById(id);
        return R.ok(main);
    }

    /**
     * 操作日志删除
     */
    @Log(title = "操作日志", businessType = BusinessType.DELETE)
    @SaCheckPermission("mon:oper:delete")
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable Long[] ids) {
        return R.ok(service.delete(ids));
    }

}
