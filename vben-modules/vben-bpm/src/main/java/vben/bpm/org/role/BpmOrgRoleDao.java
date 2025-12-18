package vben.bpm.org.role;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;

@Component
@RequiredArgsConstructor
public class BpmOrgRoleDao {

    public BpmOrgRole findById(String id) {
        String sql = "select * from bpm_org_role where id = ?";
        BpmOrgRole role = jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(BpmOrgRole.class), id);
        return role;
    }

    public void insert(BpmOrgRole tree) {
        Isqler isqler = new Isqler("bpm_org_role");
        isqler.add("id", tree.getId());
        isqler.add("name", tree.getName());
        isqler.add("notes", tree.getNotes());
        isqler.add("ornum", tree.getOrnum());
        isqler.add("treid", tree.getTreid());
        jdbcHelper.getTp().update(isqler.getSql(), isqler.getParams());
    }

    public void update(BpmOrgRole tree) {
        Usqler usqler = new Usqler("bpm_org_role");
        usqler.addWhere("id=?", tree.getId());
        usqler.add("name", tree.getName());
        usqler.add("notes", tree.getNotes());
        usqler.add("ornum", tree.getOrnum());
        usqler.add("treid", tree.getTreid());
        jdbcHelper.getTp().update(usqler.getSql(), usqler.getParams());
    }

    public void deleteById(String id) {
        String sql = "delete from bpm_org_role where id=?";
        jdbcHelper.getTp().update(sql, id);
    }

    public void deleteByTreid(String treid) {
        String sql = "delete from bpm_org_role where treid=?";
        jdbcHelper.getTp().update(sql, treid);
    }

    private final JdbcHelper jdbcHelper;
}
