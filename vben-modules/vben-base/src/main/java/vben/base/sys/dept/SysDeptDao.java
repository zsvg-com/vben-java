package vben.base.sys.dept;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.core.utils.StrUtils;
import vben.common.jdbc.dto.Stree;
import vben.common.jdbc.root.Db;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.jdbc.sqler.Usqler;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SysDeptDao {

    public SysDept findById(String id) {
        String sql = "select * from sys_dept where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(SysDept.class), id);
    }

    public void insert(SysDept sysDept) {
        Isqler sqler = new Isqler("sys_dept");
        sqler.add("id", sysDept.getId());
        sqler.add("name", sysDept.getName());
        sqler.add("type", sysDept.getType());
        sqler.add("pid", sysDept.getPid());
        sqler.add("tier", sysDept.getTier());
        sqler.add("notes", sysDept.getNotes());
        sqler.add("ornum", sysDept.getOrnum());
        sqler.add("crtim", sysDept.getCrtim());
        sqler.add("uptim", sysDept.getCrtim());
        sqler.add("avtag", sysDept.getAvtag());
        sqler.add("label", sysDept.getLabel());
        sqler.add("cruid", sysDept.getCruid());
        sqler.add("upuid", sysDept.getCruid());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void update(SysDept sysDept)  {
        Usqler sqler = new Usqler("sys_dept");
        sqler.addWhere("id=?", sysDept.getId());
        sqler.add("name", sysDept.getName());
        sqler.add("type", sysDept.getType());
        sqler.add("pid", sysDept.getPid());
        sqler.add("tier", sysDept.getTier());
        sqler.add("notes", sysDept.getNotes());
        sqler.add("ornum", sysDept.getOrnum());
        sqler.add("uptim", sysDept.getUptim());
        sqler.add("avtag", sysDept.getAvtag());
        sqler.add("label", sysDept.getLabel());
        sqler.add("upuid", sysDept.getUpuid());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public Integer getCount(String pid) {
        String sql = "select count(1) from sys_dept where pid=?";
        return jdbcHelper.getTp().queryForObject(sql, Integer.class, pid);
    }

    public String findTier(String id) {
        String sql = "select tier from sys_dept where id=?";
        return jdbcHelper.getTp().queryForObject(sql, String.class, id);
    }

    public void deleteById(String id) {
        String sql = "delete from sys_dept where id=?";
        jdbcHelper.getTp().update(sql, id);
    }

    public List<Stree> findTree(String id){
        Sqler sqler = new Sqler("sys_dept");
        sqler.addSelect("pid");
        sqler.addWhere("t.avtag = "+ Db.True);
        if (StrUtils.isNotBlank(id)) {
            sqler.addWhere("t.tier not like ?", "%_" + id + "_%");
//            sqler.addWhere("t.id <> ?", id);
        }
        sqler.addOrder("t.ornum");
        return jdbcHelper.findStreeList(sqler);
    }

    private final JdbcHelper jdbcHelper;

}
