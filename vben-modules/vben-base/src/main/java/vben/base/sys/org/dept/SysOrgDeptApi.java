package vben.base.sys.org.dept;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.dto.Smove;
import vben.common.jdbc.dto.Stree;
import vben.common.jdbc.root.Db;
import vben.common.jdbc.sqler.Sqler;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;

import java.util.List;

/**
 * 部门管理
 */
@RestController
@RequestMapping("sys/org/dept")
@RequiredArgsConstructor
public class SysOrgDeptApi {

    private final SysOrgDeptService service;

    /**
     * 部门列表查询
     * @param name 部门名称
     * @return 部门集合数据
     */
    @GetMapping("list")
    @SaCheckPermission("sysorg:dept:query")
    public R<List<SysOrgDeptVo>> list(String name) {
        Sqler sqler = new Sqler("sys_org_dept");
        sqler.addSelect("t.pid,t.notes,t.ornum");
        sqler.addLike("t.name", name);
        sqler.addOrder("t.ornum");
        sqler.addWhere("t.avtag="+ Db.True);
        sqler.selectCUinfo();
        return R.ok(service.findVoList(sqler));
    }

    /**
     * 部门分页查询
     * @param name 部门名称
     * @param pid 上级部门ID
     * @return 部门分页数据
     */
    @SaCheckPermission("sysorg:dept:query")
    @GetMapping
    public R<PageData> get(String name,String pid) {
        Sqler sqler = new Sqler("sys_org_dept");
        sqler.addSelect("t.pid,t.notes,t.ornum");
        sqler.selectCUinfo();
        sqler.addLike("t.name", name);
        sqler.addEqual("t.pid", pid);
        sqler.addOrder("t.ornum");
        sqler.addWhere("t.avtag="+Db.True);
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 部门树状查询
     * @param id 部门ID，如传入则查询出来的树结构会排除自身及子节点
     * @return 部门树状集合
     */
    @SaCheckPermission("sysorg:dept:query")
    @GetMapping("tree")
    public R<List<Stree>> tree(String id) {
        return R.ok(service.findTree(id));
    }

    /**
     * 部门详情查询
     * @param id 部门ID
     * @return 部门对象
     */
    @SaCheckPermission("sysorg:dept:query")
    @GetMapping("info/{id}")
    public R<SysOrgDept> info(@PathVariable String id) {
        SysOrgDept main = service.findById(id);
        return R.ok(main);
    }

    /**
     * 部门新增
     * @param main 部门对象
     * @return 部门ID
     */
    @SaCheckPermission("sysorg:dept:edit")
    @PostMapping
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    public R<String> post(@RequestBody SysOrgDept main) {
        service.insert(main);
        return R.ok(null,main.getId());
    }

    /**
     * 部门修改
     * @param main 部门对象
     * @return 部门ID
     */
    @SaCheckPermission("sysorg:dept:edit")
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<String> put(@RequestBody SysOrgDept main) {
        service.update(main);
        return R.ok(null,main.getId());
    }

    /**
     * 部门删除
     * @param ids 部门ID串
     * @return 部门数量
     */
    @SaCheckPermission("sysorg:dept:delete")
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable String[] ids) {
        return R.ok(service.delete(ids));
    }

    /**
     * 部门移动
     * @param bo 移动BO对象
     */
    @SaCheckPermission("sysorg:dept:edit")
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @PostMapping("move")
    public R<Void> move(@RequestBody Smove bo) {
        service.move(bo);
        return R.ok();
    }

}
