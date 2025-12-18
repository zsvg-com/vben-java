package vben.base.sys.config;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;

/**
 * 参数管理
 */
@RestController
@RequestMapping("sys/config")
@RequiredArgsConstructor
public class SysConfigApi {

    private final SysConfigService service;

    /**
     * 参数分页查询
     * @param name 参数名称
     * @return 参数分页数据
     */
    @SaCheckPermission("sys:config:query")
    @GetMapping
    public R<PageData> get(String name) {
        Sqler sqler = new Sqler("sys_config");
        sqler.addSelect("t.notes,t.kenam,t.keval,t.crtim,t.intag");
        sqler.addLike("t.name",name);
        sqler.addOrder("t.ornum");
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 参数详情查询
     * @param id 参数ID
     * @return 参数对象
     */
    @SaCheckPermission("sys:config:query")
    @GetMapping("info/{id}")
    public R<SysConfig> info(@PathVariable Long id) {
        SysConfig main = service.findById(id);
        return R.ok(main);
    }

    /**
     * 参数值查询
     * @param kenam 参数键名
     * @return 参数键值
     */
    @GetMapping("value/{kenam}")
    public R<String> value(@PathVariable String kenam) {
        return R.ok(null,service.findValue(kenam));
    }

    /**
     * 参数新增
     * @param main 参数对象
     * @return 参数ID
     */
    @Log(title = "参数管理", businessType = BusinessType.INSERT)
    @SaCheckPermission("sys:config:edit")
    @PostMapping
    public R<Long> post(@RequestBody SysConfig main) {
        return R.ok(service.insert(main));
    }

    /**
     * 参数修改
     * @param main 参数对象
     * @return 参数ID
     */
    @Log(title = "参数管理", businessType = BusinessType.UPDATE)
    @SaCheckPermission("sys:config:edit")
    @PutMapping
    public R<Long> put(@RequestBody SysConfig main) {
        return R.ok(service.update(main));
    }

    /**
     * 参数删除
     * @param ids 参数ID串
     * @return 参数数量
     */
    @Log(title = "参数管理", businessType = BusinessType.DELETE)
    @SaCheckPermission("sys:config:delete")
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable Long[] ids) {
        return R.ok(service.delete(ids));
    }

    /**
     * 参数缓存清理
     */
    @Log(title = "参数管理", businessType = BusinessType.OTHER)
    @SaCheckPermission("sys:config:edit")
    @DeleteMapping("clear")
    public R<Void> clear() {
        service.clearCache();
        return R.ok();
    }

}
