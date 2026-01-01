package vben.base.sys.corp;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.Smove;
import vben.common.jdbc.dto.Stree;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;

import java.util.List;

/**
 * 公司分类管理
 */
@RestController
@RequestMapping("sys/corpc")
@RequiredArgsConstructor
public class SysCorpCateApi {

    private final SysCorpCateService service;

    /**
     * 公司分类树状查询
     * @param id 分类ID，如传入则查询出来的树结构会排除自身及子节点
     * @return 公司树状数据
     */
    @SaCheckPermission("sys:corpc:query")
    @GetMapping("tree")
    public R<List<Stree>> tree(String id) {
        return R.ok(service.findTreeList(id));
    }

    /**
     * 公司分类详情查询
     * @param id 分类ID
     * @return 公司分类对象
     */
    @SaCheckPermission("sys:corpc:query")
    @GetMapping("info/{id}")
    public R<SysCorpCate> info(@PathVariable String id) {
        SysCorpCate cate = service.findById(id);
        return R.ok(cate);
    }

    /**
     * 公司分类新增
     * @param cate 分类对象
     * @return 分类ID
     */
    @Log(title = "公司分类管理", businessType = BusinessType.INSERT)
    @SaCheckPermission("sys:corpc:edit")
    @PostMapping
    public R<String> post(@RequestBody SysCorpCate cate) {
        service.insert(cate);
        return R.ok(null,cate.getId());
    }

    /**
     * 公司分类修改
     * @param cate 分类对象
     * @return 分类ID
     */
    @Log(title = "公司分类管理", businessType = BusinessType.UPDATE)
    @SaCheckPermission("sys:corpc:edit")
    @PutMapping
    public R<String> put(@RequestBody SysCorpCate cate) {
        service.update(cate);
        return R.ok(null,cate.getId());
    }

    /**
     * 公司分类删除
     * @param id 分类ID
     */
    @Log(title = "公司分类管理", businessType = BusinessType.DELETE)
    @SaCheckPermission("sys:corpc:delete")
    @DeleteMapping("{id}")
    public R<Void> delete(@PathVariable String id) {
        service.delete(id);
        return R.ok();
    }

    /**
     * 公司分类移动
     * @param bo 移动对象
     */
    @Log(title = "公司分类管理", businessType = BusinessType.UPDATE)
    @SaCheckPermission("sys:corpc:edit")
    @PostMapping("move")
    public R<Void> move(@RequestBody Smove bo) {
        service.move(bo);
        return R.ok();
    }

}
