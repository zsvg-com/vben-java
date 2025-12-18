package vben.base.sys.org.root;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import vben.common.jdbc.sqler.JdbcHelper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class OrgDao {

    private final JdbcHelper jdbcHelper;

    public Org findById(String id) {
        String sql = "select * from sys_org where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(Org.class), id);
    }

    public void insert(Org org)  {

        String sql="insert into sys_org(id,name,type) values(?,?,?)";
        jdbcHelper.getTp().update(sql, org.getId(), org.getName(), org.getType());

    }

    public void update(Org org)  {

        String sql="update sys_org set name=?,type=? where id=?";
        jdbcHelper.getTp().update(sql, org.getName(), org.getType(), org.getId());

    }

    public void deleteById(String id) {
        String sql = "delete from sys_org where id=?";
        jdbcHelper.getTp().update(sql, id);
    }

}
