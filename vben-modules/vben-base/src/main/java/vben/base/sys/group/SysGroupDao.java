package vben.base.sys.group;

import lombok.RequiredArgsConstructor;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.base.sys.actor.Actor;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SysGroupDao {

    public SysGroup findById(String id) {
        String sql = "select * from sys_group where id = ?";
        SysGroup group = jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(SysGroup.class), id);
        String sql2 = "select t.id,t.name from sys_actor t inner join sys_group_actor a on a.aid=t.id where a.gid = ?";
        List<Actor> users = jdbcHelper.getTp().query(sql2, new BeanPropertyRowMapper<>(Actor.class), id);
        group.setMembers(users);
        return group;
    }


    public void insert(SysGroup group) {
        Isqler sqler = new Isqler("sys_group");
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
        for (Actor org : group.getMembers()) {
            Object[] arr=new Object[2];
            arr[0]=group.getId();
            arr[1]=org.getId();
            insertMemberList.add(arr);
        }
        String insertMemberSql="insert into sys_group_actor(gid,aid) values(?,?)";
        jdbcHelper.batch(insertMemberSql, insertMemberList);
    }

    public void update(SysGroup group) {
        Usqler usqler = new Usqler("sys_group");
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

        jdbcHelper.update("delete from sys_group_actor where gid = ?", group.getId());
        List<Object[]> insertMemberList = new ArrayList<>();
        for (Actor org : group.getMembers()) {
            Object[] arr=new Object[2];
            arr[0]=group.getId();
            arr[1]=org.getId();
            insertMemberList.add(arr);
        }
        String insertMemberSql="insert into sys_group_actor(gid,aid) values(?,?)";
        jdbcHelper.batch(insertMemberSql, insertMemberList);
    }

    public void deleteById(String id) {
        String sql = "delete from sys_group where id=?";
        jdbcHelper.getTp().update(sql, id);

        String sql2 = "delete from sys_group_actor where gid=?";
        jdbcHelper.getTp().update(sql2, id);
    }

    private final JdbcHelper jdbcHelper;
}
