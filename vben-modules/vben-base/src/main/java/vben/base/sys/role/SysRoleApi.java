package vben.base.sys.role;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;

import java.util.List;

/**
 * 角色管理
 */
@RestController
@RequestMapping("sys/role")
@RequiredArgsConstructor
public class SysRoleApi {

    private final SysRoleService service;

    /**
     * 角色分页查询
     * @param name 角色名称
     * @return 角色分页数据
     */
    @SaCheckPermission("sys:role:query")
    @GetMapping
    public R<PageData> get(String name) {
        Sqler sqler = new Sqler("sys_role");
        sqler.addLike("t.name", name);
        sqler.addSelect("t.notes,t.ornum,t.crtim");
        sqler.addOrder("t.ornum");
        sqler.addDescOrder("t.crtim");
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 角色详情查询
     * @param id 角色ID
     * @return 角色对象
     */
    @SaCheckPermission("sys:role:query")
    @GetMapping("info/{id}")
    public R<SysRole> info(@PathVariable Long id) {
        SysRole main = service.findById(id);
        return R.ok(main);
    }

    /**
     * 菜单与接口关联数据查询
     * @return 菜单与权限集合
     */
    @SaCheckPermission("sys:role:query")
    @GetMapping("perms")
    public R<List<MenuVo>> perms() {
        List<MenuVo> list = service.findMenuVoList();
        return R.ok(list);
    }

    /**
     * 角色新增
     * @param main 角色对象
     * @return 角色ID
     */
    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    @SaCheckPermission("sys:role:edit")
    @PostMapping
    public R<Long> post(@RequestBody SysRole main) {
        service.insert(main);
        return R.ok(null,main.getId());
    }

    /**
     * 角色修改
     * @param main 角色对象
     * @return 角色ID
     */
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @SaCheckPermission("sys:role:edit")
    @PutMapping
    public R<Long> put(@RequestBody SysRole main) {
        service.update(main);
        return R.ok(null,main.getId());
    }

    /**
     * 角色删除
     * @param ids 角色ID串
     * @return 角色数量
     */
    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @SaCheckPermission("sys:role:delete")
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable Long[] ids) {
        return R.ok(service.delete(ids));
    }

    /**
     * 角色菜单与权限刷新
     */
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @SaCheckPermission("sys:role:edit")
    @PutMapping("cache")
    public R<Void> cache() {
        service.clearCache();
        return R.ok();
    }

}
