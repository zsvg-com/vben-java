package vben.bpm.todo.hist;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;

@Component
@RequiredArgsConstructor
public class BpmTodoHiDao {

    public BpmTodoHi findById(Long id) {
        String sql = "select * from bpm_todo_hist where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(BpmTodoHi.class), id);
    }

    public void insert(BpmTodoHi main) {
        Isqler sqler = new Isqler("bpm_todo_hist");
        if(main.getId()==null){
            main.setId(IdUtils.getSnowflakeNextIdStr());
        }
        sqler.add("id", main.getId());
        sqler.add("name", main.getName());
        sqler.add("type", main.getType());
        sqler.add("grade", main.getGrade());
        sqler.add("busca", main.getBusca());
        sqler.add("busid", main.getBusid());
        sqler.add("link", main.getLink());
        sqler.add("notes", main.getNotes());
        sqler.add("crtim", main.getCrtim());
        sqler.add("cruid", main.getCruid());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void update(BpmTodoHi main) {
        Usqler sqler = new Usqler("bpm_todo_hist");
        sqler.addWhere("id=?", main.getId());
        sqler.add("name", main.getName());
        sqler.add("type", main.getType());
        sqler.add("grade", main.getGrade());
        sqler.add("busca", main.getBusca());
        sqler.add("busid", main.getBusid());
        sqler.add("link", main.getLink());
        sqler.add("notes", main.getNotes());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void deleteById(Long id) {
        jdbcHelper.update("delete from bpm_todo_hist where id=?", id);
    }

    private final JdbcHelper jdbcHelper;

}
