package vben.bpm.org.node;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;

@Component
@RequiredArgsConstructor
public class BpmOrgNodeDao {

    public BpmOrgNode findById(String id) {
        String sql = "select t.*,o.name memna from bpm_org_node t left join sys_org o on o.id=t.memid where t.id = ?";
        BpmOrgNode node = jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(BpmOrgNode.class), id);
        return node;
    }

    public void insert(BpmOrgNode tree) {
        Isqler sqler = new Isqler("bpm_org_node");
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

    public void update(BpmOrgNode tree) {
        Usqler sqler = new Usqler("bpm_org_node");
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
        String sql = "delete from bpm_org_node where id=?";
        jdbcHelper.getTp().update(sql, id);
    }

    private final JdbcHelper jdbcHelper;

}
