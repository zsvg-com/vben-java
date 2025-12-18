package vben.base.tool.oss.api;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.core.validate.AddGroup;
import vben.common.core.validate.EditGroup;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.web.core.BaseController;
import vben.base.tool.oss.bo.SysOssConfigBo;
import vben.base.tool.oss.service.SysOssConfigService;
import vben.base.tool.oss.vo.SysOssConfigVo;

import java.util.List;

/**
 * 对象存储配置
 *
 * @author Lion Li
 * @author 孤舟烟雨
 * @date 2021-08-13
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/resource/oss/config")
public class SysOssConfigController extends BaseController {

    private final SysOssConfigService ossConfigService;

    private final JdbcHelper jdbcHelper;

    @GetMapping
    public R get() {
        Sqler sqler = new Sqler("t.*","sys_oss_config");
        return R.ok(jdbcHelper.findPageData(sqler));
    }

//    /**
//     * 查询对象存储配置列表
//     */
//    @SaCheckPermission("system:ossConfig:list")
//    @GetMapping("/list")
//    public TableDataInfo<SysOssConfigVo> list(@Validated(QueryGroup.class) SysOssConfigBo bo, PageQuery pageQuery) {
//        return ossConfigService.queryPageList(bo, pageQuery);
//    }

    /**
     * 获取对象存储配置详细信息
     *
     * @param ossConfigId OSS配置ID
     */
    @SaCheckPermission("system:ossConfig:list")
    @GetMapping("info/{ossConfigId}")
    public R<SysOssConfigVo> info(@NotNull(message = "主键不能为空")
                                     @PathVariable Long ossConfigId) {
        return R.ok(ossConfigService.queryById(ossConfigId));
    }

    /**
     * 新增对象存储配置
     */
    @SaCheckPermission("system:ossConfig:add")
    @PostMapping()
    public R<Void> post(@Validated(AddGroup.class) @RequestBody SysOssConfigBo bo) {
        return toAjax(ossConfigService.insertByBo(bo));
    }

    /**
     * 修改对象存储配置
     */
    @SaCheckPermission("system:ossConfig:edit")
    @PutMapping()
    public R<Void> put(@Validated(EditGroup.class) @RequestBody SysOssConfigBo bo) {
        return toAjax(ossConfigService.updateByBo(bo));
    }

    /**
     * 删除对象存储配置
     *
     * @param ossConfigIds OSS配置ID串
     */
    @SaCheckPermission("system:ossConfig:remove")
    @DeleteMapping("/{ossConfigIds}")
    public R<Void> delete(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ossConfigIds) {
        return toAjax(ossConfigService.deleteWithValidByIds(List.of(ossConfigIds), true));
    }

    /**
     * 状态修改
     */
    @SaCheckPermission("system:ossConfig:edit")
    @PutMapping("/changeStatus")
    public R<Void> changeStatus(@RequestBody SysOssConfigBo bo) {
        return toAjax(ossConfigService.updateOssConfigStatus(bo));
    }
}
