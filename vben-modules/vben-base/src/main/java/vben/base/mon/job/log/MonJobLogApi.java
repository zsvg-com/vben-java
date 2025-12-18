package vben.base.mon.job.log;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;

/**
 * 定时任务日志
 */
@RestController
@RequestMapping("mon/job/log")
@RequiredArgsConstructor
public class MonJobLogApi {

    private final MonJobLogService service;

    /**
     * 日志分页查询
     * @param name 任务名称
     * @return 日志分页数据
     */
    @SaCheckPermission("monjob:log:query")
    @GetMapping
    public R<PageData> get(String name) {
        Sqler sqler = new Sqler("t.id,t.name,t.sttim,t.entim,t.ret", "mon_job_log");
        sqler.addLike("t.name", name);
        sqler.addOrder("t.entim desc");
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 日志详情查询
     * @param id 日志ID
     * @return 日志对象
     */
    @SaCheckPermission("monjob:log:query")
    @GetMapping("info/{id}")
    public R<MonJobLog> info(@PathVariable Long id) {
        return R.ok(service.findOne(id));
    }

    /**
     * 日志删除
     * @param ids 日志ID串
     * @return 日志数量
     */
    @Log(title = "定时任务日志", businessType = BusinessType.DELETE)
    @SaCheckPermission("monjob:log:delete")
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable Long[] ids) {
        return R.ok(service.delete(ids));
    }

    /**
     * 日志清空处理
     */
    @Log(title = "定时任务日志", businessType = BusinessType.DELETE)
    @SaCheckPermission("monjob:log:delete")
    @DeleteMapping("all")
    public R<Void> deleteAll() {
        service.deleteAll();
        return R.ok();
    }
}
