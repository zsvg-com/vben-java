package vben.bpm.role.cate;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.Smove;
import vben.common.jdbc.dto.Stree;

import java.util.List;

@RestController
@RequestMapping("bpm/role/cate")
@RequiredArgsConstructor
public class BpmRoleCateApi {

    //查询分类树
    @GetMapping("tree")
    public R<List<Stree>> tree(String id) {
        return R.ok(service.findTreeList(id));
    }

    //查询分类详情
    @GetMapping("info/{id}")
    public R<BpmRoleCate> info(@PathVariable String id) {
        BpmRoleCate cate = service.findById(id);
        return R.ok(cate);
    }

    //新增分类
    @PostMapping
    public R<String> post(@RequestBody BpmRoleCate cate) {
        service.insert(cate);
        return R.ok(null,cate.getId());
    }

    //更新分类
    @PutMapping
    public R<String> put(@RequestBody BpmRoleCate cate) {
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

    private final BpmRoleCateService service;
}
