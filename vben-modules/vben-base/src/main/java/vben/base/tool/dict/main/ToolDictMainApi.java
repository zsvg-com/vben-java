package vben.base.tool.dict.main;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.base.tool.dict.data.ToolDictDataVo;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.dto.SidName;
import vben.common.jdbc.sqler.Sqler;
import vben.common.log.annotation.Log;
import vben.common.log.enums.BusinessType;

import java.util.List;

/**
 * 字典管理
 */
@RestController
@RequestMapping("tool/dict/main")
@RequiredArgsConstructor
public class ToolDictMainApi {

    private final ToolDictMainService service;

    /**
     * 字典分页查询
     * @param name 字典名称
     * @return 字典分页对象
     */
    @SaCheckPermission("tooldict:main:query")
    @GetMapping
    public R<PageData> get(String name) {
        Sqler sqler = new Sqler("tool_dict_main");
        sqler.addLike("t.name", name);
        sqler.addSelect("t.notes,t.ornum,t.code,t.crtim");
        sqler.addOrder("t.ornum");
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 字典列表查询
     * @return 字典列表集合
     */
    @SaCheckPermission("tooldict:main:query")
    @GetMapping("list")
    public R<List<SidName>> list() {
        return R.ok(service.findList());
    }

    /**
     * 根据字典代码查询字典数据
     * @param code 字典代码
     * @return 字典数据列表
     */
    //@SaCheckPermission("tooldict:main:data")
    @GetMapping("data")
    public R<List<ToolDictDataVo>> data(String code) {
        return R.ok(service.findDictData(code));
    }

    /**
     * 字典详情查询
     * @param id 字典ID
     * @return 字典对象
     */
    @SaCheckPermission("tooldict:main:query")
    @GetMapping("info/{id}")
    public R<ToolDictMain> info(@PathVariable Long id) {
        ToolDictMain main = service.findById(id);
        return R.ok(main);
    }

    /**
     * 字典新增
     * @param main 字典对象
     * @return 字典ID
     */
    @Log(title = "字典管理", businessType = BusinessType.INSERT)
    @SaCheckPermission("tooldict:main:edit")
    @PostMapping
    public R<Long> post(@RequestBody ToolDictMain main) {
        service.insert(main);
        return R.ok(null,main.getId());
    }

    /**
     * 字典修改
     * @param main 字典对象
     * @return 字典ID
     */
    @Log(title = "字典管理", businessType = BusinessType.UPDATE)
    @SaCheckPermission("tooldict:main:edit")
    @PutMapping
    public R<Long> put(@RequestBody ToolDictMain main) {
        service.update(main);
        return R.ok(null,main.getId());
    }

    /**
     * 字典删除
     * @param ids 字典ID串
     * @return 字典数量
     */
    @Log(title = "字典管理", businessType = BusinessType.DELETE)
    @SaCheckPermission("tooldict:main:delete")
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable Long[] ids) {
        return R.ok(service.delete(ids));
    }

}
