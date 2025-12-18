package vben.bpm.todo.done;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;

@Component
@RequiredArgsConstructor
public class BpmTodoDoneDao {

    public BpmTodoDone findById(Long id) {
        String sql = "select * from bpm_todo_done where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(BpmTodoDone.class), id);
    }

    public void insert(BpmTodoDone main) {
        Isqler sqler = new Isqler("bpm_todo_done");
        if(main.getId()==null){
            main.setId(IdUtils.getSnowflakeNextIdStr());
        }
        sqler.add("id", main.getId());
        sqler.add("todid", main.getTodid());
        sqler.add("useid", main.getUseid());
        sqler.add("entim", main.getEntim());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void update(BpmTodoDone main) {
        Usqler sqler = new Usqler("bpm_todo_done");
        sqler.addWhere("id=?", main.getId());
        sqler.add("todid", main.getTodid());
        sqler.add("useid", main.getUseid());
        sqler.add("entim", main.getEntim());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void deleteById(Long id) {
        jdbcHelper.update("delete from bpm_todo_done where id=?", id);
    }

    private final JdbcHelper jdbcHelper;

}
