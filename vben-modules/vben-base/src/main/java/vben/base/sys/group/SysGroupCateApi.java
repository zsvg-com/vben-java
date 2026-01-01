package vben.base.sys.group;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.Smove;
import vben.common.jdbc.dto.Stree;
import org.springframework.web.bind.annotation.*;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;

import java.util.List;

/**
 * 群组分类管理
 */
@RestController
@RequestMapping("sys/groupc")
@RequiredArgsConstructor
public class SysGroupCateApi {

    private final SysGroupCateService service;

    /**
     * 群组分类树状查询
     * @param id 分类ID，如传入则查询出来的树结构会排除自身及子节点
     * @return 群组树状数据
     */
    @SaCheckPermission("sys:groupc:query")
    @GetMapping("tree")
    public R<List<Stree>> tree(String id) {
        return R.ok(service.findTreeList(id));
    }

    /**
     * 群组分类详情查询
     * @param id 分类ID
     * @return 群组分类对象
     */
    @SaCheckPermission("sys:groupc:query")
    @GetMapping("info/{id}")
    public R<SysGroupCate> info(@PathVariable String id) {
        SysGroupCate cate = service.findById(id);
        return R.ok(cate);
    }

    /**
     * 群组分类新增
     * @param cate 分类对象
     * @return 分类ID
     */
    @Log(title = "群组分类管理", businessType = BusinessType.INSERT)
    @SaCheckPermission("sys:groupc:edit")
    @PostMapping
    public R<String> post(@RequestBody SysGroupCate cate) {
        service.insert(cate);
        return R.ok(null,cate.getId());
    }

    /**
     * 群组分类修改
     * @param cate 分类对象
     * @return 分类ID
     */
    @Log(title = "群组分类管理", businessType = BusinessType.UPDATE)
    @SaCheckPermission("sys:groupc:edit")
    @PutMapping
    public R<String> put(@RequestBody SysGroupCate cate) {
        service.update(cate);
        return R.ok(null,cate.getId());
    }

    /**
     * 群组分类删除
     * @param id 分类ID
     */
    @Log(title = "群组分类管理", businessType = BusinessType.DELETE)
    @SaCheckPermission("sys:groupc:delete")
    @DeleteMapping("{id}")
    public R<Void> delete(@PathVariable String id) {
        service.delete(id);
        return R.ok();
    }

    /**
     * 群组分类移动
     * @param bo 移动对象
     */
    @Log(title = "群组分类管理", businessType = BusinessType.UPDATE)
    @SaCheckPermission("sys:groupc:edit")
    @PostMapping("move")
    public R<Void> move(@RequestBody Smove bo) {
        service.move(bo);
        return R.ok();
    }

}
