package vben.bpm.role.node;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;

@Component
@RequiredArgsConstructor
public class BpmRoleNodeDao {

    public BpmRoleNode findById(String id) {
        String sql = "select t.*,a.name memna from bpm_role_node t left join sys_actor a on a.id=t.memid where t.id = ?";
        BpmRoleNode node = jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(BpmRoleNode.class), id);
        return node;
    }

    public void insert(BpmRoleNode tree) {
        Isqler sqler = new Isqler("bpm_role_node");
        sqler.add("id", tree.getId());
        sqler.add("name", tree.getName());
        sqler.add("notes", tree.getNotes());
        sqler.add("ornum", tree.getOrnum());
        sqler.add("treid", tree.getTreid());
        sqler.add("pid", tree.getPid());
        sqler.add("tier", tree.getTier());
        sqler.add("crtim", tree.getCrtim());
        sqler.add("uptim", tree.getCrtim());
        sqler.add("memid", tree.getMemid());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void update(BpmRoleNode tree) {
        Usqler sqler = new Usqler("bpm_role_node");
        sqler.addWhere("id=?", tree.getId());
        sqler.add("name", tree.getName());
        sqler.add("notes", tree.getNotes());
        sqler.add("ornum", tree.getOrnum());
        sqler.add("treid", tree.getTreid());
        sqler.add("pid", tree.getPid());
        sqler.add("tier", tree.getTier());
        sqler.add("uptim", tree.getUptim());
        sqler.add("memid", tree.getMemid());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void deleteById(String id) {
        String sql = "delete from bpm_role_node where id=?";
        jdbcHelper.getTp().update(sql, id);
    }

    private final JdbcHelper jdbcHelper;

}
