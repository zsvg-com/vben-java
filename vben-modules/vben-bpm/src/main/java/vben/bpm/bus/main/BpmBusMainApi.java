package vben.bpm.bus.main;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;

/**
 * 流程记录管理
 */
@RestController
@RequestMapping("bpm/bus/main")
@RequiredArgsConstructor
public class BpmBusMainApi {

    private final BpmBusMainService service;

    /**
     * 流程分页查询
     * @param name 流程主题
     * @return 分页数据
     */
    @SaCheckPermission("bpmbus:main:query")
    @GetMapping
    public R<PageData> get(String name) {
        Sqler sqler = new Sqler("bpm_bus_main");
        sqler.addLike("t.name", name);
        sqler.addSelect("t.state,t.notes");
        sqler.addDescOrder("t.crtim");
        sqler.selectCUinfo();
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 流程详情查询
     * @param id 流程ID
     * @return 流程对象
     */
    @SaCheckPermission("bpmbus:main:query")
    @GetMapping("info/{id}")
    public R<BpmBusMain> info(@PathVariable Long id) {
        BpmBusMain main = service.findById(id);
        return R.ok(main);
    }

    /**
     * 流程新增
     * @param main 流程对象
     * @return 流程ID
     */
    @SaCheckPermission("bpmbus:main:add")
    @PostMapping
    public R<Long> post(@RequestBody BpmBusMain main) {
        return R.ok(null,service.insert(main));
    }

    /**
     * 流程修改
     * @param main 流程对象
     * @return 流程ID
     */
    @SaCheckPermission("bpmbus:main:edit")
    @PutMapping
    public R<Long> put(@RequestBody BpmBusMain main) throws Exception {
        return R.ok(null,service.update(main));
    }

    /**
     * 流程删除
     * @param ids 流程ID串
     * @return 流程数量
     */
    @SaCheckPermission("bpmbus:main:delete")
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable Long[] ids) {
        return R.ok(service.delete(ids));
    }

}
