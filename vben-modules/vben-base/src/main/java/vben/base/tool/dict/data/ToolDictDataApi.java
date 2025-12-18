package vben.base.tool.dict.data;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;

/**
 * 字典数据管理
 */
@RestController
@RequestMapping("tool/dict/data")
@RequiredArgsConstructor
public class ToolDictDataApi {

    /**
     * 字典数据分页查询
     * @param name 字典数据名称
     * @param dicid 字典ID
     * @return 字典数据分页数据
     */
    @SaCheckPermission("tooldict:data:query")
    @GetMapping
    public R<PageData> get(String name, Long dicid) {
        Sqler sqler = new Sqler("t.id","tool_dict_data");
        sqler.addLike("t.name", name).addEqual("t.dicid", dicid);
        sqler.addSelect("t.notes,t.ornum,t.dalab,t.daval,t.crtim,t.shsty");
        sqler.addOrder("t.ornum");
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 字典数据详情查询
     * @param id 字典数据ID
     * @return 字典数据对象
     */
    @SaCheckPermission("tooldict:data:query")
    @GetMapping("info/{id}")
    public R<ToolDictData> info(@PathVariable Long id) {
        ToolDictData main = service.findById(id);
        return R.ok(main);
    }

    /**
     * 字典数据新增
     * @param main 字典数据对象
     * @return 字典数据ID
     */
    @Log(title = "字典数据管理", businessType = BusinessType.INSERT)
    @SaCheckPermission("tooldict:data:edit")
    @PostMapping
    public R<Long> post(@RequestBody ToolDictData main) {
        service.insert(main);
        return R.ok(null,main.getId());
    }

    /**
     * 字典数据修改
     * @param main 字典数据对象
     * @return 字典数据ID
     */
    @Log(title = "字典数据管理", businessType = BusinessType.UPDATE)
    @SaCheckPermission("tooldict:data:edit")
    @PutMapping
    public R<Long> put(@RequestBody ToolDictData main) {
        service.update(main);
        return R.ok(null,main.getId());
    }

    /**
     * 字典数据删除
     * @param ids 字典数据ID串
     * @return
     */
    @Log(title = "字典数据管理", businessType = BusinessType.DELETE)
    @SaCheckPermission("tooldict:data:delete")
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable Long[] ids) {
        return R.ok(service.delete(ids));
    }

    private final ToolDictDataService service;

}
