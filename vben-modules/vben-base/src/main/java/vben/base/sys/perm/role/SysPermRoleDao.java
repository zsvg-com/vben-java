package vben.base.sys.perm.role;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SysPermRoleDao {

    private final JdbcHelper jdbcHelper;

    public List<MenuVo> findMenuVoList() {
        String menuSql="select id,name,pid,icon,type from sys_perm_menu where avtag="+Db.True+" order by ornum";
        List<MenuVo> menus= jdbcHelper.getTp().query(menuSql, new BeanPropertyRowMapper<>(MenuVo.class));

        String apiSql="select id,name,menid from sys_perm_api where avtag="+Db.True+" order by ornum";
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

    public SysPermRole findById(Long id) {
        String sql = "select * from sys_perm_role where id = ?";
        SysPermRole role = jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(SysPermRole.class), id);
        //创建人与修改人
        String cusql = "select name from sys_org where id = ?";
        if(role.getCruid()!=null){
            role.setCruna(jdbcHelper.findString(cusql, role.getCruid()));
        }
        if(role.getUpuid()!=null){
            role.setUpuna(jdbcHelper.findString(cusql, role.getUpuid()));
        }
        //成员、菜单与接口
        String orgSql="select t.id,t.name from sys_org t inner join sys_perm_role_org o on o.oid=t.id where o.rid=?";
        List<SidName> orgList = jdbcHelper.getTp().query(orgSql, new BeanPropertyRowMapper<>(SidName.class),id);
        role.setOrgs(orgList);
        List<Long> menidList = jdbcHelper.findLlist("select mid id from sys_perm_role_menu where rid=?", id);
        role.setMenus(menidList);
        List<Long> apiidList = jdbcHelper.findLlist("select aid id from sys_perm_role_api where rid=?", id);
        role.setApis(apiidList);
        return role;
    }

    public void insert(SysPermRole role) {
        Isqler sqler = new Isqler("sys_perm_role");
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
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());

        String apiSql = "insert into sys_perm_role_api(rid,aid) values(?,?)";
        List<Object[]> apiInsertList = new ArrayList<>();
        for (Long aid : role.getApis()) {
            Object[] arr = new Object[2];
            arr[0] = role.getId();
            arr[1] = aid;
            apiInsertList.add(arr);
        }
        jdbcHelper.batch(apiSql, apiInsertList);

        String menuSql = "insert into sys_perm_role_menu(rid,mid) values(?,?)";
        List<Object[]> menuInsertList = new ArrayList<>();
        for (Long mid : role.getMenus()) {
            Object[] arr = new Object[2];
            arr[0] = role.getId();
            arr[1] = mid;
            menuInsertList.add(arr);
        }
        jdbcHelper.batch(menuSql, menuInsertList);

        String orgSql = "insert into sys_perm_role_org(rid,oid) values(?,?)";
        List<Object[]> orgInsertList = new ArrayList<>();
        for (SidName sidName : role.getOrgs()) {
            Object[] arr = new Object[2];
            arr[0] = role.getId();
            arr[1] = sidName.getId();
            orgInsertList.add(arr);
        }
        jdbcHelper.batch(orgSql, orgInsertList);
    }

    public void update(SysPermRole role) {
        Usqler sqler = new Usqler("sys_perm_role");
        sqler.addWhere("id=?", role.getId());
        sqler.add("name", role.getName());
        sqler.add("notes", role.getNotes());
        sqler.add("uptim", role.getUptim());
        sqler.add("upuid", role.getUpuid());
        sqler.add("avtag", role.getAvtag());
        sqler.add("ornum", role.getOrnum());
        sqler.add("type", role.getType());
        sqler.add("scope", role.getScope());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());

        jdbcHelper.update("delete from sys_perm_role_api where rid=?", role.getId());
        String apiSql = "insert into sys_perm_role_api(rid,aid) values(?,?)";
        List<Object[]> apiInsertList = new ArrayList<>();
        for (Long aid : role.getApis()) {
            Object[] arr = new Object[2];
            arr[0] = role.getId();
            arr[1] = aid;
            apiInsertList.add(arr);
        }
        jdbcHelper.batch(apiSql, apiInsertList);

        jdbcHelper.update("delete from sys_perm_role_menu where rid=?", role.getId());
        String menuSql = "insert into sys_perm_role_menu(rid,mid) values(?,?)";
        List<Object[]> menuInsertList = new ArrayList<>();
        for (Long mid : role.getMenus()) {
            Object[] arr = new Object[2];
            arr[0] = role.getId();
            arr[1] = mid;
            menuInsertList.add(arr);
        }
        jdbcHelper.batch(menuSql, menuInsertList);

        jdbcHelper.update("delete from sys_perm_role_org where rid=?", role.getId());
        String orgSql = "insert into sys_perm_role_org(rid,oid) values(?,?)";
        List<Object[]> orgInsertList = new ArrayList<>();
        for (SidName sidName : role.getOrgs()) {
            Object[] arr = new Object[2];
            arr[0] = role.getId();
            arr[1] = sidName.getId();
            orgInsertList.add(arr);
        }
        jdbcHelper.batch(orgSql, orgInsertList);
    }

    public void deleteById(Long id) {
        jdbcHelper.update("delete from sys_perm_role_org where rid=?", id);
        jdbcHelper.update("delete from sys_perm_role_api where rid=?", id);
        jdbcHelper.update("delete from sys_perm_role_menu where rid=?", id);
        jdbcHelper.update("delete from sys_perm_role where id=?", id);
    }

}
