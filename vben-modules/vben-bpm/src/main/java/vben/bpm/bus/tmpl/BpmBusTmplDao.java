package vben.bpm.bus.tmpl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;

@Component
@RequiredArgsConstructor
public class BpmBusTmplDao {



    public BpmBusTmpl findById(Long id) {
        String sql = "select * from bpm_bus_tmpl where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(BpmBusTmpl.class), id);
    }

    public void insert(BpmBusTmpl main) {
        Isqler sqler = new Isqler("bpm_bus_tmpl");
        sqler.add("id", main.getId());
        sqler.add("name", main.getName());
        sqler.add("avtag", main.getAvtag());
        sqler.add("cruid", main.getCruid());
        sqler.add("crtim", main.getCrtim());
        sqler.add("upuid", main.getCruid());
        sqler.add("uptim", main.getCrtim());
        sqler.add("notes", main.getNotes());
        sqler.add("catid", main.getCatid());
        sqler.add("ornum", main.getOrnum());
        sqler.add("prdid", main.getPrdid());
        sqler.add("fodid", main.getFodid());
        sqler.add("fpath", main.getFpath());
        sqler.add("frule", main.getFrule());
        sqler.add("ftype", main.getFtype());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void update(BpmBusTmpl main)  {
        Usqler sqler = new Usqler("bpm_bus_tmpl");
        sqler.addWhere("id=?", main.getId());
        sqler.add("name", main.getName());
        sqler.add("avtag", main.getAvtag());
        sqler.add("upuid", main.getUpuid());
        sqler.add("uptim", main.getUptim());
        sqler.add("notes", main.getNotes());
        sqler.add("catid", main.getCatid());
        sqler.add("ornum", main.getOrnum());
        sqler.add("prdid", main.getPrdid());
        sqler.add("fodid", main.getFodid());
        sqler.add("fpath", main.getFpath());
        sqler.add("frule", main.getFrule());
        sqler.add("ftype", main.getFtype());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void deleteById(Long id) {
        String sql = "delete from bpm_bus_tmpl where id=?";
        jdbcHelper.getTp().update(sql, id);
    }

    private final JdbcHelper jdbcHelper;

}
