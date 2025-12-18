package vben.base.sys.perm.api;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.dto.Stree;
import vben.common.jdbc.root.Db;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.jdbc.sqler.Usqler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class SysPermApiDao {

    public PageData findPageData(Sqler sqler) {
        return jdbcHelper.findPageData(sqler);
    }

    public Set<String> findSetByOids(String oids) {
        String sql = "select distinct perm id from sys_perm_api a inner join sys_perm_role_api ra on ra.aid=a.id inner join sys_perm_role_org ro on ro.rid=ra.rid  where a.avtag="+Db.True+" and ro.oid in ("+oids+")";
        List<String> stringList = jdbcHelper.findSlist(sql);
        return new HashSet<>(stringList);
    }

    public List<Stree> findTreeList() {
        return jdbcHelper.findStreeList("select id,name,pid from sys_perm_api where catag="+Db.True+" order by ornum");
    }

    public SysPermApi findById(Long id) {
        String sql = "select * from sys_perm_api where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(SysPermApi.class), id);
    }

    public void insert(SysPermApi main) {
        main.setId(IdUtils.getSnowflakeNextId());
        Isqler isqler = new Isqler("sys_perm_api");
        isqler.add("id", main.getId());
        isqler.add("name", main.getName());
        isqler.add("type", main.getType());
        isqler.add("menid", main.getMenid());
        isqler.add("notes", main.getNotes());
        isqler.add("crtim", main.getCrtim());
        isqler.add("uptim", main.getCrtim());
        isqler.add("avtag", main.getAvtag());
        isqler.add("ornum", main.getOrnum());
        isqler.add("perm", main.getPerm());
        jdbcHelper.getTp().update(isqler.getSql(), isqler.getParams());
    }

    public void update(SysPermApi main) {
        Usqler usqler = new Usqler("sys_perm_api");
        usqler.addWhere("id=?", main.getId());
        usqler.add("name", main.getName());
        usqler.add("type", main.getType());
        usqler.add("menid", main.getMenid());
        usqler.add("notes", main.getNotes());
        usqler.add("uptim", main.getUptim());
        usqler.add("avtag", main.getAvtag());
        usqler.add("ornum", main.getOrnum());
        usqler.add("perm", main.getPerm());
        jdbcHelper.getTp().update(usqler.getSql(), usqler.getParams());
    }

    public void deleteById(Long id) {
        String sql = "delete from sys_perm_api where id=?";
        jdbcHelper.getTp().update(sql, id);
    }

    private final JdbcHelper jdbcHelper;

}
