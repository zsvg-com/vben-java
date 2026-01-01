package vben.base.sys.group;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.core.utils.StrUtils;
import vben.common.jdbc.dto.Stree;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SysGroupCateDao {

    public List<Stree> findTreeList(String id) {
        if(StrUtils.isBlank(id)) {
            return jdbcHelper.findStreeList("select id,name,pid from sys_group_cate order by ornum");
        }else{
            return jdbcHelper.findStreeList("select id,name,pid from sys_group_cate where id <> ? order by ornum",id);
        }
    }

    public SysGroupCate findById(String id) {
        String sql = "select * from sys_group_cate where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(SysGroupCate.class), id);
    }

    public void insert(SysGroupCate cate) {
        Isqler isqler = new Isqler("sys_group_cate");
        isqler.add("id", cate.getId());
        isqler.add("name", cate.getName());
        isqler.add("pid", cate.getPid());
        isqler.add("ornum", cate.getOrnum());
        jdbcHelper.getTp().update(isqler.getSql(), isqler.getParams());
    }

    public void update(SysGroupCate cate) {
        Usqler usqler = new Usqler("sys_group_cate");
        usqler.addWhere("id=?", cate.getId());
        usqler.add("name", cate.getName());
        usqler.add("ornum", cate.getOrnum());
        usqler.add("pid", cate.getPid());
        jdbcHelper.getTp().update(usqler.getSql(), usqler.getParams());
    }

    public void deleteById(String id) {
        String sql = "delete from sys_group_cate where id=?";
        jdbcHelper.getTp().update(sql, id);
    }

    public Integer getCount(String pid){
        String countSql="select count(1) from sys_group_cate where pid=?";
        Integer count = jdbcHelper.getTp().queryForObject(countSql,Integer.class,pid);
        if(count==null){
            count=0;
        }
        return count;
    }


    private final JdbcHelper jdbcHelper;
}
