package vben.bpm.proc.inst;

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
public class BpmProcInstDao {

    public void updateEndState(Long id){
        String sql="update bpm_proc_inst set state='30' where id=?";
        jdbcHelper.update(sql,id);
    }

    private static final String table = "bpm_proc_inst";

    private void fieldHandle(BaseSqler sqler, BpmProcInst main){
        sqler.add("name", main.getName());
        sqler.add("avtag", main.getAvtag());
        sqler.add("notes", main.getNotes());
        sqler.add("busid", main.getBusid());
        sqler.add("busty", main.getBusty());
        sqler.add("prdid", main.getPrdid());
        sqler.add("state", main.getState());
    }

    public BpmProcInst findById(Long id) {
        String sql = "select * from "+table+" where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(BpmProcInst.class), id);
    }

    public void insert(BpmProcInst main) {
        Isqler sqler = new Isqler(table);
        if(main.getId()==null){
            main.setId(IdUtils.getSnowflakeNextId());
        }
        sqler.add("id", main.getId());
        sqler.add("crtim", main.getCrtim());
        sqler.add("uptim", main.getCrtim());
        sqler.add("cruid", main.getCruid());
        sqler.add("upuid", main.getCruid());
        fieldHandle(sqler,main);
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void update(BpmProcInst main) {
        Usqler usqler = new Usqler(table);
        usqler.addWhere("id=?", main.getId());
        usqler.add("uptim", main.getUptim());
        usqler.add("upuid", main.getUpuid());
        fieldHandle(usqler,main);
        jdbcHelper.getTp().update(usqler.getSql(), usqler.getParams());
    }

    public void deleteById(Long id) {
        jdbcHelper.update("delete from "+table+" where id=?", id);
    }

    private final JdbcHelper jdbcHelper;

}
