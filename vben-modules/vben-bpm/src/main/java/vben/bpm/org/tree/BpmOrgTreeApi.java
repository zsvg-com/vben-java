package vben.bpm.org.tree;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;

@RestController
@RequestMapping("bpm/org/tree")
@RequiredArgsConstructor
public class BpmOrgTreeApi {

    @GetMapping
    public R get(String name,String catid) {
        Sqler sqler = new Sqler("bpm_org_tree");
        sqler.addLike("t.name", name);
        sqler.addEqual("t.catid", catid);
        sqler.addSelect("t.notes,t.ornum,t.crtim,t.uptim");
        sqler.addOrder("t.ornum");
        return R.ok(jdbcHelper.findPageData(sqler));
    }

    @GetMapping("info/{id}")
    public R info(@PathVariable String id) {
        BpmOrgTree main = service.findById(id);
        return R.ok(main);
    }

    @PostMapping
    public R post(@RequestBody BpmOrgTree main) {
        return R.ok(service.insert(main));
    }

    @PutMapping
    public R put(@RequestBody BpmOrgTree main) {
        return R.ok(service.update(main));
    }

    @DeleteMapping("{ids}")
    public R delete(@PathVariable String[] ids) {
        return R.ok(service.delete(ids));
    }

    @GetMapping("calc")
    public R calc(String useid, String rolid) {
        return R.ok(service.calc(useid, rolid));
    }

    @GetMapping("rlist")
    public R rlist(String treid) {
        String sql = "select id,name from bpm_org_role where treid=?";
        return R.ok(jdbcHelper.getTp().queryForList(sql, treid));
    }

    private final BpmOrgTreeService service;

    private final JdbcHelper jdbcHelper;

}
