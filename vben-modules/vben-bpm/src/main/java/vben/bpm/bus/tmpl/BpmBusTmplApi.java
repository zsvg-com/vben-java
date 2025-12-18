package vben.bpm.bus.tmpl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.dto.SidName;
import vben.common.jdbc.dto.Stree;
import vben.common.jdbc.sqler.Sqler;

import java.util.List;

/**
 * 流程模板
 */
@RestController
@RequestMapping("bpm/bus/tmpl")
@RequiredArgsConstructor
public class BpmBusTmplApi {

    private final BpmBusTmplService service;

    /**
     * 流程模板树状查询
     * @return
     */
    @GetMapping("tree")
    public R<List<Stree>> tree() {
        List<Stree> list = service.findTreeList();
        return R.ok(list);
    }

    /**
     * 流程模板分页查询
     * @param name 模板名称
     * @return 分页数据
     */
    @GetMapping
    public R<PageData> get(String name) {
        Sqler sqler = new Sqler("bpm_bus_tmpl");
        sqler.addLike("t.name", name);
        sqler.addOrder("t.ornum");
        sqler.selectCUinfo();
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 流程模板列表查询
     * @param catid 流程分类ID
     * @return 列表集合
     */
    @GetMapping("list")
    public R<List<SidName>> list(Long catid) {
        Sqler sqler = new Sqler("bpm_bus_tmpl");
        sqler.addEqual("t.catid", catid);
        sqler.addOrder("t.ornum");
        return R.ok(service.findIdNameList(sqler));
    }

    /**
     * 流程模板详情查询
     * @param id 流程模板ID
     * @return
     */
    @GetMapping("info/{id}")
    public R<BpmBusTmpl> info(@PathVariable Long id) {
        BpmBusTmpl main = service.findById(id);
        return R.ok(main);
    }

    /**
     * 流程模板新增
     * @param main 流程模板对象
     * @return 流程模板ID
     */
    @PostMapping
    public R<Long> post(@RequestBody BpmBusTmpl main) {
        return R.ok(null,service.insert(main));
    }

    /**
     * 流程模板修改
     * @param main 流程模板对象
     * @return 流程模板ID
     */
    @PutMapping
    public R<Long> put(@RequestBody BpmBusTmpl main) {
        return R.ok(null,service.update(main));
    }

    /**
     * 流程模板删除
     * @param ids 流程模板ID
     * @return
     */
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable Long[] ids) {
        return R.ok(service.delete(ids));
    }


}
