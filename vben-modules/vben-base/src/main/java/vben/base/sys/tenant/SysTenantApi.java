package vben.base.sys.tenant;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;

/**
 * 租户管理
 */
@RestController
@RequestMapping("sys/tenant")
@RequiredArgsConstructor
public class SysTenantApi {

    private final SysTenantService service;

    /**
     * 租户分页查询
     * @param name 租户名称
     * @return 租户分页数据
     */
    @SaCheckPermission("sys:tenant:query")
    @GetMapping
    public R<PageData> get(String name) {
        Sqler sqler = new Sqler("sys_tenant");
        sqler.addSelect("t.notes,t.crtim");
        sqler.addLike("t.name",name);
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 租户详情查询
     * @param id 租户ID
     * @return 租户对象
     */
    @SaCheckPermission("sys:tenant:query")
    @GetMapping("info/{id}")
    public R<SysTenant> info(@PathVariable Long id) {
        SysTenant main = service.findById(id);
        return R.ok(main);
    }

    /**
     * 租户新增
     * @param main 租户对象
     * @return 租户ID
     */
    @Log(title = "租户管理", businessType = BusinessType.INSERT)
    @SaCheckPermission("sys:tenant:edit")
    @PostMapping
    public R<Long> post(@RequestBody SysTenant main) {
        return R.ok(service.insert(main));
    }

    /**
     * 租户修改
     * @param main 租户对象
     * @return 租户ID
     */
    @Log(title = "租户管理", businessType = BusinessType.UPDATE)
    @SaCheckPermission("sys:tenant:edit")
    @PutMapping
    public R<Long> put(@RequestBody SysTenant main) {
        return R.ok(service.update(main));
    }

    /**
     * 租户删除
     * @param ids 租户ID串
     * @return 租户数量
     */
    @Log(title = "租户管理", businessType = BusinessType.DELETE)
    @SaCheckPermission("sys:tenant:delete")
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable Long[] ids) {
        return R.ok(service.delete(ids));
    }

}
