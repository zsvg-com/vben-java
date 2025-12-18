package vben.mybatis.demo.link.main;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.idempotent.annotation.RepeatSubmit;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;

import java.util.concurrent.TimeUnit;

/**
 * 关联主表案例
 *
 * @author baby_fox
 * @date 2025-11-29
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("demo/link/main")
public class DemoLinkMainController {

    private final DemoLinkMainService service;

    /**
     * 关联主表-分页查询
     * @param name XX名称
     * @param catid 分类ID
     * @return XX分页数据
     */
    @SaCheckPermission("link:main:query")
    @GetMapping
    public R<PageData> get(String name,Long catid) {
        Sqler sqler = new Sqler("demo_link_main");
        sqler.addLike("t.name", name);
        sqler.addSelect("t.notes");
        sqler.addEqual("t.catid", catid);
        sqler.addLeftJoin("c.name catna","demo_link_cate c","c.id=t.catid");
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 关联主表-详情查询
     * @param id ID
     * @return XX对象
     */
    @SaCheckPermission("link:main:query")
    @GetMapping("info/{id}")
    public R<DemoLinkMain> info(@NotNull(message = "主键不能为空")
                                 @PathVariable("id") Long id) {
        return R.ok(service.selectx(id));
    }

    /**
     * 关联主表-新增
     * @param main XX对象
     * @return ID
     */
    @SaCheckPermission("link:main:add")
    @Log(title = "关联主表", businessType = BusinessType.INSERT)
    @RepeatSubmit(interval = 2, timeUnit = TimeUnit.SECONDS, message = "{repeat.submit.message}")
    @PostMapping
    public R<Long> post(@RequestBody DemoLinkMain main) {
        return R.ok(null,service.insertx(main));
    }

    /**
     * 关联主表-修改
     * @param main XX对象
     * @return ID
     */
    @SaCheckPermission("link:main:edit")
    @Log(title = "关联主表", businessType = BusinessType.UPDATE)
    @RepeatSubmit
    @PutMapping
    public R<Long> put(@RequestBody DemoLinkMain main) {
        return R.ok(null,service.updatex(main));
    }

    /**
     * 关联主表-删除
     * @param ids ID串
     * @return 删除的XX数量
     */
    @SaCheckPermission("link:main:remove")
    @Log(title = "关联主表", businessType = BusinessType.DELETE)
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable Long[] ids) {
        return R.ok(null,service.deletex(ids));
    }

}
