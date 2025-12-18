package vben.bpm.proc.node;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.sqler.BaseSqler;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BpmProcNodeHiDao {

    public List<String> findFacnoListByProid(Long proid){
        String sql="select distinct t.facno id from bpm_proc_node_hi t where proid= ?";
        return jdbcHelper.findSlist(sql, proid);
    }

    private static final String table = "bpm_proc_node_hi";

    private void fieldHandle(BaseSqler sqler, BpmProcNodeHi main){
        sqler.add("facno", main.getFacno());
        sqler.add("facna", main.getFacna());
        sqler.add("flway", main.getFlway());
        sqler.add("tarno", main.getTarno());
        sqler.add("tarna", main.getTarna());
        sqler.add("proid", main.getProid());
        sqler.add("state", main.getState());
        sqler.add("sttim", main.getSttim());
        sqler.add("entim", main.getEntim());
    }

    public BpmProcNodeHi findById(Long id) {
        String sql = "select * from "+table+" where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(BpmProcNodeHi.class), id);
    }

    public void insert(BpmProcNodeHi main) {
        Isqler isqler = new Isqler(table);
        if(main.getId()==null){
            main.setId(IdUtils.getSnowflakeNextId());
        }
        isqler.add("id", main.getId());
        fieldHandle(isqler,main);
        jdbcHelper.getTp().update(isqler.getSql(), isqler.getParams());
    }

    public void update(BpmProcNodeHi main) {
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
