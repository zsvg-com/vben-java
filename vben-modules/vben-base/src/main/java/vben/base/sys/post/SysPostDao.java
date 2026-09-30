package vben.base.sys.post;

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
public class SysPostDao {

    public SysPost findById(String id) {
        String sql = "select * from sys_post where id = ?";
        SysPost post= jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(SysPost.class), id);
        String sql2 = "select t.id,t.name from sys_actor t inner join sys_post_actor a on a.aid=t.id where a.pid = ?";
        List<Actor> users = jdbcHelper.getTp().query(sql2, new BeanPropertyRowMapper<>(Actor.class), id);
        post.setUsers(users);
        return post;
    }

    public void insert(SysPost post) {
        Isqler sqler = new Isqler("sys_post");
        sqler.add("id", post.getId());
        sqler.add("name", post.getName());
        sqler.add("orgid", post.getOrgid());
        sqler.add("notes", post.getNotes());
        sqler.add("ornum", post.getOrnum());
        sqler.add("crtim", post.getCrtim());
        sqler.add("uptim", post.getCrtim());
        sqler.add("cruid", post.getCruid());
        sqler.add("upuid", post.getCruid());
        sqler.add("avtag", post.getAvtag());
        sqler.add("label", post.getLabel());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());

        List<Object[]> insertMemberList = new ArrayList<>();
        for (Actor user : post.getUsers()) {
            Object[] arr=new Object[2];
            arr[0]=post.getId();
            arr[1]=user.getId();
            insertMemberList.add(arr);
        }
        String insertMemberSql="insert into sys_post_actor(pid,aid) values(?,?)";
        jdbcHelper.batch(insertMemberSql, insertMemberList);
    }

    public void update(SysPost post) {
        Usqler sqler = new Usqler("sys_post");
        sqler.addWhere("id=?", post.getId());
        sqler.add("name", post.getName());
        sqler.add("orgid", post.getOrgid());
        sqler.add("notes", post.getNotes());
        sqler.add("ornum", post.getOrnum());
        sqler.add("uptim", post.getUptim());
        sqler.add("upuid", post.getUpuid());
        sqler.add("avtag", post.getAvtag());
        sqler.add("label", post.getLabel());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());

        jdbcHelper.update("delete from sys_post_actor where pid = ?", post.getId());
        List<Object[]> insertMemberList = new ArrayList<>();
        for (Actor user : post.getUsers()) {
            Object[] arr=new Object[2];
            arr[0]=post.getId();
            arr[1]=user.getId();
            insertMemberList.add(arr);
        }
        String insertMemberSql="insert into sys_post_actor(pid,aid) values(?,?)";
        jdbcHelper.batch(insertMemberSql, insertMemberList);
    }

    public void deleteById(String id) {
        String sql = "delete from sys_post where id=?";
        jdbcHelper.getTp().update(sql, id);

        String sql2 = "delete from sys_post_actor where pid=?";
        jdbcHelper.getTp().update(sql2, id);
    }

    private final JdbcHelper jdbcHelper;
}
