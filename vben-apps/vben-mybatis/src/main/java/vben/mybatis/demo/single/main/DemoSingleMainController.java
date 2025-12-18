package vben.mybatis.demo.single.main;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.idempotent.annotation.RepeatSubmit;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;

import java.util.concurrent.TimeUnit;

/**
 * 单一主表案例
 *
 * @author baby_fox
 * @date 2025-11-29
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("demo/single/main")
public class DemoSingleMainController {

    private final DemoSingleMainService service;

    private final JdbcHelper jdbcHelper;

    /**
     * 单一主表-分页查询
     * @param name XX名称
     * @return XX分页数据
     */
    @SaCheckPermission("single:main:query")
    @GetMapping
    public R<PageData> get(String name) {
        Sqler sqler = new Sqler("demo_single_main");
        sqler.addLike("t.name", name);
        sqler.addSelect("t.notes");
        sqler.selectCUinfo();
        return R.ok(jdbcHelper.findPageData(sqler));
    }

    /**
     * 单一主表-详情查询
     * @param id ID
     * @return XX对象
     */
    @SaCheckPermission("single:main:query")
    @GetMapping("info/{id}")
    public R<DemoSingleMain> info(@PathVariable("id") Long id) {
        return R.ok(service.select(id));
    }

    /**
     * 单一主表-新增
     * @param main XX对象
     * @return ID
     */
    @SaCheckPermission("single:main:add")
    @Log(title = "单一主表", businessType = BusinessType.INSERT)
    @RepeatSubmit(interval = 2, timeUnit = TimeUnit.SECONDS, message = "{repeat.submit.message}")
    @PostMapping
    public R<Long> post(@RequestBody DemoSingleMain main) {
        return R.ok(null,service.insert(main));
    }

    /**
     * 单一主表-修改
     * @param main XX对象
     * @return ID
     */
    @SaCheckPermission("single:main:edit")
    @Log(title = "单一主表", businessType = BusinessType.UPDATE)
    @RepeatSubmit
    @PutMapping
    public R<Long> put(@RequestBody DemoSingleMain main) {
        return R.ok(null,service.update(main));
    }

    /**
     * 单一主表-删除
     * @param ids ID串
     * @return 删除的XX数量
     */
    @SaCheckPermission("single:main:remove")
    @Log(title = "单一主表", businessType = BusinessType.DELETE)
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable Long[] ids) {
        return R.ok(null,service.delete(ids));
    }
}
