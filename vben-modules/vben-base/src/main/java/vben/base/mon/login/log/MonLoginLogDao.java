package vben.base.mon.login.log;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;


@Component
@RequiredArgsConstructor
public class MonLoginLogDao {

    public PageData findPageData(Sqler sqler) {
        return jdbcHelper.findPageData(sqler);
    }

    public MonLoginLog findById(Long id) {
        String sql = "select * from mon_login_log where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(MonLoginLog.class), id);
    }

    public void insert(MonLoginLog main) {
        Isqler isqler = new Isqler("mon_login_log");
        isqler.add("id", main.getId());
        isqler.add("tenid", main.getTenid());
        isqler.add("usnam", main.getUsnam());
        isqler.add("clkey", main.getClkey());
        isqler.add("detyp", main.getDetyp());
        isqler.add("sutag", main.getSutag());
        isqler.add("loip", main.getLoip());
        isqler.add("loloc", main.getLoloc());
        isqler.add("browser", main.getBrowser());
        isqler.add("os", main.getOs());
        isqler.add("himsg", main.getHimsg());
        isqler.add("lotim", main.getLotim());
        jdbcHelper.getTp().update(isqler.getSql(), isqler.getParams());
    }


    public void deleteById(Long id) {
        jdbcHelper.update("delete from mon_login_log where id=?", id);
    }

    private final JdbcHelper jdbcHelper;

}

