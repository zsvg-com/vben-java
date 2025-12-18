package vben.base.sys.org.group;

import lombok.RequiredArgsConstructor;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.base.sys.org.root.Org;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SysOrgGroupDao {

    public SysOrgGroup findById(String id) {
        String sql = "select * from sys_org_group where id = ?";
        SysOrgGroup group = jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(SysOrgGroup.class), id);
        String sql2 = "select t.id,t.name from sys_org t inner join sys_org_group_org o on o.oid=t.id where o.gid = ?";
        List<Org> users = jdbcHelper.getTp().query(sql2, new BeanPropertyRowMapper<>(Org.class), id);
        group.setMembers(users);
        return group;
    }


    public void insert(SysOrgGroup group) {
        Isqler sqler = new Isqler("sys_org_group");
        sqler.add("id", group.getId());
        sqler.add("name", group.getName());
        sqler.add("notes", group.getNotes());
        sqler.add("ornum", group.getOrnum());
        sqler.add("crtim", group.getCrtim());
        sqler.add("uptim", group.getCrtim());
        sqler.add("cruid", group.getCruid());
        sqler.add("upuid", group.getCruid());
        sqler.add("avtag", group.getAvtag());
        sqler.add("label", group.getLabel());
        sqler.add("catid", group.getCatid());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());

        List<Object[]> insertMemberList = new ArrayList<>();
        for (Org org : group.getMembers()) {
            Object[] arr=new Object[2];
            arr[0]=group.getId();
            arr[1]=org.getId();
            insertMemberList.add(arr);
        }
        String insertMemberSql="insert into sys_org_group_org(gid,oid) values(?,?)";
        jdbcHelper.batch(insertMemberSql, insertMemberList);
    }

    public void update(SysOrgGroup group) {
        Usqler usqler = new Usqler("sys_org_group");
        usqler.addWhere("id=?", group.getId());
        usqler.add("name", group.getName());
        usqler.add("notes", group.getNotes());
        usqler.add("ornum", group.getOrnum());
        usqler.add("uptim", group.getUptim());
        usqler.add("upuid", group.getUpuid());
        usqler.add("avtag", group.getAvtag());
        usqler.add("label", group.getLabel());
        usqler.add("catid", group.getCatid());
        jdbcHelper.getTp().update(usqler.getSql(), usqler.getParams());

        jdbcHelper.update("delete from sys_org_group_org where gid = ?", group.getId());
        List<Object[]> insertMemberList = new ArrayList<>();
        for (Org org : group.getMembers()) {
            Object[] arr=new Object[2];
            arr[0]=group.getId();
            arr[1]=org.getId();
            insertMemberList.add(arr);
        }
        String insertMemberSql="insert into sys_org_group_org(gid,oid) values(?,?)";
        jdbcHelper.batch(insertMemberSql, insertMemberList);
    }

    public void deleteById(String id) {
        String sql = "delete from sys_org_group where id=?";
        jdbcHelper.getTp().update(sql, id);

        String sql2 = "delete from sys_org_group_org where gid=?";
        jdbcHelper.getTp().update(sql2, id);
    }

    private final JdbcHelper jdbcHelper;
}
