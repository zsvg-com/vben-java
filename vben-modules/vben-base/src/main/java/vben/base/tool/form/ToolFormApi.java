package vben.base.tool.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;

/**
 * 在线表单
 */
@RestController
@RequestMapping("tool/form")
public class ToolFormApi {

    /**
     * 查询在线表单分页
     * @param name
     * @return
     */
    @GetMapping
    public R<PageData> get(String name) {
        Sqler sqler = new Sqler("tool_form");
        sqler.addLike("t.name", name);
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 查询在线表单详情
     * @param id
     * @return
     */
    @GetMapping("info/{id}")
    public R<ToolForm> getInfo(@PathVariable Long id) {
        ToolForm main = service.select(id);
        return R.ok(main);
    }

    /**
     * 新增在线表单
     * @param main
     * @return
     */
    @PostMapping
    public R<Long> post(@RequestBody ToolForm main) {
        return R.ok(null,service.insert(main));
    }

    /**
     * 更新在线表单
     * @param main
     * @return
     */
    @PutMapping
    public R<Long> put(@RequestBody ToolForm main) {
        return R.ok(null,service.update(main));
    }

    /**
     * 删除在线表单
     * @param ids
     * @return
     */
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable Long[] ids) {
        return R.ok(service.delete(ids));
    }

    @Autowired
    private ToolFormService service;

}
