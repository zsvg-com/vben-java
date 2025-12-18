package vben.bpm.org.treec;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.Smove;
import vben.common.jdbc.dto.Stree;

import java.util.List;

@RestController
@RequestMapping("bpm/org/treec")
@RequiredArgsConstructor
public class BpmOrgTreeCateApi {

    //查询分类树
    @GetMapping("tree")
    public R<List<Stree>> tree(String id) {
        return R.ok(service.findTreeList(id));
    }

    //查询分类详情
    @GetMapping("info/{id}")
    public R<BpmOrgTreeCate> info(@PathVariable String id) {
        BpmOrgTreeCate cate = service.findById(id);
        return R.ok(cate);
    }

    //新增分类
    @PostMapping
    public R<String> post(@RequestBody BpmOrgTreeCate cate) {
        service.insert(cate);
        return R.ok(null,cate.getId());
    }

    //更新分类
    @PutMapping
    public R<String> put(@RequestBody BpmOrgTreeCate cate) {
        service.update(cate);
        return R.ok(null,cate.getId());
    }

    //删除分类
    @DeleteMapping("{id}")
    public R<Integer> delete(@PathVariable String id) {
        return R.ok(service.delete(id));
    }

    //移动分类
    @PostMapping("move")
    public R move(@RequestBody Smove bo) {
        service.move(bo);
        return R.ok();
    }

    private final BpmOrgTreeCateService service;
}
