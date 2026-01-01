package vben.base.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vben.common.core.domain.R;
import vben.common.jdbc.dto.SidName;
import vben.common.jdbc.dto.Stree;
import vben.common.jdbc.sqler.JdbcHelper;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("pub/org/group")
@RequiredArgsConstructor
public class PubOrgGroupApi {

    @GetMapping("tree")
    public R<List<Stree>> getTree(String name) {
        String sql="select id,pid,name,'cate' type from sys_group_cate " +
                "union all select id,catid as pid,name,'group' type from sys_group";
        List<Stree> list = jdbcHelper.findStreeList(sql);
        return R.ok(list);
    }

    @GetMapping("list")
    public R<List<SidName>> getList(String pid,String type,String name) {
        if("cate".equals(type)){
            String sql="select id,name from sys_group where catid=? order by ornum";
            return R.ok(jdbcHelper.findSidNameList(sql, pid));
        }else if("group".equals(type)){
            String sql="select id,name from sys_group where id=? order by ornum";
            List<SidName> list = jdbcHelper.findSidNameList(sql, pid);
            String sql2="select t.id,t.name from sys_org t inner join sys_group_org o on o.oid=t.id where o.gid=?";
            list.addAll(jdbcHelper.findSidNameList(sql2, pid));
            return R.ok(list);
        }else{
            return R.ok(new ArrayList<>());
        }
    }


    private final JdbcHelper jdbcHelper;


}
