package vben.base.sys.tenant;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.jdbc.sqler.Usqler;

@Component
@RequiredArgsConstructor
public class SysTenantDao {

    private static final String table = "sys_tenant";

    public PageData findPageData(Sqler sqler) {
        return jdbcHelper.findPageData(sqler);
    }

    public SysTenant findById(Long id) {
        String sql = "select * from "+table+" where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(SysTenant.class), id);
    }

    public void insert(SysTenant main) {
        Isqler sqler = new Isqler(table);
        sqler.add("id", main.getId());
        sqler.add("name", main.getName());
        sqler.add("notes", main.getNotes());
        sqler.add("crtim", main.getCrtim());
        sqler.add("uptim", main.getCrtim());
        sqler.add("cruid", main.getCruid());
        sqler.add("upuid", main.getCruid());
        sqler.add("avtag", main.getAvtag());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void update(SysTenant main) {
        Usqler sqler = new Usqler(table);
        sqler.addWhere("id=?", main.getId());
        sqler.add("name", main.getName());
        sqler.add("notes", main.getNotes());
        sqler.add("uptim", main.getUptim());
        sqler.add("upuid", main.getUpuid());
        sqler.add("avtag", main.getAvtag());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());

    }

    public void deleteById(Long id) {
        jdbcHelper.update("delete from "+table+" where id=?", id);
    }

    private final JdbcHelper jdbcHelper;

}
