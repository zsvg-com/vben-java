package vben.bpm.proc.task;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.sqler.BaseSqler;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;

@Component
@RequiredArgsConstructor
public class BpmProcTaskHiDao {

    private static final String table = "bpm_proc_task_hi";

    private void fieldHandle(BaseSqler sqler, BpmProcTaskHi main){
        sqler.add("type", main.getType());
        sqler.add("proid", main.getProid());
        sqler.add("nodid", main.getNodid());
        sqler.add("sttim", main.getSttim());
        sqler.add("entim", main.getEntim());
        sqler.add("notty", main.getNotty());
        sqler.add("state", main.getState());
        sqler.add("auuid", main.getAuuid());
        sqler.add("exuid", main.getExuid());
    }

    public BpmProcTaskHi findById(Long id) {
        String sql = "select * from "+table+" where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(BpmProcTaskHi.class), id);
    }

    public void insert(BpmProcTaskHi main) {
        Isqler isqler = new Isqler(table);
        if(main.getId()==null){
            main.setId(IdUtils.getSnowflakeNextId());
        }
        isqler.add("id", main.getId());
        fieldHandle(isqler,main);
        jdbcHelper.getTp().update(isqler.getSql(), isqler.getParams());
    }

    public void update(BpmProcTaskHi main) {
        Usqler usqler = new Usqler(table);
        usqler.addWhere("id=?", main.getId());
        fieldHandle(usqler,main);
        jdbcHelper.getTp().update(usqler.getSql(), usqler.getParams());
    }

    public void deleteById(Long id) {
        jdbcHelper.update("delete from "+table+" where id=?", id);
    }

    private final JdbcHelper jdbcHelper;

}
