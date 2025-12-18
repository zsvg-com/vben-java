package vben.bpm.form.def;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;

@Component
@RequiredArgsConstructor
public class BpmFormDefDao {


    public BpmFormDef findById(Long id) {
        String sql = "select * from bpm_proc_def where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(BpmFormDef.class), id);
    }

    public void insert(BpmFormDef main) {
        Isqler sqler = new Isqler("bpm_form_def");
        if(main.getId()==null){
            main.setId(IdUtils.getSnowflakeNextId());
        }
        sqler.add("id", main.getId());
        sqler.add("venum", main.getVenum());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void update(BpmFormDef main) {
        Usqler sqler = new Usqler("bpm_form_def");
        sqler.addWhere("id=?", main.getId());
        sqler.add("venum", main.getVenum());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void deleteById(Long id) {
        jdbcHelper.update("delete from bpm_form_def where id=?", id);
    }

    private final JdbcHelper jdbcHelper;

}
