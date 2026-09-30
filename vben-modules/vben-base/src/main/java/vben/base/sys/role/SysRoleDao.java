package vben.base.sys.role;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.dto.SidName;
import vben.common.jdbc.root.Db;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.jdbc.sqler.Usqler;

import java.util.*;

@Component
@RequiredArgsConstructor
public class SysRoleDao {

    private final JdbcHelper jdbcHelper;

    public Set<String> findSetByAids(String aids) {
        String sql = "select distinct r.label id from sys_role r inner join sys_role_actor ra on ra.rid=r.id  where r.avtag="+Db.True+" and ra.aid in ("+aids+")";
        List<String> stringList = jdbcHelper.findSlist(sql);
        return new HashSet<>(stringList);
    }

    public Integer findDataScope(String aids) {
        String sql = "select max(r.scope) id from sys_role r inner join sys_role_actor ra on ra.rid=r.id  where r.avtag="+Db.True+" and ra.aid in ("+aids+")";
        return jdbcHelper.findInteger(sql);
    }

    public List<MenuVo> findMenuVoList() {
        String menuSql="select id,name,pid,icon,type from sys_menu where avtag="+Db.True+" order by ornum";
        List<MenuVo> menus= jdbcHelper.getTp().query(menuSql, new BeanPropertyRowMapper<>(MenuVo.class));

        String apiSql="select id,name,menid from sys_api where avtag="+Db.True+" order by ornum";
        List<ApiVo> apis= jdbcHelper.getTp().query(apiSql, new BeanPropertyRowMapper<>(ApiVo.class));

        for (MenuVo menu : menus) {
            for (ApiVo api : apis) {
                if(Objects.equals(api.getMenid(), menu.getId())) {
                    menu.getApis().add(api);
                }
            }
        }
        return menus;
    }

    public PageData findPageData(Sqler sqler) {
        return jdbcHelper.findPageData(sqler);
    }

