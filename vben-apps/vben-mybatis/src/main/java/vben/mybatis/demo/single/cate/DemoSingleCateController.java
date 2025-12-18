package vben.mybatis.demo.single.cate;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.core.utils.ValidatorUtils;
import vben.common.core.validate.AddGroup;
import vben.common.core.validate.EditGroup;
import vben.common.idempotent.annotation.RepeatSubmit;
import vben.common.jdbc.dto.Ltree;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 单一树表案例
 *
 * @author baby_fox
 * @date 2025-11-29
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("demo/single/cate")
public class DemoSingleCateController {

    private final DemoSingleCateService service;

    private final JdbcHelper jdbcHelper;

    private final String table = "demo_single_cate";

    /**
     * 单一树表-树状查询
     * @param name XX名称
     * @param id 排除的ID
     * @return XX树状数据
     */
    @SaCheckPermission("single:cate:query")
    @GetMapping("tree")
    public R<List<Ltree>> getTree(String name, Long id) {
        Sqler sqler = new Sqler(table);
        List<Ltree> list = service.findTreeList(sqler, name, id);
        return R.ok(list);
    }

    /**
     * 单一树表-列表查询
     * @param name XX名称
     * @return XX列表数据
     */
    @SaCheckPermission("single:cate:query")
    @GetMapping("list")
    public R<List<DemoSingleCate>> getList(String name) {
        Sqler sqler = new Sqler(table);
        sqler.addLike("t.name", name);
        sqler.addSelect("t.notes");
        List<DemoSingleCate> list = service.findList(sqler, DemoSingleCate.class);
        return R.ok(list);
    }

    /**
     * 单一树表-分页查询
     * @param name XX名称
     * @return XX分页数据
     */
    @SaCheckPermission("single:cate:query")
    @GetMapping
    public R<PageData> get(String name) {
        Sqler sqler = new Sqler("demo_single_main");
        sqler.addLike("t.name", name);
        sqler.addSelect("t.notes");
        sqler.selectCUinfo();
        return R.ok(jdbcHelper.findPageData(sqler));
    }

    /**
     * 单一树表-详情查询
     * @param id ID
     * @return XX对象
     */
    @SaCheckPermission("single:cate:query")
    @GetMapping("info/{id}")
    public R<DemoSingleCate> info(@PathVariable("id") Long id) {
        return R.ok(service.select(id));
    }

    /**
     * 单一树表-新增
     * @param cate XX对象
     * @return ID
     */
    @SaCheckPermission("single:cate:add")
    @Log(title = "单一树表", businessType = BusinessType.INSERT)
    @RepeatSubmit(interval = 2, timeUnit = TimeUnit.SECONDS, message = "{repeat.submit.message}")
    @PostMapping
    public R<Long> post(@RequestBody DemoSingleCate cate) {
        ValidatorUtils.validate(cate, AddGroup.class);
        return R.ok(null,service.insert(cate,table));
    }

    /**
     * 单一树表-修改
     * @param cate XX对象
     * @return ID
     */
    @SaCheckPermission("single:cate:edit")
    @Log(title = "单一树表", businessType = BusinessType.UPDATE)
    @RepeatSubmit
    @PutMapping
    public R<Long> put(@Validated(EditGroup.class) @RequestBody DemoSingleCate cate) {
        return R.ok(null, service.update(cate, table));
    }

    /**
     * 单一树表-删除
     * @param ids ID串
     * @return 删除的XX数量
     */
    @SaCheckPermission("single:cate:remove")
    @Log(title = "单一树表", businessType = BusinessType.DELETE)
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable Long[] ids) {
        return R.ok(null,service.delete(ids));
    }
}
