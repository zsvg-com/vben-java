package vben.base.sys.org.group;

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
 * 群组管理
 */
@RestController
@RequestMapping("sys/org/group")
@RequiredArgsConstructor
public class SysOrgGroupApi {

    private final SysOrgGroupService service;

    /**
     * 群组分页查询
     * @param catid 群组分类ID
     * @param name 群组名称
     * @return 群组分页数据
     */
    @SaCheckPermission("sysorg:group:query")
    @GetMapping
    public R<PageData> get(Long catid, String name) {
        Sqler sqler = new Sqler("sys_org_group");
        if (StrUtils.isNotBlank(name)) {
            sqler.addLike("t.name", name);
        } else if (catid!=null) {
            sqler.addEqual("t.catid", catid);
        }
        sqler.addLeftJoin("c.name catna","sys_org_group_cate c","c.id=t.catid");
        sqler.addSelect("t.ornum,t.notes,t.crtim,t.uptim,t.avtag");
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 群组详情查询
     * @param id 群组ID
     * @return 群组对象
     */
    @SaCheckPermission("sysorg:group:query")
    @GetMapping("info/{id}")
    public R<SysOrgGroup> info(@PathVariable String id) {
        SysOrgGroup main = service.findById(id);
        return R.ok(main);
    }

    /**
     * 群组新增
     * @param main 群组对象
     * @return 群组ID
     */
    @Log(title = "群组管理", businessType = BusinessType.INSERT)
    @SaCheckPermission("sysorg:group:edit")
    @PostMapping
    public R<String> post(@RequestBody SysOrgGroup main) {
        service.insert(main);
        return R.ok(null,main.getId());
    }

    /**
     * 群组修改
     * @param main 群组对象
     * @return 群组ID
     */
    @Log(title = "群组管理", businessType = BusinessType.UPDATE)
    @SaCheckPermission("sysorg:group:edit")
    @PutMapping
    public R<String> put(@RequestBody SysOrgGroup main) {
        service.update(main);
        return R.ok(null,main.getId());
    }

    /**
     * 群组删除
     * @param ids 群组ID串
     * @return 群组数量
     */
    @Log(title = "群组管理", businessType = BusinessType.DELETE)
    @SaCheckPermission("sysorg:group:delete")
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable String[] ids) {
        return R.ok(service.delete(ids));
    }

}
