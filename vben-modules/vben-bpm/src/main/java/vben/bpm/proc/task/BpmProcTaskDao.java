package vben.bpm.proc.task;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.bpm.root.domain.dto.BpmTaskDto;
import vben.bpm.root.domain.vo.BpmCcInfoVo;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.root.Db;
import vben.common.jdbc.sqler.BaseSqler;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BpmProcTaskDao {

    public List<BpmProcTask> findAllByNodidAndActagOrderByOrnum(Long nodid){
        String sql="select * from bpm_proc_task where nodid = ? and actag="+Db.False+" order by ornum";
        return  jdbcHelper.getTp().query(sql,new BeanPropertyRowMapper<>(BpmProcTask.class),nodid);
    }

    public List<BpmProcTask> findAllByNodidOrderByOrnum(Long nodid){
        String sql="select * from bpm_proc_task where nodid = ? order by ornum";
        return  jdbcHelper.getTp().query(sql, new BeanPropertyRowMapper<>(BpmProcTask.class),nodid);
    }


    public List<BpmTaskDto> findCurrentExmenByProid(Long proid){
        String sql="""
         select t.id,t.exuid,t.nodid,o.name exuna,t.type,n.facno nodno,n.facna nodna
         from bpm_proc_task t
         inner join sys_org o on o.id=t.exuid
         inner join bpm_proc_node n on n.id=t.nodid
         """;
        sql+="where t.proid=? and t.actag="+ Db.True+" order by t.ornum";
        return  jdbcHelper.getTp().query(sql, new BeanPropertyRowMapper<>(BpmTaskDto.class),proid);
    }


    public List<BpmCcInfoVo> findCanCancelCommunitMen(String proid){
        String sql="""
            select o.id,o.name,t.id tasid from bpm_proc_task t
            inner join sys_org o on o.id=t.exuid
            where t.proid=? and t.type='communicate'
            order by t.sttim
            """;
        return  jdbcHelper.getTp().query(sql,new BeanPropertyRowMapper<>(BpmCcInfoVo.class),proid);
    }

    public void deleteAllPyProid(Long proid) {
        jdbcHelper.update("delete from "+table+" where proid=?", proid);
    }

    private static final String table = "bpm_proc_task";

    private void fieldHandle(BaseSqler sqler, BpmProcTask main){
        sqler.add("type", main.getType());
        sqler.add("proid", main.getProid());
        sqler.add("nodid", main.getNodid());
        sqler.add("sttim", main.getSttim());
        sqler.add("notty", main.getNotty());
        sqler.add("state", main.getState());
        sqler.add("ornum", main.getOrnum());
        sqler.add("actag", main.getActag());
        sqler.add("hauid", main.getHauid());
        sqler.add("auuid", main.getAuuid());
        sqler.add("exuid", main.getExuid());
    }

    public BpmProcTask findById(Long id) {
        String sql = "select * from "+table+" where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(BpmProcTask.class), id);
    }

    public void insert(BpmProcTask main) {
        Isqler isqler = new Isqler(table);
        if(main.getId()==null){
            main.setId(IdUtils.getSnowflakeNextId());
        }
        isqler.add("id", main.getId());
        fieldHandle(isqler,main);
        jdbcHelper.getTp().update(isqler.getSql(), isqler.getParams());
    }

    public void update(BpmProcTask main) {
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
