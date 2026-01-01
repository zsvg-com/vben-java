package vben.base.sys.post;

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
 * 岗位管理
 */
@RestController
@RequestMapping("sys/post")
@RequiredArgsConstructor
public class SysPostApi {

    private final SysPostService service;

    /**
     * 岗位分页查询
     * @param depid 部门ID
     * @param name 岗位名称
     * @return 岗位分页数据
     */
    @SaCheckPermission("sys:post:query")
    @GetMapping
    public R<PageData> get(String depid, String name) {
        Sqler sqler = new Sqler("sys_post");
        if (StrUtils.isNotBlank(name)) {
            sqler.addLike("t.name", name);
        } else if (depid!=null) {
            sqler.addEqual("t.depid", depid);
        }
        sqler.addInnerJoin("d.name depna","sys_dept d","d.id=t.depid");
        sqler.addSelect("t.crtim,t.uptim,t.notes,t.avtag");
        sqler.addOrder("t.ornum");
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 岗位详情查询
     * @param id 岗位ID
     * @return 岗位对象
     */
    @SaCheckPermission("sys:post:query")
    @GetMapping("info/{id}")
    public R<SysPost> info(@PathVariable String id) {
        SysPost main = service.findById(id);
        return R.ok(main);
    }

    /**
     * 岗位新增
     * @param main 岗位对象
     * @return 岗位ID
     */
    @Log(title = "岗位管理", businessType = BusinessType.INSERT)
    @SaCheckPermission("sys:post:edit")
    @PostMapping
    public R<String> post(@RequestBody SysPost main) {
        service.insert(main);
        return R.ok(null,main.getId());
    }

    /**
     * 岗位修改
     * @param main 岗位对象
     * @return 岗位ID
     */
    @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
    @SaCheckPermission("sys:post:edit")
    @PutMapping
    public R<String> put(@RequestBody SysPost main) {
        service.update(main);
        return R.ok(null,main.getId());
    }

    /**
     * 岗位删除
     * @param ids 岗位ID串
     * @return 岗位数量
     */
    @Log(title = "岗位管理", businessType = BusinessType.DELETE)
    @SaCheckPermission("sys:post:delete")
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable String[] ids) {
        return R.ok(service.delete(ids));
    }

}
