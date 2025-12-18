package vben.bpm.org.node;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.web.bind.annotation.*;
import vben.common.core.domain.R;
import vben.common.core.utils.StrUtils;
import vben.common.jdbc.dto.Stree;
import vben.common.jdbc.dto.Smove;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;

import java.util.List;

@RestController
@RequestMapping("bpm/org/node")
@RequiredArgsConstructor
public class BpmOrgNodeApi {

//    @GetMapping
//    public R get(String treid, String name, String pid, String notes) {
//        if(StrUtils.isBlank(treid)){
//            return R.ok(new PageData(0, new ArrayList<>()));
//        }
//        Sqler sqler = new Sqler(table);
//        if (StrUtils.isNotBlank(name)) {
//            sqler.addLike("t.name" , name);
//        } else {
//            if ("".equals(pid)) {
//                sqler.addWhere("t.pid is null");
//            } else if (StrUtils.isNotBlank(pid)) {
//                sqler.addEqual("t.pid" , pid);
//            }
//        }
//        sqler.addLike("t.notes" , notes);
//        sqler.addWhere("t.avtag="+Db.True);
//        sqler.addOrder("t.ornum");
//        sqler.addEqual("t.treid",treid);
//        sqler.addSelect("t.ornum,t.notes,t.pid,t.crtim,t.uptim");
//        sqler.addLeftJoin("o.name memna","sys_org o","o.id=t.memid");
//        return R.ok(jdbcHelper.findPageData(sqler));
//    }
//
//    //查询层级树
//    @GetMapping("treea")
//    public R getTreea(String treid) {
//        Sqler sqler = new Sqler("bpm_org_node");
//        sqler.addSelect("t.pid");
//        sqler.addEqual("t.treid", treid);
//        sqler.addOrder("t.ornum");
//        sqler.addLeftJoin("o.name type","sys_org o","o.id=t.memid");
//
////        List<Stree> list = jdbcDao.findTreeList(sqler);
//        List<Stree> streeList = jdbcHelper.getTp().query(sqler.getSql(),new Object[]{treid},new BeanPropertyRowMapper<>(Stree.class));
//        for (Stree stree : streeList) {
//            if(StrUtils.isBlank(stree.getName())){
//                stree.setName(stree.getType());
//            }else{
//                if(StrUtils.isNotBlank(stree.getType())){
//                    stree.setName(stree.getName()+"("+stree.getType()+")");
//                }
//            }
//        }
//        List<Stree> list= StreeUtils.build(streeList);
//        return R.ok(list);
//    }

    //查询层级树
    @GetMapping("tree")
    public R getTree(String name,String treid,String id) {
        Sqler sqler = new Sqler("bpm_org_node");
        sqler.addSelect("t.pid");
        sqler.addLike("t.name", name);
        sqler.addEqual("t.treid", treid);
        sqler.addOrder("t.ornum");
        if (StrUtils.isNotBlank(id)&&!"undefined".equals(id)) {
            sqler.addWhere("t.tier not like ?", "%_" + id + "_%");
//            sqler.addWhere("t.id <> ?", id);
        }
        List<Stree> list = jdbcHelper.findStreeList(sqler);
        return R.ok(list);
    }

    @GetMapping("list")
    public R<List<BpmOrgNode>> list(String name) {
        Sqler sqler = new Sqler("bpm_org_node");
        sqler.addSelect("t.pid,t.notes,t.crtim,t.uptim,t.ornum,t.tier");
        sqler.addLike("t.name", name);
        sqler.addOrder("t.ornum");
        sqler.addLeftJoin("o.name memna","sys_org o","o.id=t.memid");
        List<BpmOrgNode> list = jdbcHelper.getTp().query(sqler.getSql(), sqler.getParams(),
            new BeanPropertyRowMapper<>(BpmOrgNode.class));
        return R.ok(list);
    }

    //排除了自己
//    @GetMapping("listn")
//    public R listn(String name, String id) {
//        Sqler sqler = new Sqler(table);
//        sqler.addLike("t.name", name);
//        sqler.addOrder("t.ornum");
//        sqler.addSelect("t.pid");
//        return R.ok(service.findWithoutItself(sqler, id));
//    }

    @GetMapping("info/{id}")
    public R info(@PathVariable String id, HttpServletRequest request) {
        BpmOrgNode main = service.findById(id);
        return R.ok(main);
    }

    @PostMapping
    public R post(@RequestBody BpmOrgNode main) {
        main.setOrnum(service.getCount(main.getPid(),main.getTreid())+1);
        service.insert(main);
        return R.ok(main.getId());
    }

    @PutMapping
    public R put(@RequestBody BpmOrgNode main) throws Exception {
        service.update(main);
        return R.ok(main.getId());
    }

    @DeleteMapping("{ids}")
    public R delete(@PathVariable String[] ids) {
        return R.ok(service.delete(ids));
    }

    @PostMapping("move")
    public R move(@RequestBody Smove bo) throws Exception {
        service.move(bo);
        return R.ok();
    }

    private final JdbcHelper jdbcHelper;

    private final BpmOrgNodeService service;

}
