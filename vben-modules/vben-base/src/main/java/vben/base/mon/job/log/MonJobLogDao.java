package vben.base.mon.job.log;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class MonJobLogDao {

    public List<MonJobLog> findAll() {
        String sql = "select * from mon_job_log";
        return jdbcHelper.getTp().query(sql, new BeanPropertyRowMapper<>(MonJobLog.class));
    }

    public MonJobLog findById(Long id) {
        String sql = "select * from mon_job_log where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(MonJobLog.class), id);
    }

    public void insert(MonJobLog main) {
        Isqler sqler = new Isqler("mon_job_log");
        sqler.add("id", main.getId());
        sqler.add("name", main.getName());
        sqler.add("sttim", main.getSttim());
        sqler.add("entim", main.getEntim());
        sqler.add("ret", main.getRet());
        sqler.add("msg", main.getMsg());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void update(MonJobLog main) {
        Usqler sqler = new Usqler("mon_job_log");
        sqler.addWhere("id=?", main.getId());
        sqler.add("name", main.getName());
        sqler.add("sttim", main.getSttim());
        sqler.add("entim", main.getEntim());
        sqler.add("ret", main.getRet());
        sqler.add("msg", main.getMsg());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void deleteById(Long id) {
        jdbcHelper.update("delete from mon_job_log where id=?", id);
    }

    public void deleteAll() {
        jdbcHelper.update("delete from mon_job_log");
    }

    private final JdbcHelper jdbcHelper;

}

