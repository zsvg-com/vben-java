package vben.bpm.proc.def;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.bpm.root.domain.vo.BpmProcDefVo;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;

@Component
@RequiredArgsConstructor
public class BpmProcDefDao {

    public String selectExxmlById(Long id){
        String sql="SELECT exxml FROM bpm_proc_def WHERE id = ?";
        return jdbcHelper.findString(sql,id);
    }

    public String selectExxmlByProid(Long proid){
        String sql="SELECT f.exxml FROM bpm_proc_def f inner join bpm_proc_inst p on p.prdid=f.id WHERE p.id = ?";
        return jdbcHelper.findString(sql,proid);
    }

    public String selectDixmlById(Long id){
        String sql="SELECT dixml FROM bpm_proc_def WHERE id = ?";
        return jdbcHelper.findString(sql,id);
    }

    public String selectDixmlByProid(Long proid){
        String sql="SELECT f.dixml FROM bpm_proc_def f inner join bpm_proc_inst p on p.prdid=f.id WHERE p.id = ?";
        return jdbcHelper.findString(sql,proid);
    }

    public BpmProcDef findById(Long id) {
        String sql = "select * from bpm_proc_def where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(BpmProcDef.class), id);
    }

    public BpmProcDefVo findVoById(Long id) {
        String sql = "select * from bpm_proc_def where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(BpmProcDefVo.class), id);
    }

    public void insert(BpmProcDef main) {
        Isqler isqler = new Isqler("bpm_proc_def");
        if(main.getId()==null){
            main.setId(IdUtils.getSnowflakeNextId());
        }
        isqler.add("id", main.getId());
        isqler.add("name", main.getName());
        isqler.add("avtag", main.getAvtag());
        isqler.add("cruid", main.getCruid());
        isqler.add("crtim", main.getCrtim());
        isqler.add("uptim", main.getCrtim());
        isqler.add("notes", main.getNotes());
        isqler.add("dixml", main.getDixml());
        isqler.add("exxml", main.getExxml());
        isqler.add("busid", main.getBusid());
        jdbcHelper.getTp().update(isqler.getSql(), isqler.getParams());
    }

    public void update(BpmProcDef main) {
        Usqler usqler = new Usqler("bpm_proc_def");
        usqler.addWhere("id=?", main.getId());
        usqler.add("name", main.getName());
        usqler.add("avtag", main.getAvtag());
        usqler.add("cruid", main.getCruid());
        usqler.add("uptim", main.getUptim());
        usqler.add("notes", main.getNotes());
        usqler.add("dixml", main.getDixml());
        usqler.add("exxml", main.getExxml());
        usqler.add("busid", main.getBusid());
        jdbcHelper.getTp().update(usqler.getSql(), usqler.getParams());
    }

    public void deleteById(Long id) {
        jdbcHelper.update("delete from bpm_proc_def where id=?", id);
    }

    private final JdbcHelper jdbcHelper;

}
