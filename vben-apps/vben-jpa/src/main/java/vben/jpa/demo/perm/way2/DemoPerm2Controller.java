package vben.jpa.demo.perm.way2;

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
 * 数据权限案例2
 */
@RestController
@RequestMapping("demo/perm2")
@RequiredArgsConstructor
public class DemoPerm2Controller {

    /**
     * 数据权限案例2-分页查询
     * @param name XX名称
     * @return XX分页数据
     */
    @GetMapping
    @SaCheckPermission("demo:perm2:query")
    public R<PageData> get(String name) {
        Sqler sqler = new Sqler("demo_perm2");
        sqler.addLike("t.name", name);
        sqler.addSelect("t.notes");
        return R.ok(service.findPageDataByViewPerm(sqler,"demo_perm2_vimen"));
    }

    /**
     * 数据权限案例2-详情查询
     * @param id ID
     * @return XX对象
     */
    @GetMapping("info/{id}")
    @SaCheckPermission("demo:perm2:query")
    public R<DemoPerm2> info(@PathVariable Long id) {
        DemoPerm2 main = service.select(id);
        service.checkViewPerm(main.getId(),"demo_perm2_vimen");
        return R.ok(main);
    }

    /**
     * 数据权限案例2-新增
     * @param main XX对象
     * @return ID
     */
    @PostMapping
    @SaCheckPermission("demo:perm2:add")
    @Log(title = "数据权限案例2", businessType = BusinessType.INSERT)
    @RepeatSubmit
    public R<Long> post(@RequestBody DemoPerm2 main) {
        return R.ok(null,service.insert(main));
    }

    /**
     * 数据权限案例2-修改
     * @param main XX对象
     * @return ID
     */
    @PutMapping
    @RepeatSubmit
    @SaCheckPermission("demo:perm2:edit")
    @Log(title = "数据权限案例2", businessType = BusinessType.UPDATE)
    public R<Long> put(@RequestBody DemoPerm2 main) {
        service.checkEditPerm(main.getId(),"demo_perm2_edmen");
        return R.ok(null,service.update(main));
    }

    /**
     * 数据权限案例2-删除
     * @param ids ID串
     * @return 删除的XX数量
     */
    @DeleteMapping("{ids}")
    @SaCheckPermission("demo:perm2:delete")
    @Log(title = "数据权限案例2", businessType = BusinessType.DELETE)
    public R<Integer> delete(@PathVariable Long[] ids) {
        for (Long id : ids) {
            service.checkEditPerm(id,"demo_perm2_edmen");
        }
        return R.ok(service.delete(ids));
    }

    private final DemoPerm2Service service;

}
