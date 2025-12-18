package vben.bpm.bus.cate;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.Stree;
import vben.common.jdbc.sqler.Sqler;

import java.util.List;

/**
 * 流程分类管理
 */
@RestController
@RequestMapping("bpm/bus/cate")
@RequiredArgsConstructor
public class BpmBusCateApi {

    private final BpmBusCateService service;

    /**
     * 流程分类列表查询
     * @param name 分类名称
     * @return 分类列表集合
     */
    @GetMapping("list")
    public R<List<BpmBusCate>> list(String name) {
        Sqler sqler = new Sqler("t.*","bpm_bus_cate");
        //sqler.addSelect("t.pid,t.notes,t.crtim,t.uptim,t.ornum");
        sqler.addLike("t.name", name);
        sqler.addOrder("t.ornum");
        return R.ok(service.findList(sqler));
    }

    /**
     * 流程分类树状查询
     * @param id 流程分类ID，如传入则查询出来的树结构会排除自身及子节点
     * @return
     */
    @GetMapping("tree")
    public R<List<Stree>> tree(Long id) {
        return R.ok(service.findTree(id));
    }

    /**
     * 流程分类详情查询
     * @param id 流程分类ID
     * @return 流程分类对象
     */
    @GetMapping("info/{id}")
    public R<BpmBusCate> info(@PathVariable Long id) {
        BpmBusCate main = service.findById(id);
        return R.ok(main);
    }

    /**
     * 流程分类新增
     * @param main 流程分类对象
     * @return 流程分类ID
     */
    @PostMapping
    public R<Long> post(@RequestBody BpmBusCate main) {
        if(main.getPid()==null){
            main.setPid(0L);
        }
        service.insert(main);
        return R.ok(null,main.getId());
    }

    /**
     * 流程分类修改
     * @param main 流程分类对象
     * @return 流程分类ID
     */
    @PutMapping
    public R<Long> put(@RequestBody BpmBusCate main) {
        if(main.getPid()==null){
            main.setPid(0L);
        }
        service.update(main);
        return R.ok(null,main.getId());
    }

    /**
     * 流程分类删除
     * @param ids 流程分类ID串
     * @return 流程分类数量
     */
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable Long[] ids) {
        return R.ok(service.delete(ids));
    }

}
