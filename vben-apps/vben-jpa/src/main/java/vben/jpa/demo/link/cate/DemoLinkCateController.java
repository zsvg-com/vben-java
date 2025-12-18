package vben.jpa.demo.link.cate;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.idempotent.annotation.RepeatSubmit;
import vben.common.jdbc.dto.Lmove;
import vben.common.jdbc.dto.Ltree;
import vben.common.jdbc.sqler.Sqler;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 关联分类表案例
 */
@RestController
@RequestMapping("demo/link/cate")
@RequiredArgsConstructor
public class DemoLinkCateController {

    private final String table = "demo_link_cate";

    /**
     * 关联分类表-树状查询
     * @param name 案例名称
     * @param id 排除的案例ID
     * @return 案例树状数据
     */
    @GetMapping("tree")
    @SaCheckPermission("link:cate:query")
    public R<List<Ltree>> getTree(String name, Long id) {
        Sqler sqler = new Sqler(table);
        List<Ltree> list = service.findTreeList(sqler, name, id);
        return R.ok(list);
    }

    /**
     * 关联分类表-列表查询
     * @param name 案例名称
     * @return 案例列表数据
     */
    @GetMapping("list")
    @SaCheckPermission("link:cate:query")
    public R<List<DemoLinkCate>> getList(String name) {
        Sqler sqler = new Sqler(table);
        sqler.addLike("t.name", name);
        sqler.addSelect("t.notes");
        List<DemoLinkCate> list = service.findList(sqler, DemoLinkCate.class);
        return R.ok(list);
    }

    /**
     * 关联分类表-详情查询
     * @param id 案例ID
     * @return 案例对象
     */
    @GetMapping("info/{id}")
    @SaCheckPermission("link:cate:query")
    public R<DemoLinkCate> info(@PathVariable Long id) {
        DemoLinkCate cate = service.select(id);
        return R.ok(cate);
    }

    /**
     * 关联分类表-新增
     * @param cate 案例对象
     * @return 案例ID
     */
    @PostMapping
    @SaCheckPermission("link:cate:add")
    @Log(title = "综合树表案例", businessType = BusinessType.INSERT)
    @RepeatSubmit(interval = 2, timeUnit = TimeUnit.SECONDS, message = "{repeat.submit.message}")
    public R<Long> post(@RequestBody DemoLinkCate cate) {
        return R.ok(null, service.insert(cate,table));
    }

    /**
     * 关联分类表-修改
     * @param cate 案例对象
     * @return 案例ID
     */
    @PutMapping
    @RepeatSubmit
    @SaCheckPermission("link:cate:edit")
    @Log(title = "关联分类表案例", businessType = BusinessType.UPDATE)
    public R<Long> put(@RequestBody DemoLinkCate cate) {
        return R.ok(null, service.update(cate, table));
    }

    /**
     * 关联分类表-删除
     * @param ids 案例ID串
     * @return 删除的案例数量
     */
    @DeleteMapping("{ids}")
    @SaCheckPermission("link:cate:remove")
    @Log(title = "关联分类表案例", businessType = BusinessType.DELETE)
    public R<Integer> delete(@PathVariable Long[] ids) {
        return R.ok(service.delete(ids));
    }

    /**
     * 关联分类表-移动
     * @param bo 移动对象
     */
    @PostMapping("move")
    @SaCheckPermission("link:cate:edit")
    @Log(title = "关联分类表案例", businessType = BusinessType.OTHER)
    public R<Void> move(@RequestBody Lmove bo) {
        service.move(bo, table);
        return R.ok();
    }

    private final DemoLinkCateService service;

}
