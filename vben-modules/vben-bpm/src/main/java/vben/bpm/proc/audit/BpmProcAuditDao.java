package vben.bpm.proc.audit;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;
import vben.bpm.root.domain.vo.BpmAuditVo;
import vben.bpm.root.domain.vo.BpmRefuseInfoVo;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BpmProcAuditDao {

    public List<BpmAuditVo> findListByProid(Long proid){
        String sql="select t.id,t.crtim,t.facna,t.facno,t.opnot,t.opinf,o.name hauna,t.atids from bpm_proc_audit t" +
            " left join sys_org o on o.id=t.hauid where t.proid=? order by t.crtim";
        return  jdbcHelper.getTp().query(sql,new BeanPropertyRowMapper<>(BpmAuditVo.class),proid);
    }

    public List<BpmRefuseInfoVo> findCanRefuseNodeList(Long proid){
        String sql="""
            select distinct t.facno refno,t.facna refna,t.hauid exuid,t.crtim
            from bpm_proc_audit t
            where proid=? and opkey in('dsubmit','pass')
            order by t.crtim
        """;
        return  jdbcHelper.getTp().query(sql,new BeanPropertyRowMapper<>(BpmRefuseInfoVo.class),proid);
    }

    public BpmProcAudit findById(Long id) {
        String sql = "select * from bpm_proc_audit where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(BpmProcAudit.class), id);
    }

    public void insert(BpmProcAudit main) {
        Isqler isqler = new Isqler("bpm_proc_audit");
        if(main.getId()==null){
            main.setId(IdUtils.getSnowflakeNextId());
        }
        isqler.add("id", main.getId());
        isqler.add("facno", main.getFacno());
        isqler.add("facna", main.getFacna());
        isqler.add("crtim", main.getCrtim());
        isqler.add("proid", main.getProid());
        isqler.add("nodid", main.getNodid());
        isqler.add("tasid", main.getTasid());
        isqler.add("hauid", main.getHauid());
        isqler.add("opkey", main.getOpkey());
        isqler.add("opinf", main.getOpinf());
        isqler.add("opnot", main.getOpnot());
        isqler.add("atids", main.getAtids());
        jdbcHelper.getTp().update(isqler.getSql(), isqler.getParams());
    }

    public void update(BpmProcAudit main) {
        Usqler usqler = new Usqler("bpm_proc_audit");
        usqler.addWhere("id=?", main.getId());
        usqler.add("facno", main.getFacno());
        usqler.add("facna", main.getFacna());
        usqler.add("proid", main.getProid());
        usqler.add("nodid", main.getNodid());
        usqler.add("tasid", main.getTasid());
        usqler.add("hauid", main.getHauid());
        usqler.add("opkey", main.getOpkey());
        usqler.add("opinf", main.getOpinf());
        usqler.add("opnot", main.getOpnot());
        usqler.add("atids", main.getAtids());
        jdbcHelper.getTp().update(usqler.getSql(), usqler.getParams());
    }

    public void deleteById(Long id) {
        jdbcHelper.update("delete from bpm_proc_audit where id=?", id);
    }

    private final JdbcHelper jdbcHelper;

}
