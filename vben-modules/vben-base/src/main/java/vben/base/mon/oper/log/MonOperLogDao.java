package vben.base.mon.oper.log;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;


@Component
@RequiredArgsConstructor
public class MonOperLogDao {

    public PageData findPageData(Sqler sqler) {
        return jdbcHelper.findPageData(sqler);
    }

    public MonOperLog findById(Long id) {
        String sql = "select * from mon_oper_log where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(MonOperLog.class), id);
    }

    public void insert(MonOperLog main) {
        Isqler isqler = new Isqler("mon_oper_log");
        isqler.add("id", main.getId());
        isqler.add("tenid", main.getTenid());
        isqler.add("opmod", main.getOpmod());
        isqler.add("butyp", main.getButyp());
        isqler.add("remet", main.getRemet());
        isqler.add("reway", main.getReway());
        isqler.add("optyp", main.getOptyp());
        isqler.add("opuna", main.getOpuna());
        isqler.add("opdna", main.getOpdna());
        isqler.add("reurl", main.getReurl());
        isqler.add("opip", main.getOpip());
        isqler.add("oploc", main.getOploc());
        isqler.add("repar", main.getRepar());
        isqler.add("bapar", main.getBapar());
        isqler.add("sutag", main.getSutag());
        isqler.add("ermsg", main.getErmsg());
        isqler.add("optim", main.getOptim());
        isqler.add("cotim", main.getCotim());
        jdbcHelper.getTp().update(isqler.getSql(), isqler.getParams());
    }


    public void deleteById(Long id) {
        jdbcHelper.update("delete from mon_oper_log where id=?", id);
    }

    private final JdbcHelper jdbcHelper;

}

