package vben.base.tool.oss.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.base.tool.oss.SysOssConfig;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SysOssConfigDao {

    public SysOssConfig findById(Long id) {
        String sql = "select * from sys_oss_config where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(SysOssConfig.class), id);
    }

    public void insert(SysOssConfig main) {
        Isqler sqler = new Isqler("sys_oss_config");
        sqler.add("oss_config_id", main.getOssConfigId());
        sqler.add("create_dept", main.getCreateDept());
        sqler.add("create_by", main.getCreateBy());
        sqler.add("create_time", main.getCreateTime());
        sqler.add("update_by", main.getUpdateBy());
        sqler.add("update_time", main.getUpdateTime());
        sqler.add("config_key", main.getConfigKey());
        sqler.add("access_key", main.getAccessKey());
        sqler.add("bucket_name", main.getBucketName());
        sqler.add("prefix", main.getPrefix());
        sqler.add("endpoint", main.getEndpoint());
        sqler.add("domain", main.getDomain());
        sqler.add("is_https", main.getIsHttps());
        sqler.add("region", main.getRegion());
        sqler.add("status", main.getStatus());
        sqler.add("ext1", main.getExt1());
        sqler.add("remark", main.getRemark());
        sqler.add("access_policy", main.getAccessPolicy());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void update(SysOssConfig main) {
        Usqler sqler = new Usqler("sys_oss_config");
        sqler.addWhere("oss_config_id=?", main.getOssConfigId());
        sqler.add("update_by", main.getUpdateBy());
        sqler.add("update_time", main.getUpdateTime());
        sqler.add("config_key", main.getConfigKey());
        sqler.add("access_key", main.getAccessKey());
        sqler.add("bucket_name", main.getBucketName());
        sqler.add("prefix", main.getPrefix());
        sqler.add("endpoint", main.getEndpoint());
        sqler.add("domain", main.getDomain());
        sqler.add("is_https", main.getIsHttps());
        sqler.add("region", main.getRegion());
        sqler.add("status", main.getStatus());
        sqler.add("ext1", main.getExt1());
        sqler.add("remark", main.getRemark());
        sqler.add("access_policy", main.getAccessPolicy());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void deleteById(String id) {
        jdbcHelper.update("delete from sys_oss_config where id=?", id);
    }

    public List<SysOssConfig> findAll() {
        String sql = "select * from sys_oss_config";
        return jdbcHelper.getTp().query(sql, new BeanPropertyRowMapper<>(SysOssConfig.class));
    }

    private final JdbcHelper jdbcHelper;


}
