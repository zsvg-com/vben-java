package vben.base.sys.org.user;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.crypto.digest.BCrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.base.sys.org.user.bo.IdAvtagBo;
import vben.base.sys.org.user.bo.IdPacodBo;
import vben.common.core.domain.R;
import vben.common.core.utils.StrUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;

/**
 * 用户管理
 */
@RestController
@RequestMapping("sys/org/user")
@RequiredArgsConstructor
public class SysOrgUserApi {

    private final SysOrgUserService service;

    /**
     * 用户分页查询
     * @param depid 部门ID
     * @param name 用户姓名
     * @param usnam 用户账号
     * @param ninam 用户昵称
     * @param monum 用户手机号
     * @return 用户分页数据
     */
    @SaCheckPermission("sysorg:user:query")
    @GetMapping
    public R<PageData> get(String depid, String name, String usnam, String ninam, String monum) {
        Sqler sqler = new Sqler("sys_org_user");
        if (StrUtils.isNotBlank(name) || StrUtils.isNotBlank(usnam) || StrUtils.isNotBlank(ninam) || StrUtils.isNotBlank(monum)) {
            sqler.addLike("t.name", name);
            sqler.addLike("t.usnam", usnam);
            sqler.addLike("t.ninam", ninam);
            sqler.addLike("t.monum", monum);
        } else if (depid!=null) {
            sqler.addEqual("t.depid", depid);
        }
        sqler.addLeftJoin("d.name depna","sys_org_dept d","d.id=t.depid");
        sqler.addSelect("t.crtim,t.uptim,t.notes,t.avtag,t.monum,t.usnam");
        sqler.addOrder("t.ornum");
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 用户详情查询
     * @param id 用户ID
     * @return 用户对象
     */
    @SaCheckPermission("sysorg:user:query")
    @GetMapping("info/{id}")
    public R<SysOrgUser> info(@PathVariable String id) {
        SysOrgUser main = service.findById(id);
        return R.ok(main);
    }

    /**
     * 用户新增
     * @param main 用户对象
     * @return 用户ID
     */
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @SaCheckPermission("sysorg:user:edit")
    @PostMapping
    public R<String> post(@RequestBody SysOrgUser main) {
        service.insert(main);
        return R.ok(null,main.getId());
    }

    /**
     * 用户修改
     * @param main 用户对象
     * @return 用户ID
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @SaCheckPermission("sysorg:user:edit")
    @PutMapping
    public R<String> put(@RequestBody SysOrgUser main) {
        service.update(main);
        return R.ok(null,main.getId());
    }

    /**
     * 用户删除
     * @param ids 用户ID串
     * @return 用户数量
     */
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @SaCheckPermission("sysorg:user:delete")
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable String[] ids) {
        return R.ok(service.delete(ids));
    }

    /**
     * 用户启用禁用
     * @param bo 用户ID与是否可用组合对象
     */
    @Log(title = "用户管理", businessType = BusinessType.OTHER)
    @SaCheckPermission("sysorg:user:avtag")
    @PutMapping("avtag")
    public R<Void> avtag(@RequestBody IdAvtagBo bo) {
        service.changeAvatg(bo);
        return R.ok();
    }

    /**
     * 用户密码修改
     * @param bo 用户ID与密码组合对象
     */
    //@ApiEncrypt
    @Log(title = "用户管理", businessType = BusinessType.OTHER)
    @SaCheckPermission("sysorg:user:pacod")
    @PutMapping("pacod")
    public R<Void> pacod(@RequestBody IdPacodBo bo) {
        service.resetPassword(bo.getId(), BCrypt.hashpw(bo.getPacod()));
        return R.ok();
    }


}
