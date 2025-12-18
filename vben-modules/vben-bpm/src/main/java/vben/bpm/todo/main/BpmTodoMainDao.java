package vben.bpm.todo.main;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;

@Component
@RequiredArgsConstructor
public class BpmTodoMainDao {

    public BpmTodoMain findById(String id) {
        String sql = "select * from bpm_todo_main where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(BpmTodoMain.class), id);
    }

    public void insert(BpmTodoMain main) {
        Isqler sqler = new Isqler("bpm_todo_main");
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

    public void update(BpmTodoMain main) {
        Usqler sqler = new Usqler("bpm_todo_main");
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

    public void deleteById(String id) {
        jdbcHelper.update("delete from bpm_todo_main where id=?", id);
    }

    private final JdbcHelper jdbcHelper;

}
