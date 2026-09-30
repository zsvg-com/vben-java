package vben.bpm.todo.main;

import lombok.RequiredArgsConstructor;
import org.dom4j.DocumentException;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;

/**
 * 流程待办管理
 */
@RestController
@RequestMapping("bpm/todo")
@RequiredArgsConstructor
public class BpmTodoApi {

    private final BpmTodoService service;

    /**
     * 流程待办分页查询
     * @param name 流程主题
     * @return 分页数据
     */
    @GetMapping
    public R<PageData> get(String name) {
        Sqler sqler = new Sqler("bpm_todo");
        sqler.addLike("t.name", name);
        sqler.addOrder("t.crtim desc");
        sqler.addSelect("t.link,t.crtim");
        sqler.addInnerJoin("","bpm_todo_user t2", "t2.todid=t.id");
        sqler.addInnerJoin("a.name as hauna","sys_actor a", "a.id=t2.useid");
        sqler.addInnerJoin("a2.name as cruna","sys_actor a2", "a2.id=t.cruid");
        System.out.println(sqler.getSql());
        return R.ok(service.findPageData(sqler));
    }

    /**
     * 待办详情查询
     * @param id 待办ID
     * @return 待办对象
     */
    @GetMapping("info/{id}")
    public R<BpmTodo> info(@PathVariable String id) {
        BpmTodo main = service.findOne(id);
        return R.ok(main);
    }

    /**
     * 待办新增
     * @param main 待办对象
     * @return 待办ID
     * @throws DocumentException
     */
    @PostMapping
    public R<String> post(@RequestBody BpmTodo main) throws DocumentException {
        return R.ok(null,service.insert(main));
    }

    /**
     * 待办修改
     * @param main 待办对象
     * @return 待办ID
     */
    @PutMapping
    public R<String> put(@RequestBody BpmTodo main) {
        return R.ok(null,service.update(main));
    }

    /**
     * 待办删除
     * @param ids 待办ID串
     * @return 待办数量
     */
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable String[] ids) {
        return R.ok(service.delete(ids));
    }

}