    public SysRole findById(Long id) {
        String sql = "select * from sys_role where id = ?";
        SysRole role = jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(SysRole.class), id);
        //创建人与修改人
        String cusql = "select name from sys_actor where id = ?";
        if(role.getCruid()!=null){
            role.setCruna(jdbcHelper.findString(cusql, role.getCruid()));
        }
        if(role.getUpuid()!=null){
            role.setUpuna(jdbcHelper.findString(cusql, role.getUpuid()));
        }
        //成员、菜单与接口
        String actorSql="select t.id,t.name from sys_actor t inner join sys_role_actor a on a.aid=t.id where a.rid=?";
        List<SidName> actorList = jdbcHelper.getTp().query(actorSql, new BeanPropertyRowMapper<>(SidName.class),id);
        role.setActors(actorList);
        List<Long> menidList = jdbcHelper.findLlist("select mid id from sys_role_menu where rid=?", id);
        role.setMenus(menidList);
        List<Long> apiidList = jdbcHelper.findLlist("select aid id from sys_role_api where rid=?", id);
        role.setApis(apiidList);
        List<String> orgidList = jdbcHelper.findSlist("select aid id from sys_role_org where rid=?", id);
        role.setOrgids(orgidList);
        return role;
    }

    public void insert(SysRole role) {
        Isqler sqler = new Isqler("sys_role");
        sqler.add("id", role.getId());
        sqler.add("name", role.getName());
        sqler.add("notes", role.getNotes());
        sqler.add("crtim", role.getCrtim());
        sqler.add("cruid", role.getCruid());
        sqler.add("uptim", role.getCrtim());
        sqler.add("upuid", role.getCruid());
        sqler.add("avtag", role.getAvtag());
        sqler.add("ornum", role.getOrnum());
        sqler.add("type", role.getType());
        sqler.add("scope", role.getScope());
        sqler.add("label", role.getLabel());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());

        //接口处理
        String apiSql = "insert into sys_role_api(rid,aid) values(?,?)";
        List<Object[]> apiInsertList = new ArrayList<>();
        for (Long aid : role.getApis()) {
            Object[] arr = new Object[2];
            arr[0] = role.getId();
            arr[1] = aid;
            apiInsertList.add(arr);
        }
        jdbcHelper.batch(apiSql, apiInsertList);

        //菜单处理
        String menuSql = "insert into sys_role_menu(rid,mid) values(?,?)";
        List<Object[]> menuInsertList = new ArrayList<>();
        for (Long mid : role.getMenus()) {
            Object[] arr = new Object[2];
            arr[0] = role.getId();
            arr[1] = mid;
            menuInsertList.add(arr);
        }
        jdbcHelper.batch(menuSql, menuInsertList);

        //成员处理
        String actorSql = "insert into sys_role_actor(rid,aid) values(?,?)";
        List<Object[]> actorInsertList = new ArrayList<>();
        for (SidName sidName : role.getActors()) {
            Object[] arr = new Object[2];
            arr[0] = role.getId();
            arr[1] = sidName.getId();
            actorInsertList.add(arr);
        }
        jdbcHelper.batch(actorSql, actorInsertList);

        //数据权限处理
        String orgSql = "insert into sys_role_org(rid,aid) values(?,?)";
        List<Object[]> orgInsertList = new ArrayList<>();
        for (String orgid : role.getOrgids()) {
            Object[] arr = new Object[2];
            arr[0] = role.getId();
            arr[1] = orgid;
            orgInsertList.add(arr);
        }
        jdbcHelper.batch(orgSql, orgInsertList);
    }

    public void update(SysRole role) {
        Usqler sqler = new Usqler("sys_role");
        sqler.addWhere("id=?", role.getId());
        sqler.add("name", role.getName());
        sqler.add("notes", role.getNotes());
        sqler.add("uptim", role.getUptim());
        sqler.add("upuid", role.getUpuid());
        sqler.add("avtag", role.getAvtag());
        sqler.add("ornum", role.getOrnum());
        sqler.add("type", role.getType());
        sqler.add("scope", role.getScope());
        sqler.add("label", role.getLabel());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());

        //接口处理
        jdbcHelper.update("delete from sys_role_api where rid=?", role.getId());
        String apiSql = "insert into sys_role_api(rid,aid) values(?,?)";
        List<Object[]> apiInsertList = new ArrayList<>();
        for (Long aid : role.getApis()) {
            Object[] arr = new Object[2];
            arr[0] = role.getId();
            arr[1] = aid;
            apiInsertList.add(arr);
        }
        jdbcHelper.batch(apiSql, apiInsertList);

        //菜单处理
        jdbcHelper.update("delete from sys_role_menu where rid=?", role.getId());
        String menuSql = "insert into sys_role_menu(rid,mid) values(?,?)";
        List<Object[]> menuInsertList = new ArrayList<>();
        for (Long mid : role.getMenus()) {
            Object[] arr = new Object[2];
            arr[0] = role.getId();
            arr[1] = mid;
            menuInsertList.add(arr);
        }
        jdbcHelper.batch(menuSql, menuInsertList);

        //成员处理
        jdbcHelper.update("delete from sys_role_actor where rid=?", role.getId());
        String actorSql = "insert into sys_role_actor(rid,aid) values(?,?)";
        List<Object[]> actorInsertList = new ArrayList<>();
        for (SidName sidName : role.getActors()) {
            Object[] arr = new Object[2];
            arr[0] = role.getId();
            arr[1] = sidName.getId();
            actorInsertList.add(arr);
        }
        jdbcHelper.batch(actorSql, actorInsertList);

        //数据权限处理
        jdbcHelper.update("delete from sys_role_org where rid=?", role.getId());
        String orgSql = "insert into sys_role_org(rid,aid) values(?,?)";
        List<Object[]> orgInsertList = new ArrayList<>();
        for (String orgid : role.getOrgids()) {
            Object[] arr = new Object[2];
            arr[0] = role.getId();
            arr[1] = orgid;
            orgInsertList.add(arr);
        }
        jdbcHelper.batch(orgSql, orgInsertList);
    }

    public void deleteById(Long id) {
        jdbcHelper.update("delete from sys_role_actor where rid=?", id);
        jdbcHelper.update("delete from sys_role_org where rid=?", id);
        jdbcHelper.update("delete from sys_role_api where rid=?", id);
        jdbcHelper.update("delete from sys_role_menu where rid=?", id);
        jdbcHelper.update("delete from sys_role where id=?", id);
    }

}
