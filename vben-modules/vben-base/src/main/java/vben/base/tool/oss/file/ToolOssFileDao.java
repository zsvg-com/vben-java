package vben.base.tool.oss.file;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;


@Component
@RequiredArgsConstructor
public class ToolOssFileDao {

    public ToolOssFile findByMd5(String md5) {
        String sql = "select * from tool_oss_file where md5 = ?";
        ToolOssFile file = null;
        try {
            file = jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(ToolOssFile.class), md5);
        } catch (Exception ignored) {

        }
        return file;
    }

    public ToolOssFile findById(String id) {
        String sql = "select * from tool_oss_file where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(ToolOssFile.class), id);
    }

    public void insert(ToolOssFile main) {
        Isqler sqler = new Isqler("tool_oss_file");
        sqler.add("id", main.getId());
        sqler.add("md5", main.getMd5());
        sqler.add("fsize", main.getFsize());
        sqler.add("path", main.getPath());
        sqler.add("service", main.getService());
        sqler.add("crtim", main.getCrtim());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void update(ToolOssFile main) {
        Usqler sqler = new Usqler("tool_oss_file");
        sqler.addWhere("id=?", main.getId());
        sqler.add("md5", main.getMd5());
        sqler.add("fsize", main.getFsize());
        sqler.add("path", main.getPath());
        sqler.add("service", main.getService());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void deleteById(String id) {
        jdbcHelper.update("delete from tool_oss_file where id=?", id);
    }

    private final JdbcHelper jdbcHelper;

}

