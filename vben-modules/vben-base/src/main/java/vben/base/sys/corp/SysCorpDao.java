package vben.base.sys.corp;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;

@Component
@RequiredArgsConstructor
public class SysCorpDao {

    public SysCorp findById(String id) {
        String sql = "select * from sys_corp where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(SysCorp.class), id);
    }

    public void insert(SysCorp corp) {
        Isqler sqler = new Isqler("sys_corp");
        sqler.add("id", corp.getId());
        sqler.add("name", corp.getName());
        sqler.add("catid", corp.getCatid());
        sqler.add("type", corp.getType());
        sqler.add("notes", corp.getNotes());
        sqler.add("ornum", corp.getOrnum());
        sqler.add("crtim", corp.getCrtim());
        sqler.add("uptim", corp.getCrtim());
        sqler.add("avtag", corp.getAvtag());
        sqler.add("label", corp.getLabel());
        sqler.add("cruid", corp.getCruid());
        sqler.add("upuid", corp.getCruid());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void update(SysCorp corp)  {
        Usqler sqler = new Usqler("sys_corp");
        sqler.addWhere("id=?", corp.getId());
        sqler.add("name", corp.getName());
        sqler.add("catid", corp.getCatid());
        sqler.add("type", corp.getType());
        sqler.add("notes", corp.getNotes());
        sqler.add("ornum", corp.getOrnum());
        sqler.add("uptim", corp.getUptim());
        sqler.add("avtag", corp.getAvtag());
        sqler.add("label", corp.getLabel());
        sqler.add("upuid", corp.getUpuid());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public Integer getCount(String pid) {
        String sql = "select count(1) from sys_corp where pid=?";
        return jdbcHelper.getTp().queryForObject(sql, Integer.class, pid);
    }

    public void deleteById(String id) {
        String sql = "delete from sys_corp where id=?";
        jdbcHelper.getTp().update(sql, id);
    }

    private final JdbcHelper jdbcHelper;

}
