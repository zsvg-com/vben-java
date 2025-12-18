package vben.mybatis.demo.link.cate;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.core.utils.ValidatorUtils;
import vben.common.core.validate.AddGroup;
import vben.common.core.validate.EditGroup;
import vben.common.idempotent.annotation.RepeatSubmit;
import vben.common.jdbc.dto.Lmove;
import vben.common.jdbc.dto.Ltree;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 关联树表案例
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("demo/link/cate")
public class DemoLinkCateController {

    private final DemoLinkCateService service;

    private final String table = "demo_link_cate";

    /**
     * 关联分类表-树状查询
     * @param name 案例名称
     * @param id 排除的案例ID
     * @return 案例树状数据
     */
    @SaCheckPermission("link:cate:query")
    @GetMapping("tree")
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
    @SaCheckPermission("link:cate:query")
    @GetMapping("list")
    public R<List<DemoLinkCate>> getList(String name) {
        Sqler sqler = new Sqler(table);
        sqler.addLike("t.name", name);
        sqler.addSelect("t.notes");
        List<DemoLinkCate> list = service.findList(sqler, DemoLinkCate.class);
        return R.ok(list);
    }

    /**
     * 关联分类表-分页查询
     * @param name 案例名称
     * @return 案例分页数据
     */
    @SaCheckPermission("link:cate:query")
    @GetMapping
    public R<PageData> get(String name) {
        Sqler sqler = new Sqler("demo_easy_main");
        sqler.addLike("t.name", name);
        sqler.addSelect("t.notes");
        sqler.selectCUinfo();
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 关联分类表-详情查询
     * @param id 案例ID
     * @return 案例对象
     */
    @SaCheckPermission("link:cate:query")
    @GetMapping("info/{id}")
    public R<DemoLinkCate> info(@PathVariable("id") Long id) {
        return R.ok(service.select(id));
    }

    /**
     * 关联分类表-新增
     * @param cate 案例对象
     * @return 案例ID
     */
    @SaCheckPermission("link:cate:add")
    @Log(title = "综合树表案例", businessType = BusinessType.INSERT)
    @RepeatSubmit(interval = 2, timeUnit = TimeUnit.SECONDS, message = "{repeat.submit.message}")
    @PostMapping
    public R<Long> post(@RequestBody DemoLinkCate cate) {
        ValidatorUtils.validate(cate, AddGroup.class);
        return R.ok(null,service.insert(cate,table));
    }

    /**
     * 关联分类表-修改
     * @param cate 案例对象
     * @return 案例ID
     */
    @SaCheckPermission("link:cate:edit")
    @Log(title = "关联分类表", businessType = BusinessType.UPDATE)
    @RepeatSubmit
    @PutMapping
    public R<Long> put(@Validated(EditGroup.class) @RequestBody DemoLinkCate cate) {
        return R.ok(null, service.update(cate, table));
    }

    /**
     * 关联分类表-删除
     * @param ids 案例ID串
     * @return 删除的案例数量
     */
    @SaCheckPermission("link:cate:remove")
    @Log(title = "关联分类表", businessType = BusinessType.DELETE)
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable Long[] ids) {
        return R.ok(null,service.delete(ids));
    }

    /**
     * 关联分类表-移动
     * @param bo 移动对象
     */
    @PostMapping("move")
    @Log(title = "关联分类表", businessType = BusinessType.UPDATE)
    public R<Void> move(@RequestBody Lmove bo) {
        service.move(bo, table);
        return R.ok();
    }
}
