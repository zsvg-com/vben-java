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
public class ToolOssDao {

    public ToolOss findById(String id) {
        String sql = "select * from tool_oss where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(ToolOss.class), id);
    }

    public void insert(ToolOss main) {
        Isqler sqler = new Isqler("tool_oss");
        sqler.add("id", main.getId());
        sqler.add("crtim", main.getCrtim());
        sqler.add("name", main.getName());
        sqler.add("type", main.getType());
        sqler.add("filid", main.getFilid());
        sqler.add("busid", main.getBusid());
        sqler.add("cruid", main.getCruid());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void update(ToolOss main) {
        Usqler sqler = new Usqler("tool_oss");
        sqler.addWhere("id=?", main.getId());
        sqler.add("name", main.getName());
        sqler.add("type", main.getType());
        sqler.add("filid", main.getFilid());
        sqler.add("busid", main.getBusid());
        sqler.add("cruid", main.getCruid());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void deleteById(String id) {
        jdbcHelper.update("delete from tool_oss where id=?", id);
    }

    public List<ToolOss> findAllById(List<String> idList) {
        List<ToolOss> list=new ArrayList<>();
        for (String id : idList) {
            String sql = "select * from tool_oss=?";
            ToolOss main= jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(ToolOss.class), id);
            list.add(main);
        }
        return list;
    }

    private final JdbcHelper jdbcHelper;

}

