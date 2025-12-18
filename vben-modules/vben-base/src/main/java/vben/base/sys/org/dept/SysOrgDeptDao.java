package vben.base.sys.org.dept;

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
public class SysOrgDeptDao {

    public SysOrgDept findById(String id) {
        String sql = "select * from sys_org_dept where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(SysOrgDept.class), id);
    }

    public void insert(SysOrgDept sysOrgDept) {
        Isqler sqler = new Isqler("sys_org_dept");
        sqler.add("id", sysOrgDept.getId());
        sqler.add("name", sysOrgDept.getName());
        sqler.add("type", sysOrgDept.getType());
        sqler.add("pid", sysOrgDept.getPid());
        sqler.add("tier", sysOrgDept.getTier());
        sqler.add("notes", sysOrgDept.getNotes());
        sqler.add("ornum", sysOrgDept.getOrnum());
        sqler.add("crtim", sysOrgDept.getCrtim());
        sqler.add("uptim", sysOrgDept.getCrtim());
        sqler.add("avtag", sysOrgDept.getAvtag());
        sqler.add("label", sysOrgDept.getLabel());
        sqler.add("cruid", sysOrgDept.getCruid());
        sqler.add("upuid", sysOrgDept.getCruid());
        sqler.add("ex1", sysOrgDept.getEx1());
        sqler.add("ex2", sysOrgDept.getEx2());
        sqler.add("ex3", sysOrgDept.getEx3());
        sqler.add("ex4", sysOrgDept.getEx4());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void update(SysOrgDept sysOrgDept)  {
        Usqler sqler = new Usqler("sys_org_dept");
        sqler.addWhere("id=?", sysOrgDept.getId());
        sqler.add("name", sysOrgDept.getName());
        sqler.add("type", sysOrgDept.getType());
        sqler.add("pid", sysOrgDept.getPid());
        sqler.add("tier", sysOrgDept.getTier());
        sqler.add("notes", sysOrgDept.getNotes());
        sqler.add("ornum", sysOrgDept.getOrnum());
        sqler.add("uptim", sysOrgDept.getUptim());
        sqler.add("avtag", sysOrgDept.getAvtag());
        sqler.add("label", sysOrgDept.getLabel());
        sqler.add("upuid", sysOrgDept.getUpuid());
        sqler.add("ex1", sysOrgDept.getEx1());
        sqler.add("ex2", sysOrgDept.getEx2());
        sqler.add("ex3", sysOrgDept.getEx3());
        sqler.add("ex4", sysOrgDept.getEx4());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public Integer getCount(String pid) {
        String sql = "select count(1) from sys_org_dept where pid=?";
        return jdbcHelper.getTp().queryForObject(sql, Integer.class, pid);
    }

    public String findTier(String id) {
        String sql = "select tier from sys_org_dept where id=?";
        return jdbcHelper.getTp().queryForObject(sql, String.class, id);
    }

    public void deleteById(String id) {
        String sql = "delete from sys_org_dept where id=?";
        jdbcHelper.getTp().update(sql, id);
    }

    public List<Stree> findTree(String id){
        Sqler sqler = new Sqler("sys_org_dept");
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
