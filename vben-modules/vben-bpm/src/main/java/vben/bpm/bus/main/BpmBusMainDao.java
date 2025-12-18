package vben.bpm.bus.main;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.jdbc.sqler.Usqler;

@Component
@RequiredArgsConstructor
public class BpmBusMainDao {

    public PageData findPageData(Sqler sqler) {
        return jdbcHelper.findPageData(sqler);
    }

    public BpmBusMain findById(Long id) {
        String sql = "select * from bpm_bus_main where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(BpmBusMain.class), id);
    }

    public void insert(BpmBusMain main) {
        Isqler sqler = new Isqler("bpm_bus_main");
        sqler.add("id", main.getId());
        sqler.add("name", main.getName());
        sqler.add("avtag", main.getAvtag());
        sqler.add("cruid", main.getCruid());
        sqler.add("crtim", main.getCrtim());
        sqler.add("upuid", main.getCruid());
        sqler.add("uptim", main.getCrtim());
        sqler.add("notes", main.getNotes());
        sqler.add("tmpid", main.getTmpid());
        sqler.add("state", main.getState());
        sqler.add("fdata", main.getFdata());
        sqler.add("prdid", main.getPrdid());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void update(BpmBusMain main)  {
        Usqler sqler = new Usqler("bpm_bus_main");
        sqler.addWhere("id=?", main.getId());
        sqler.add("name", main.getName());
        sqler.add("avtag", main.getAvtag());
        sqler.add("cruid", main.getCruid());
        sqler.add("crtim", main.getCrtim());
        sqler.add("upuid", main.getUpuid());
        sqler.add("uptim", main.getUptim());
        sqler.add("notes", main.getNotes());
        sqler.add("tmpid", main.getTmpid());
        sqler.add("state", main.getState());
        sqler.add("fdata", main.getFdata());
        sqler.add("prdid", main.getPrdid());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void deleteById(Long id) {
        String sql = "delete from bpm_bus_main where id=?";
        jdbcHelper.getTp().update(sql, id);
    }


    public void updateState(BpmBusMain main)  {
        Usqler sqler = new Usqler("bpm_bus_main");
        sqler.addWhere("id=?", main.getId());
        sqler.add("state", main.getState());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    private final JdbcHelper jdbcHelper;

}
