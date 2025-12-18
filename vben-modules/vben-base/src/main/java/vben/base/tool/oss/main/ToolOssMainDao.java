package vben.base.tool.oss.main;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class ToolOssMainDao {

    public ToolOssMain findById(String id) {
        String sql = "select * from tool_oss_main where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(ToolOssMain.class), id);
    }

    public void insert(ToolOssMain main) {
        Isqler sqler = new Isqler("tool_oss_main");
        sqler.add("id", main.getId());
        sqler.add("crtim", main.getCrtim());
        sqler.add("name", main.getName());
        sqler.add("type", main.getType());
        sqler.add("filid", main.getFilid());
        sqler.add("busid", main.getBusid());
        sqler.add("cruid", main.getCruid());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void update(ToolOssMain main) {
        Usqler sqler = new Usqler("tool_oss_main");
        sqler.addWhere("id=?", main.getId());
        sqler.add("name", main.getName());
        sqler.add("type", main.getType());
        sqler.add("filid", main.getFilid());
        sqler.add("busid", main.getBusid());
        sqler.add("cruid", main.getCruid());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void deleteById(String id) {
        jdbcHelper.update("delete from tool_oss_main where id=?", id);
    }

    public List<ToolOssMain> findAllById(List<String> idList) {
        List<ToolOssMain> list=new ArrayList<>();
        for (String id : idList) {
            String sql = "select * from tool_oss_main=?";
            ToolOssMain main= jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(ToolOssMain.class), id);
            list.add(main);
        }
        return list;
    }

    private final JdbcHelper jdbcHelper;

}

