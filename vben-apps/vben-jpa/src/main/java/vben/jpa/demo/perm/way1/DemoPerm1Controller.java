package vben.jpa.demo.perm.way1;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.idempotent.annotation.RepeatSubmit;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;

/**
 * 数据权限案例1
 */
@RestController
@RequestMapping("demo/perm1")
@RequiredArgsConstructor
public class DemoPerm1Controller {

    /**
     * 数据权限案例1-分页查询
     * @param name XX名称
     * @return XX分页数据
     */
    @GetMapping
    @SaCheckPermission("demo:perm1:query")
    public R<PageData> get(String name) {
        Sqler sqler = new Sqler("demo_perm1");
        sqler.addLike("t.name", name);
        sqler.addSelect("t.notes");
        sqler.addLeftJoin("a1.name bluna", "sys_actor a1", "a1.id=t.bluid");
        sqler.addLeftJoin("a2.name blona", "sys_actor a2", "a2.id=t.bloid");
        return R.ok(service.findPageDataByPerm(sqler));
    }

    /**
     * 数据权限案例1-详情查询
     * @param id ID
     * @return XX对象
     */
    @GetMapping("info/{id}")
    @SaCheckPermission("demo:perm1:query")
    public R<DemoPerm1> info(@PathVariable Long id) {
        DemoPerm1 main = service.select(id);
        service.checkPerm(main.getBlman().getId(), main.getBlorg().getId());
        return R.ok(main);
    }

    /**
     * 数据权限案例1-新增
     * @param main XX对象
     * @return ID
     */
    @PostMapping
    @SaCheckPermission("demo:perm1:add")
    @Log(title = "数据权限案例1", businessType = BusinessType.INSERT)
    @RepeatSubmit
    public R<Long> post(@RequestBody DemoPerm1 main) {
        return R.ok(null,service.insert(main));
    }

    /**
     * 数据权限案例1-修改
     * @param main XX对象
     * @return ID
     */
    @PutMapping
    @RepeatSubmit
    @SaCheckPermission("demo:perm1:edit")
    @Log(title = "数据权限案例1", businessType = BusinessType.UPDATE)
    public R<Long> put(@RequestBody DemoPerm1 main) {
        DemoPerm1 dbMain = service.select(main.getId());
        service.checkPerm(dbMain.getBlman().getId(), dbMain.getBlorg().getId());
        return R.ok(null,service.update(main));
    }

    /**
     * 数据权限案例1-删除
     * @param ids ID串
     * @return 删除的XX数量
     */
    @DeleteMapping("{ids}")
    @SaCheckPermission("demo:perm1:delete")
    @Log(title = "数据权限案例1", businessType = BusinessType.DELETE)
    public R<Integer> delete(@PathVariable Long[] ids) {
        for (Long id : ids) {
            DemoPerm1 main = service.select(id);
            service.checkPerm(main.getBlman().getId(), main.getBlorg().getId());
        }
        return R.ok(service.delete(ids));
    }

    private final DemoPerm1Service service;

}
