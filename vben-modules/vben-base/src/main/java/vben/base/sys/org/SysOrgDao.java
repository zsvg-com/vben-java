package vben.base.sys.org;

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
public class SysOrgDao {

    public SysOrg findById(String id) {
        String sql = "select * from sys_org where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(SysOrg.class), id);
    }

    public void insert(SysOrg sysOrg) {
        Isqler sqler = new Isqler("sys_org");
        sqler.add("id", sysOrg.getId());
        sqler.add("name", sysOrg.getName());
        sqler.add("type", sysOrg.getType());
        sqler.add("pid", sysOrg.getPid());
        sqler.add("tier", sysOrg.getTier());
        sqler.add("notes", sysOrg.getNotes());
        sqler.add("ornum", sysOrg.getOrnum());
        sqler.add("crtim", sysOrg.getCrtim());
        sqler.add("uptim", sysOrg.getCrtim());
        sqler.add("avtag", sysOrg.getAvtag());
        sqler.add("label", sysOrg.getLabel());
        sqler.add("cruid", sysOrg.getCruid());
        sqler.add("upuid", sysOrg.getCruid());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void update(SysOrg sysOrg)  {
        Usqler sqler = new Usqler("sys_org");
        sqler.addWhere("id=?", sysOrg.getId());
        sqler.add("name", sysOrg.getName());
        sqler.add("type", sysOrg.getType());
        sqler.add("pid", sysOrg.getPid());
        sqler.add("tier", sysOrg.getTier());
        sqler.add("notes", sysOrg.getNotes());
        sqler.add("ornum", sysOrg.getOrnum());
        sqler.add("uptim", sysOrg.getUptim());
        sqler.add("avtag", sysOrg.getAvtag());
        sqler.add("label", sysOrg.getLabel());
        sqler.add("upuid", sysOrg.getUpuid());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public Integer getCount(String pid) {
        String sql = "select count(1) from sys_org where pid=?";
        return jdbcHelper.getTp().queryForObject(sql, Integer.class, pid);
    }

    public String findTier(String id) {
        String sql = "select tier from sys_org where id=?";
        return jdbcHelper.getTp().queryForObject(sql, String.class, id);
    }

    public void deleteById(String id) {
        String sql = "delete from sys_org where id=?";
        jdbcHelper.getTp().update(sql, id);
    }

    public List<Stree> findTree(String id){
        Sqler sqler = new Sqler("sys_org");
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
