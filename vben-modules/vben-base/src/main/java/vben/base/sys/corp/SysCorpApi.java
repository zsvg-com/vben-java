package vben.base.sys.corp;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.core.utils.StrUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;

/**
 * 公司管理
 */
@RestController
@RequestMapping("sys/corp")
@RequiredArgsConstructor
public class SysCorpApi {

    private final SysCorpService service;

    /**
     * 公司分页查询
     * @param catid 公司分类ID
     * @param name 公司名称
     * @return 公司分页数据
     */
    @SaCheckPermission("sys:corp:query")
    @GetMapping
    public R<PageData> get(Long catid, String name) {
        Sqler sqler = new Sqler("sys_corp");
        if (StrUtils.isNotBlank(name)) {
            sqler.addLike("t.name", name);
        } else if (catid!=null) {
            sqler.addEqual("t.catid", catid);
        }
        sqler.addLeftJoin("c.name catna","sys_corp_cate c","c.id=t.catid");
        sqler.addSelect("t.ornum,t.notes,t.crtim,t.uptim,t.avtag");
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 公司详情查询
     * @param id 公司ID
     * @return 公司对象
     */
    @SaCheckPermission("sys:corp:query")
    @GetMapping("info/{id}")
    public R<SysCorp> info(@PathVariable String id) {
        SysCorp main = service.findById(id);
        return R.ok(main);
    }

    /**
     * 公司新增
     * @param main 公司对象
     * @return 公司ID
     */
    @Log(title = "公司管理", businessType = BusinessType.INSERT)
    @SaCheckPermission("sys:corp:edit")
    @PostMapping
    public R<String> post(@RequestBody SysCorp main) {
        service.insert(main);
        return R.ok(null,main.getId());
    }

    /**
     * 公司修改
     * @param main 公司对象
     * @return 公司ID
     */
    @Log(title = "公司管理", businessType = BusinessType.UPDATE)
    @SaCheckPermission("sys:corp:edit")
    @PutMapping
    public R<String> put(@RequestBody SysCorp main) {
        service.update(main);
        return R.ok(null,main.getId());
    }

    /**
     * 公司删除
     * @param ids 公司ID串
     * @return 公司数量
     */
    @Log(title = "公司管理", businessType = BusinessType.DELETE)
    @SaCheckPermission("sys:corp:delete")
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable String[] ids) {
        return R.ok(service.delete(ids));
    }

}
