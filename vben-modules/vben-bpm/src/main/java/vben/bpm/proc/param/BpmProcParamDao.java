package vben.bpm.proc.param;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.sqler.BaseSqler;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;
import vben.bpm.root.domain.dto.BpmParamDto;

@Component
@RequiredArgsConstructor
public class BpmProcParamDao {

    public BpmParamDto findParam(Long proid, String pakey){
        String sql="select t.id,t.paval from bpm_proc_param t where t.proid=? and t.pakey=?";
        BpmParamDto dto = null;
        try {
            dto = jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(BpmParamDto.class),proid,pakey);
        } catch (Exception ignored) {

        }
        return dto;
    }

    private static final String table = "bpm_proc_param";

    private void fieldHandle(BaseSqler sqler, BpmProcParam main){
        sqler.add("pakey", main.getPakey());
        sqler.add("paval", main.getPaval());
        sqler.add("offty", main.getOffty());
        sqler.add("offid", main.getOffid());
        sqler.add("proid", main.getProid());
    }

    public BpmProcParam findById(Long id) {
        String sql = "select * from "+table+" where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(BpmProcParam.class), id);
    }

    public void insert(BpmProcParam main) {
        Isqler isqler = new Isqler(table);
        if(main.getId()==null){
            main.setId(IdUtils.getSnowflakeNextId());
        }
        isqler.add("id", main.getId());
        fieldHandle(isqler,main);
        jdbcHelper.getTp().update(isqler.getSql(), isqler.getParams());
    }

    public void update(BpmProcParam main) {
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
