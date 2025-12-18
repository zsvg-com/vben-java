package vben.base.mon.job.main;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.jdbc.sqler.Usqler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class MonJobMainDao {

    public PageData findPageData(Sqler sqler) {
        return jdbcHelper.findPageData(sqler);
    }

    public List<MonJobMain> findAll() {
        String sql = "select * from mon_job_main";
        return jdbcHelper.getTp().query(sql, new BeanPropertyRowMapper<>(MonJobMain.class));
    }

    public MonJobMain findById(Long id) {
        String sql = "select * from mon_job_main where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(MonJobMain.class), id);
    }

    public void insert(MonJobMain main) {
        Isqler sqler = new Isqler("mon_job_main");
        sqler.add("id", main.getId());
        sqler.add("name", main.getName());
        sqler.add("code", main.getCode());
        sqler.add("retyp", main.getRetyp());
        sqler.add("reurl", main.getReurl());
        sqler.add("avtag", main.getAvtag());
        sqler.add("ornum", main.getOrnum());
        sqler.add("crtim", main.getCrtim());
        sqler.add("cron", main.getCron());
        sqler.add("notes", main.getNotes());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void update(MonJobMain main) {
        Usqler sqler = new Usqler("mon_job_main");
        sqler.addWhere("id=?", main.getId());
        sqler.add("name", main.getName());
        sqler.add("code", main.getCode());
        sqler.add("retyp", main.getRetyp());
        sqler.add("reurl", main.getReurl());
        sqler.add("avtag", main.getAvtag());
        sqler.add("ornum", main.getOrnum());
        sqler.add("cron", main.getCron());
        sqler.add("notes", main.getNotes());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void deleteById(Long id) {
        jdbcHelper.update("delete from mon_job_main where id=?", id);
    }

    private final JdbcHelper jdbcHelper;

}

