package vben.base.tool.num;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.core.utils.StrUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;

import java.util.Date;

/**
 * 编号管理
 */
@RestController
@RequestMapping("tool/num")
@RequiredArgsConstructor
public class ToolNumApi {

    /**
     * 编号分页查询
     * @param name 编号名称
     * @return 编号分页数据
     */
    @SaCheckPermission("tool:num:query")
    @GetMapping
    public R<PageData> get(String name) {
        Sqler sqler = new Sqler("t.id,t.label,t.name,t.nflag,t.cudat,t.crtim,t.uptim","tool_num");
        sqler.addLike("t.name",name);
        sqler.addSelect("t.nupre,t.nunex,t.numod");
        return R.ok(service.findPageData(sqler)) ;
    }

    /**
     * 编号详情查询
     * @param id 编号ID
     * @return 编号对象
     */
    @SaCheckPermission("tool:num:query")
    @GetMapping("info/{id}")
    public R<ToolNum> info(@PathVariable String id) {
        ToolNum main=service.findById(id);
        return R.ok(main);
    }

    /**
     * 编号新增
     * @param main 编号对象
     * @return 编号ID
     */
    @SaCheckPermission("tool:num:edit")
    @PostMapping
    public synchronized R<String> post(@RequestBody ToolNum main) {
        if(service.existsById(main.getId())){
            return R.fail(201,"编号已存在，请修改编号");
        }
        main.setNflag(true);
        service.insert(main);
        return R.ok(null,main.getId());
    }

    /**
     * 编号修改
     * @param main 编号对象
     * @return 编号ID
     */
    @SaCheckPermission("tool:num:edit")
    @PutMapping
    public R<String> pust(@RequestBody ToolNum main) {
        if(StrUtils.isBlank(main.getNunex())){
            main.setNflag(true);
        }
        main.setUptim(new Date());
        service.update(main);
        return R.ok(null,main.getId());
    }

    /**
     * 编号删除
     * @param ids 编号ID串
     * @return 编号数量
     */
    @SaCheckPermission("tool:num:delete")
    @DeleteMapping("{ids}")
    public R<Integer> delete(@PathVariable String[] ids) {
        return R.ok(service.delete(ids));
    }

    private final ToolNumService service;
}
