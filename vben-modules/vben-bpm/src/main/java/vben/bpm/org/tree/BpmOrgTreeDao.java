package vben.bpm.org.tree;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.bpm.org.role.BpmOrgRole;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BpmOrgTreeDao {

    public BpmOrgTree findById(String id) {
        String sql = "select * from bpm_org_tree where id = ?";
        BpmOrgTree tree = jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(BpmOrgTree.class), id);
        String roleSql="select * from bpm_org_role where treid = ? order by ornum";
        List<BpmOrgRole> list = jdbcHelper.getTp().query(roleSql, new BeanPropertyRowMapper<>(BpmOrgRole.class),id);
        tree.setRoles(list);
        return tree;
    }

    public void insert(BpmOrgTree tree) {
        Isqler isqler = new Isqler("bpm_org_tree");
        isqler.add("id", tree.getId());
        isqler.add("name", tree.getName());
        isqler.add("notes", tree.getNotes());
        isqler.add("ornum", tree.getOrnum());
        isqler.add("crtim", tree.getCrtim());
        isqler.add("uptim", tree.getCrtim());
        isqler.add("avtag", tree.getAvtag());
        isqler.add("cruid", tree.getCruid());
        isqler.add("upuid", tree.getUpuid());
        isqler.add("catid", tree.getCatid());
        jdbcHelper.getTp().update(isqler.getSql(), isqler.getParams());

    }

    public void update(BpmOrgTree tree) {
        Usqler usqler = new Usqler("bpm_org_tree");
        usqler.addWhere("id=?", tree.getId());
        usqler.add("name", tree.getName());
        usqler.add("notes", tree.getNotes());
        usqler.add("ornum", tree.getOrnum());
        usqler.add("uptim", tree.getUptim());
        usqler.add("avtag", tree.getAvtag());
        usqler.add("cruid", tree.getCruid());
        usqler.add("upuid", tree.getUpuid());
        usqler.add("catid", tree.getCatid());
        jdbcHelper.getTp().update(usqler.getSql(), usqler.getParams());
    }

    public void deleteById(String id) {
        String sql = "delete from bpm_org_tree where id=?";
        jdbcHelper.getTp().update(sql, id);
    }

    private final JdbcHelper jdbcHelper;
}
