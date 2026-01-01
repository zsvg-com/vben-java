package vben.base.sys.menu;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.Ltree;
import vben.common.jdbc.root.Db;
import vben.common.jdbc.sqler.Sqler;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;

import java.util.List;

/**
 * 菜单管理
 */
@RestController
@RequestMapping("sys/menu")
@RequiredArgsConstructor
public class SysMenuApi {

    private final SysMenuService service;

    /**
     * 菜单列表查询
     * @param name 菜单名称
     * @return 菜单列表集合
     */
    @SaCheckPermission("sys:menu:query")
    @GetMapping("list")
    public R<List<SysMenuVo>> list(String name) {
        Sqler sqler = new Sqler("t.*","sys_menu");
        //sqler.addSelect("t.pid,t.notes,t.crtim,t.uptim,t.ornum");
        sqler.addLike("t.name", name);
        sqler.addOrder("t.ornum");
        sqler.addLeftJoin("oo1.name as cruna", "sys_org oo1", "oo1.id=t.cruid");
        sqler.addLeftJoin("oo2.name as upuna", "sys_org oo2", "oo2.id=t.upuid");
        sqler.addWhere("t.avtag="+ Db.True);
        return R.ok(service.findList(sqler));
    }

    /**
     * 菜单树状查询
     * @param id 菜单ID，如传入则查询出来的树结构会排除自身及子节点
     * @return 菜单树状集合
     */
    @SaCheckPermission("sys:menu:query")
    @GetMapping("tree")
    public R<List<Ltree>> tree(Long id) {
        return R.ok(service.findTree(id));
    }

    /**
     * 菜单详情查询
     * @param id 菜单ID
     * @return 菜单对象
     */
    @SaCheckPermission("sys:menu:query")
    @GetMapping("info/{id}")
    public R<SysMenu> info(@PathVariable Long id) {
        SysMenu main = service.findById(id);
        return R.ok(main);
    }

    /**
     * 菜单新增
     * @param main 菜单对象
     * @return 菜单ID
     */
    @Log(title = "菜单管理", businessType = BusinessType.INSERT)
    @SaCheckPermission("sys:menu:edit")
    @PostMapping
    public R<Long> post(@RequestBody SysMenu main) {
        if(main.getPid()==null){
            main.setPid(0L);
        }
        service.insert(main);
        return R.ok(null,main.getId());
    }

    /**
     * 菜单修改
     * @param main 菜单对象
     * @return 菜单ID
     */
    @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
    @SaCheckPermission("sys:menu:edit")
    @PutMapping
    public R<Long> put(@RequestBody SysMenu main) {
        if(main.getPid()==null){
            main.setPid(0L);
        }
        service.update(main);
        return R.ok(null,main.getId());
    }

    /**
     * 菜单删除
     * @param ids 菜单ID串
     * @return 菜单数量
     */
    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    @SaCheckPermission("sys:menu:delete")
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable Long[] ids) {
        return R.ok(service.delete(ids));
    }

}
