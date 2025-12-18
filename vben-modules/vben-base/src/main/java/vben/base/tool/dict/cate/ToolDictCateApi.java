package vben.base.tool.dict.cate;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.SidName;

import java.util.List;

/**
 * 字典分类管理
 */
@RestController
@RequestMapping("tool/dict/cate")
@RequiredArgsConstructor
public class ToolDictCateApi {

    private final ToolDictCateService service;

    /**
     * 字典分类列表查询
     * @return 分类列表
     */
    @GetMapping("list")
    public R<List<SidName>> getList() {
        return R.ok(service.findList());
    }

    /**
     * 字典分类详情查询
     * @param id 分类ID
     * @return 分类对象
     */
    @GetMapping("info/{id}")
    public R<ToolDictCate> info(@PathVariable Long id) {
        ToolDictCate main = service.findById(id);
        return R.ok(main);
    }

    /**
     * 字典分类新增
     * @param main 分类对象
     * @return 分类ID
     */
    @PostMapping
    public R<Long> post(@RequestBody ToolDictCate main) {
        service.insert(main);
        return R.ok(null,main.getId());
    }

    /**
     * 字典分类修改
     * @param main 分类对象
     * @return 分类ID
     */
    @PutMapping
    public R<Long> put(@RequestBody ToolDictCate main) {
        service.update(main);
        return R.ok(null,main.getId());
    }

    /**
     * 字典分类删除
     * @param ids 分类ID串
     * @return
     */
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable Long[] ids) {
        return R.ok(service.delete(ids));
    }

}
