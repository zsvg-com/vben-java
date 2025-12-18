package vben.base.tool.oss.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.base.tool.oss.SysOss;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;

@Component
@RequiredArgsConstructor
public class SysOssDao {

    public SysOss findById(Long id) {
        String sql = "select * from sys_oss where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(SysOss.class), id);
    }

    public void insert(SysOss main) {
        Isqler sqler = new Isqler("sys_oss");
        sqler.add("oss_id", main.getOssId());
        sqler.add("create_dept", main.getCreateDept());
        sqler.add("create_by", main.getCreateBy());
        sqler.add("create_time", main.getCreateTime());
        sqler.add("update_by", main.getUpdateBy());
        sqler.add("update_time", main.getUpdateTime());
        sqler.add("tenant_Id", main.getTenantId());
        sqler.add("file_name", main.getFileName());
        sqler.add("original_name", main.getOriginalName());
        sqler.add("file_suffix", main.getFileSuffix());
        sqler.add("url", main.getUrl());
        sqler.add("ext1", main.getExt1());
        sqler.add("service", main.getService());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void update(SysOss main) {
        Usqler sqler = new Usqler("sys_oss");
        sqler.addWhere("id=?", main.getOssId());
        sqler.add("update_by", main.getUpdateBy());
        sqler.add("update_time", main.getUpdateTime());
        sqler.add("tenant_Id", main.getTenantId());
        sqler.add("file_name", main.getFileName());
        sqler.add("original_name", main.getOriginalName());
        sqler.add("file_suffix", main.getFileSuffix());
        sqler.add("url", main.getUrl());
        sqler.add("ext1", main.getExt1());
        sqler.add("service", main.getService());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void deleteById(String id) {
        jdbcHelper.update("delete from sys_oss where id=?", id);
    }


    private final JdbcHelper jdbcHelper;


}
