package vben.base.sys.notice;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;
import vben.common.sse.utils.SseMessageUtils;

/**
 * 通知管理
 */
@RestController
@RequestMapping("sys/notice")
@RequiredArgsConstructor
public class SysNoticeApi {

    private final SysNoticeService service;

    /**
     * 通知分页查询
     * @param name 通知名称
     * @return 通知分页数据
     */
    @SaCheckPermission("sys:notice:query")
    @GetMapping
    public R<PageData> get(String name) {
        Sqler sqler = new Sqler("sys_notice");
        sqler.addSelect("t.type,t.crtim,t.uptim,t.avtag");
        sqler.addLike("t.name",name);
        sqler.addDescOrder("t.crtim");
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 通知详情查询
     * @param id 通知ID
     * @return 通知对象
     */
    @SaCheckPermission("sys:notice:query")
    @GetMapping("info/{id}")
    public R<SysNotice> info(@PathVariable Long id) {
        SysNotice main = service.findById(id);
        return R.ok(main);
    }

    /**
     * 通知新增
     * @param main 通知对象
     * @return 通知ID
     */
    @Log(title = "参数管理", businessType = BusinessType.INSERT)
    @SaCheckPermission("sys:notice:edit")
    @PostMapping
    public R<Long> post(@RequestBody SysNotice main) {
        service.insert(main);
        String typeName=main.getType()==1?"通知":"公告";
        SseMessageUtils.publishAll("[" + typeName + "] " + main.getName());
        return R.ok(main.getId());
    }

    /**
     * 通知修改
     * @param main 通知对象
     * @return 通知ID
     */
    @Log(title = "参数管理", businessType = BusinessType.UPDATE)
    @SaCheckPermission("sys:notice:edit")
    @PutMapping
    public R<Long> put(@RequestBody SysNotice main) {
        service.update(main);
        return R.ok(main.getId());
    }

    /**
     * 通知删除
     * @param ids 通知ID串
     * @return 通知数量
     */
    @Log(title = "参数管理", businessType = BusinessType.DELETE)
    @SaCheckPermission("sys:notice:delete")
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable Long[] ids) {
        return R.ok(service.delete(ids));
    }

}
