package vben.base.sys.perm.api;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;

import java.util.Date;

/**
 * 接口管理
 */
@RestController
@RequestMapping("sys/perm/api")
@RequiredArgsConstructor
public class SysPermApiApi {

    private final SysPermApiService service;

    /**
     * 接口分页查询
     * @param name 接口名称
     * @param menid 所属菜单ID
     * @return 接口分页数据
     */
    @SaCheckPermission("sysperm:api:query")
    @GetMapping
    public R<PageData> get(String name, Long menid) {
        Sqler sqler = new Sqler("sys_perm_api");
        sqler.addLike("t.name", name);
        sqler.addEqual("t.menid", menid);
        sqler.addSelect("t.notes,t.ornum,t.crtim,t.perm");
        sqler.addOrder("t.ornum");
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 接口详情查询
     * @param id 接口ID
     * @return 接口对象
     */
    @SaCheckPermission("sysperm:api:query")
    @GetMapping("info/{id}")
    public R<SysPermApi> one(@PathVariable Long id) {
        SysPermApi main=service.findById(id);
        return R.ok(main);
    }

    /**
     * 接口新增
     * @param main 接口对象
     * @return 接口ID
     */
    @Log(title = "接口管理", businessType = BusinessType.INSERT)
    @SaCheckPermission("sysperm:api:edit")
    @PostMapping
    public R<Long> post(@RequestBody SysPermApi main) {
        main.setAvtag(true);
        service.insert(main);
        return R.ok(null,main.getId());
    }

    /**
     * 接口修改
     * @param main 接口对象
     * @return 接口ID
     */
    @Log(title = "接口管理", businessType = BusinessType.UPDATE)
    @SaCheckPermission("sysperm:api:edit")
    @PutMapping
    public R<Long> put(@RequestBody SysPermApi main) {
        main.setUptim(new Date());
        service.update(main);
        return R.ok(null,main.getId());
    }

    /**
     * 删除接口
     * @param ids 接口ID串
     * @return 接口数量
     */
    @Log(title = "接口管理", businessType = BusinessType.DELETE)
    @SaCheckPermission("sysperm:api:delete")
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable Long[] ids) {
        return R.ok(service.delete(ids));
    }

}
