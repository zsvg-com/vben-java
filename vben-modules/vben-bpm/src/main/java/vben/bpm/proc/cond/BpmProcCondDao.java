package vben.bpm.proc.cond;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;

@Component
@RequiredArgsConstructor
public class BpmProcCondDao {

    public BpmProcCond findById(Long id) {
        String sql = "select * from bpm_proc_cond where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(BpmProcCond.class), id);
    }

    public void insert(BpmProcCond main) {
        Isqler isqler = new Isqler("bpm_proc_cond");
        if(main.getId()==null){
            main.setId(IdUtils.getSnowflakeNextIdStr());
        }
        isqler.add("id", main.getId());
        isqler.add("cond", main.getCond());
        jdbcHelper.getTp().update(isqler.getSql(), isqler.getParams());
    }

    public void update(BpmProcCond main) {
        Usqler usqler = new Usqler("bpm_proc_cond");
        usqler.addWhere("id=?", main.getId());
        usqler.add("name", main.getCond());
        jdbcHelper.getTp().update(usqler.getSql(), usqler.getParams());
    }

    public void deleteById(Long id) {
        jdbcHelper.update("delete from bpm_proc_cond where id=?", id);
    }

    private final JdbcHelper jdbcHelper;

}
