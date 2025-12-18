package vben.jpa.demo.single.main;

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
 * 单一主表案例
 */
@RestController
@RequestMapping("demo/single/main")
@RequiredArgsConstructor
public class DemoSingleMainController {

    /**
     * 单一主表-分页查询
     * @param name XX名称
     * @return XX分页数据
     */
    @GetMapping
    @SaCheckPermission("single:main:query")
    public R<PageData> get(String name) {
        Sqler sqler = new Sqler("demo_single_main");
        sqler.addLike("t.name", name);
        sqler.addSelect("t.notes");
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 单一主表-详情查询
     * @param id ID
     * @return XX对象
     */
    @GetMapping("info/{id}")
    @SaCheckPermission("single:main:query")
    public R<DemoSingleMain> info(@PathVariable Long id) {
        DemoSingleMain main = service.select(id);
        return R.ok(main);
    }

    /**
     * 单一主表-新增
     * @param main XX对象
     * @return ID
     */
    @PostMapping
    @SaCheckPermission("single:main:add")
    @Log(title = "单一主表案例", businessType = BusinessType.INSERT)
    @RepeatSubmit
//    @RepeatSubmit(interval = 2, timeUnit = TimeUnit.SECONDS, message = "{repeat.submit.message}")
    public R<Long> post(@RequestBody DemoSingleMain main) {
        return R.ok(null,service.insert(main));
    }

    /**
     * 单一主表-修改
     * @param main XX对象
     * @return ID
     */
    @PutMapping
    @RepeatSubmit
    @SaCheckPermission("single:main:edit")
    @Log(title = "单一主表案例", businessType = BusinessType.UPDATE)
    public R<Long> put(@RequestBody DemoSingleMain main) {
        return R.ok(null,service.update(main));
    }

    /**
     * 单一主表-删除
     * @param ids ID串
     * @return 删除的XX数量
     */
    @DeleteMapping("{ids}")
    @SaCheckPermission("single:main:remove")
    @Log(title = "单一主表案例", businessType = BusinessType.DELETE)
    public R<Integer> delete(@PathVariable Long[] ids) {
        return R.ok(service.delete(ids));
    }

    private final DemoSingleMainService service;

}
