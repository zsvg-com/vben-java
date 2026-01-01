package vben.base.sys.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.core.constant.Constants;
import vben.common.core.utils.IdUtils;
import vben.common.core.utils.StreamUtils;
import vben.common.jdbc.dto.Ltree;
import vben.common.jdbc.root.Db;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.jdbc.sqler.Usqler;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SysMenuDao {

    public List<SysMenu> findListByOids(String oids) {
        String sql = "select distinct m.shtag,m.type,m.name,m.path,m.icon,m.comp,m.ornum,m.id,m.pid,m.outag,m.catag from sys_menu m inner join sys_role_menu rm on rm.mid=m.id inner join sys_role_org ro on ro.rid=rm.rid  where m.avtag="+Db.True+" and ro.oid in ("+oids+") order by m.pid,m.ornum";
        List<SysMenu> list = jdbcHelper.getTp().query(sql, new BeanPropertyRowMapper<>(SysMenu.class));
        return getChildPerms(list, Constants.TOP_PARENT_ID);
    }

    public List<SysMenu> findAll() {
        String sql = "select m.shtag,m.type,m.name,m.path,m.icon,m.comp,m.ornum,m.id,m.pid,m.outag,m.catag from sys_menu m where m.avtag="+Db.True+" order by m.pid,m.ornum";
        List<SysMenu> list = jdbcHelper.getTp().query(sql, new BeanPropertyRowMapper<>(SysMenu.class));
        return getChildPerms(list, Constants.TOP_PARENT_ID);
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    private List<SysMenu> getChildPerms(List<SysMenu> list, Long parentId) {
        List<SysMenu> returnList = new ArrayList<>();
        for (SysMenu t : list) {
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getPid().equals(parentId)) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }


    /**
     * 递归列表
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = StreamUtils.filter(list, n -> n.getPid().equals(t.getId()));
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            // 判断是否有子节点
            if (list.stream().anyMatch(n -> n.getPid().equals(tChild.getId()))) {
                recursionFn(list, tChild);
            }
        }
    }

    public SysMenu findById(Long id) {
        String sql = "select * from sys_menu where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(SysMenu.class), id);
    }

    public void insert(SysMenu menu) {
        menu.setId(IdUtils.getSnowflakeNextId());
        Isqler isqler = new Isqler("sys_menu");
        isqler.add("id", menu.getId());
        isqler.add("name", menu.getName());
        isqler.add("pid", menu.getPid());
        isqler.add("ornum", menu.getOrnum());
        isqler.add("crtim", menu.getCrtim());
        isqler.add("uptim", menu.getCrtim());
        isqler.add("avtag", menu.getAvtag());
        isqler.add("icon", menu.getIcon());
        isqler.add("comp", menu.getComp());
        isqler.add("outag", menu.getOutag());
        isqler.add("shtag", menu.getShtag());
        isqler.add("param", menu.getParam());
        isqler.add("type", menu.getType());
        isqler.add("path", menu.getPath());
        isqler.add("catag", menu.getCatag());
        isqler.add("notes", menu.getNotes());
        jdbcHelper.getTp().update(isqler.getSql(), isqler.getParams());
    }

    public void update(SysMenu menu)  {
        Usqler usqler = new Usqler("sys_menu");
        usqler.addWhere("id=?", menu.getId());
        usqler.add("name", menu.getName());
        usqler.add("pid", menu.getPid());
        usqler.add("ornum", menu.getOrnum());
        usqler.add("uptim", menu.getUptim());
        usqler.add("avtag", menu.getAvtag());
        usqler.add("icon", menu.getIcon());
        usqler.add("comp", menu.getComp());
        usqler.add("outag", menu.getOutag());
        usqler.add("shtag", menu.getShtag());
        usqler.add("param", menu.getParam());
        usqler.add("type", menu.getType());
        usqler.add("path", menu.getPath());
        usqler.add("catag", menu.getCatag());
        usqler.add("notes", menu.getNotes());
        jdbcHelper.getTp().update(usqler.getSql(), usqler.getParams());
    }

    public void deleteById(Long id) {
        String sql = "delete from sys_menu where id=?";
        jdbcHelper.getTp().update(sql, id);
    }

    public List<Ltree> findTree(Long id){
        Sqler sqler = new Sqler("sys_menu");
        sqler.addSelect("pid");
        sqler.addWhere("t.avtag = "+ Db.True);
        if (id!=null) {
//            sqler.addWhere("t.tier not like ?", "%_" + id + "_%");
            sqler.addWhere("t.id <> ?", id);
        }
        sqler.addOrder("t.ornum");
        return jdbcHelper.findLtreeList(sqler);
    }



    private final JdbcHelper jdbcHelper;


    public List<SysMenuVo> findList(Sqler sqler) {
        return jdbcHelper.getTp().query(sqler.getSql(),
            new BeanPropertyRowMapper<>(SysMenuVo.class),sqler.getParams());
    }

}
