package vben.bpm.proc.node;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;

@Component
@RequiredArgsConstructor
public class BpmProcNodeDao {

    public BpmProcNode findById(Long id) {
        String sql = "select * from bpm_proc_node where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(BpmProcNode.class), id);
    }

    public void insert(BpmProcNode main) {
        Isqler isqler = new Isqler("bpm_proc_node");
        if(main.getId()==null){
            main.setId(IdUtils.getSnowflakeNextId());
        }
        isqler.add("id", main.getId());
        isqler.add("facno", main.getFacno());
        isqler.add("facna", main.getFacna());
        isqler.add("facty", main.getFacty());
        isqler.add("flway", main.getFlway());
        isqler.add("proid", main.getProid());
        isqler.add("state", main.getState());
        isqler.add("sttim", main.getSttim());
        jdbcHelper.getTp().update(isqler.getSql(), isqler.getParams());
    }

    public void update(BpmProcNode main) {
        Usqler usqler = new Usqler("bpm_proc_node");
        usqler.addWhere("id=?", main.getId());
        usqler.add("facno", main.getFacno());
        usqler.add("facna", main.getFacna());
        usqler.add("facty", main.getFacty());
        usqler.add("flway", main.getFlway());
        usqler.add("proid", main.getProid());
        usqler.add("state", main.getState());
        usqler.add("sttim", main.getSttim());
        jdbcHelper.getTp().update(usqler.getSql(), usqler.getParams());
    }

    public void deleteById(Long id) {
        jdbcHelper.update("delete from bpm_proc_node where id=?", id);
    }

    private final JdbcHelper jdbcHelper;

}
