package vben.bpm.bus.leave;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;

/**
 * 请假申请
 */
@RestController
@RequestMapping("bpm/bus/leave")
@RequiredArgsConstructor
public class BpmBusLeaveApi {

    private final BpmBusLeaveService service;

    /**
     * 请假分页查询
     * @param name 请假主题
     * @return 分页数据
     */
    @GetMapping
    public R<PageData> get(String name) {
        Sqler sqler = new Sqler("bpm_bus_leave");
        sqler.addLike("t.name", name);
        sqler.addSelect("t.notes");
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 请假详情查询
     * @param id 请假ID
     * @return 请假对象
     */
    @GetMapping("info/{id}")
    public R<BpmBusLeave> info(@PathVariable Long id) {
        BpmBusLeave main = service.findById(id);
        return R.ok(main);
    }

    /**
     * 请假新增
     * @param main 请假对象
     * @return 请假ID
     */
    @PostMapping
    public R<Long> post(@RequestBody BpmBusLeave main) {
        return R.ok(null,service.insert(main));
    }

    /**
     * 请假修改
     * @param main 请假对象
     * @return 请假ID
     */
    @PutMapping
    public R<Long> put(@RequestBody BpmBusLeave main) throws Exception {
        return R.ok(null,service.update(main));
    }

    /**
     * 请假删除
     * @param ids 请假ID串
     * @return 请假数量
     */
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable Long[] ids) {
        return R.ok(service.delete(ids));
    }

}
