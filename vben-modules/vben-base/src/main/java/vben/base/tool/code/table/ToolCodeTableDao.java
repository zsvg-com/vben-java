package vben.base.tool.code.table;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.base.tool.code.field.ToolCodeField;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.jdbc.sqler.Usqler;

import java.lang.reflect.Field;
import java.util.List;


@Component
@RequiredArgsConstructor
public class ToolCodeTableDao {

    private static final String table = "tool_code_table";

    public PageData findPageData(Sqler sqler) {
        return jdbcHelper.findPageData(sqler);
    }

    public ToolCodeTable findById(Long id) {
        String sql = "select * from "+table+" where id = ?";
        ToolCodeTable table= jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(ToolCodeTable.class), id);
        String roleSql="select * from tool_code_field where tabid = ? order by ornum";
        List<ToolCodeField> list = jdbcHelper.getTp().query(roleSql, new BeanPropertyRowMapper<>(ToolCodeField.class),id);
        table.setFields(list);

        String cusql = "select name from sys_org where id = ?";
        if(table.getCruid()!=null){
            table.setCruna(jdbcHelper.findString(cusql, table.getCruid()));
        }
        if(table.getUpuid()!=null){
            table.setUpuna(jdbcHelper.findString(cusql, table.getUpuid()));
        }
        return table;
    }

    @SneakyThrows
    public void insert(ToolCodeTable main) {
        Isqler sqler = new Isqler(table);
        Class<?> clazz = main.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if(!field.getName().equals("fields")&&!field.getName().equals("cruna")&&!field.getName().equals("upuna")) {
                field.setAccessible(true);
                sqler.add(field.getName(), field.get(main));
            }
        }
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    @SneakyThrows
    public void update(ToolCodeTable main) {
        Usqler sqler = new Usqler(table);
        sqler.addWhere("id=?", main.getId());
        Class<?> clazz = main.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if(!field.getName().equals("id")&&!field.getName().equals("fields")&&!field.getName().equals("cruna")&&!field.getName().equals("upuna")) {
                field.setAccessible(true);
                sqler.add(field.getName(), field.get(main));
            }
        }
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void deleteById(Long id) {
        jdbcHelper.update("delete from "+table+" where id=?", id);
    }

    private final JdbcHelper jdbcHelper;

}
