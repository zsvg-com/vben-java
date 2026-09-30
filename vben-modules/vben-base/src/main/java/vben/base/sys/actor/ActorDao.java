package vben.base.sys.actor;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import vben.common.jdbc.sqler.JdbcHelper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ActorDao {

    private final JdbcHelper jdbcHelper;

    public Actor findById(String id) {
        String sql = "select * from sys_actor where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(Actor.class), id);
    }

    public void insert(Actor org)  {

        String sql="insert into sys_actor(id,name,type) values(?,?,?)";
        jdbcHelper.getTp().update(sql, org.getId(), org.getName(), org.getType());

    }

    public void update(Actor org)  {

        String sql="update sys_actor set name=?,type=? where id=?";
        jdbcHelper.getTp().update(sql, org.getName(), org.getType(), org.getId());

    }

    public void deleteById(String id) {
        String sql = "delete from sys_actor where id=?";
        jdbcHelper.getTp().update(sql, id);
    }

}
