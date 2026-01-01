package vben.jpa.demo.single.cate;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.idempotent.annotation.RepeatSubmit;
import vben.common.jdbc.dto.Ltree;
import vben.common.jdbc.sqler.Sqler;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 单一树表案例
 */
@RestController
@RequestMapping("demo/single/cate")
@RequiredArgsConstructor
public class DemoSingleCateController {

    private final String table = "demo_single_cate";

    /**
     * 单一树表-树状查询
     * @param name XX名称
     * @param id 排除的ID
     * @return XX树状数据
     */
    @GetMapping("tree")
    @SaCheckPermission("single:cate:query")
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
    @GetMapping("list")
    @SaCheckPermission("single:cate:query")
    public R<List<DemoSingleCate>> getList(String name) {
        Sqler sqler = new Sqler(table);
        sqler.addLike("t.name", name);
        sqler.addSelect("t.notes");
        List<DemoSingleCate> list = service.findList(sqler, DemoSingleCate.class);
        return R.ok(list);
    }

    /**
     * 单一树表-详情查询
     * @param id ID
     * @return XX对象
     */
    @SaCheckPermission("single:cate:query")
    @GetMapping("info/{id}")
    public R<DemoSingleCate> info(@PathVariable Long id) {
        DemoSingleCate cate = service.select(id);
        return R.ok(cate);
    }

    /**
     * 单一树表-新增
     * @param cate XX对象
     * @return ID
     */
    @PostMapping
    @SaCheckPermission("single:cate:add")
    @Log(title = "单一树表案例", businessType = BusinessType.INSERT)
    @RepeatSubmit(interval = 2, timeUnit = TimeUnit.SECONDS, message = "{repeat.submit.message}")
    public R<Long> post(@RequestBody DemoSingleCate cate) {
        return R.ok(null, service.insert(cate,table));
    }

    /**
     * 单一树表-修改
     * @param cate XX对象
     * @return ID
     */
    @PutMapping
    @RepeatSubmit
    @SaCheckPermission("single:cate:edit")
    @Log(title = "单一树表案例", businessType = BusinessType.UPDATE)
    public R<Long> put(@RequestBody DemoSingleCate cate) {
        return R.ok(null, service.update(cate, table));
    }

    /**
     * 单一树表-删除
     * @param ids ID串
     * @return 删除的XX数量
     */
    @DeleteMapping("{ids}")
    @RepeatSubmit
    @SaCheckPermission("single:cate:delete")
    @Log(title = "单一树表案例", businessType = BusinessType.DELETE)
    public R<Integer> delete(@PathVariable Long[] ids) {
        return R.ok(service.delete(ids));
    }

    private final DemoSingleCateService service;

}
