package vben.common.jdbc.sqler;

import cn.hutool.core.convert.Convert;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import vben.common.core.utils.ServletUtils;
import vben.common.core.utils.StrUtils;
import vben.common.jdbc.dto.*;
import vben.common.jdbc.root.Db;
import vben.common.jdbc.root.DbType;
import vben.common.jdbc.utils.LtreeUtils;
import vben.common.jdbc.utils.StreeUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JdbcHelper {

//    @Value("${spring.datasource.primary.dbType}")
//    @Value("#{'${first.url:${secondary.url:}}}'")
//    private String DB_TYPE = "mysql";

    @PostConstruct
    public void init() {

        jdbcTemplate.execute((Connection conn) -> {
            DatabaseMetaData metaData = conn.getMetaData();
            Db.Type= StrUtils.toLowerCase(metaData.getDatabaseProductName());
            if(Db.Type.contains("sql server")){
                Db.Type="sqlserver";
            }
            if("postgresql".equals(Db.Type)) {
                Db.True="true";
                Db.False="false";
            }
            return metaData.getDatabaseProductName(); // 返回数据库产品名称
            //return metaData.getDatabaseProductVersion(); // 返回版本号
        });

    }

    //-------------------------------------------------------------------------------

    public PageData findPageData(Sqler sqler) {
        HttpServletRequest request = ServletUtils.getRequest();
        Integer pageNum =  Convert.toInt(request.getParameter("pageNum"));
        Integer pageSize =  Convert.toInt(request.getParameter("pageSize"));
        if(pageNum!=null){
            sqler.setPanum(pageNum);
        }
        if(pageSize!=null){
            sqler.setPasiz(pageSize);
        }
        Integer count = jdbcTemplate.queryForObject(sqler.getSizeSql(),Integer.class,sqler.getParams());
        if (count == null || count == 0) {
            return new PageData(0, new ArrayList<>());
        }
        List<Map<String, Object>> list;
        if (DbType.MYSQL.equalsIgnoreCase(Db.Type)) {
            list = jdbcTemplate.queryForList(sqler.getMysqlPagingSql(), sqler.getParams());
        }  else if (DbType.PGSQL.equalsIgnoreCase(Db.Type)) {
            list = jdbcTemplate.queryForList(sqler.getPgsqlPagingSql(), sqler.getParams());
        }  else if (DbType.ORACLE.equalsIgnoreCase(Db.Type)) {
            list = jdbcTemplate.queryForList(sqler.getOraclePagingLowerCaseSql(), sqler.getParams());
        } else if (DbType.SQL_SERVER.equalsIgnoreCase(Db.Type)) {
            list = jdbcTemplate.queryForList(sqler.getSqlserverPagingSql(), sqler.getParams());
        } else {
            System.err.println("目前只支持mysql,oracle,sqlserver与postgresql");
            list = new ArrayList<>();
        }
        return new PageData(count, list);
    }

    public List<Stree> findStreeList(Sqler sqler) {
        List<Stree> list = jdbcTemplate.query(sqler.getSql(),  (rs, rowNum) -> {
            Stree stree = new Stree();
            stree.setId(rs.getString("id"));
            stree.setName(rs.getString("name"));
            stree.setPid(rs.getString("pid"));
            return stree;
        },sqler.getParams());
        return StreeUtils.build(list);
    }

    public List<Stree> findStreeList(String sql, Object... args) {
        List<Stree> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Stree.class),args);
        return StreeUtils.build(list);
    }

    public List<Ltree> findLtreeList(Sqler sqler) {
        List<Ltree> list = jdbcTemplate.query(sqler.getSql(),  (rs, rowNum) -> {
            Ltree ltree = new Ltree();
            ltree.setId(rs.getLong("id"));
            ltree.setName(rs.getString("name"));
            ltree.setPid(rs.getLong("pid"));
            return ltree;
        },sqler.getParams());
        return LtreeUtils.build(list);
    }

    public List<Ltree> findLtreeList(String sql, Object... args) {
        List<Ltree> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Ltree.class),args);
        return LtreeUtils.build(list);
    }

    public List<SidName> findSidNameList(String sql, Object... args) {
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            SidName ztwo = new SidName();
            ztwo.setId(rs.getString("id"));
            ztwo.setName(rs.getString("name"));
            return ztwo;
        },args);
    }

    public List<SidName> findSidNameList(Sqler sqler) {
        return findSidNameList((sqler.getSql()), sqler.getParams());
    }

    public List<String> findSlist(String sql, Object... args) {
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("id"),args);
    }

    public List<Long> findLlist(String sql, Object... args) {
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"),args);
    }

    public String findString(String sql, Object... args) {
        String str = null;
        try {
            str = jdbcTemplate.queryForObject(sql, String.class,args);
        } catch (Exception ignored) {

        }
        return str;
    }

    public Integer findInteger(String sql, Object... args) {
        Integer i = null;
        try {
            i = jdbcTemplate.queryForObject(sql, Integer.class,args);
        } catch (Exception ignored) {

        }
        return i;
    }

    public Integer findSize(Sqler sqler) {
        return jdbcTemplate.queryForObject(sqler.getSizeSql(), Integer.class,sqler.getParams());
    }

    public Long findLong(String sql, Object... args) {
        Long l = null;
        try {
            l = jdbcTemplate.queryForObject(sql, Long.class,args);
        } catch (Exception ignored) {

        }
        return l;
    }

    public Map<String, Object> findMap(Sqler sqler) {
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sqler.getSql(), sqler.getParams());
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public Map<String, Object> findMap(String sql, Object... args) {
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public List<Map<String, Object>> findMapList(Sqler sqler) {
        if (DbType.ORACLE.equals(Db.Type)) {
            return jdbcTemplate.queryForList(sqler.getLowerCaseSql(), sqler.getParams());
        } else{
            return jdbcTemplate.queryForList(sqler.getSql(), sqler.getParams());
        }
    }

    //增删改----------------------------------------------------------------------------------
    public int update(String sql, Object... args) {
        return jdbcTemplate.update(sql, args);
    }

    public int update(BaseSqler sqler) throws SQLException {
        return jdbcTemplate.update(sqler.getSql(), sqler.getParams());
    }

    public int[] batch(String sql, List<Object[]> list) {
        return jdbcTemplate.batchUpdate(sql, list);
    }

//    public String getDbType() {
//        return Db.Type;
//    }

    public JdbcTemplate getTp() {
        return jdbcTemplate;
    }

    private final JdbcTemplate jdbcTemplate;

}
