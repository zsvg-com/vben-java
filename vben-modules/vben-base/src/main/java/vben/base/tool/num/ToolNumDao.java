package vben.base.tool.num;

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
public class ToolNumDao {

    public PageData findPageData(Sqler sqler) {
        return jdbcHelper.findPageData(sqler);
    }

    public ToolNum findById(String id) {
        String sql = "select * from tool_num where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(ToolNum.class), id);
    }

    public void insert(ToolNum main) {
        Isqler isqler = new Isqler("tool_num");
        isqler.add("id", main.getId());
        isqler.add("name", main.getName());
        isqler.add("label", main.getLabel());
        isqler.add("numod", main.getNumod());
        isqler.add("nupre", main.getNupre());
        isqler.add("nflag", main.getNflag());
        isqler.add("nunex", main.getNunex());
        isqler.add("nulen", main.getNulen());
        isqler.add("cudat", main.getCudat());
        isqler.add("crtim", main.getCrtim());
        isqler.add("uptim", main.getCrtim());
        isqler.add("avtag", main.getAvtag());
        isqler.add("notes", main.getNotes());
        isqler.add("ornum", main.getOrnum());
        jdbcHelper.getTp().update(isqler.getSql(), isqler.getParams());
    }

    public void update(ToolNum main) {
        Usqler usqler = new Usqler("tool_num");
        usqler.addWhere("id=?", main.getId());
        usqler.add("name", main.getName());
        usqler.add("label", main.getLabel());
        usqler.add("numod", main.getNumod());
        usqler.add("nupre", main.getNupre());
        usqler.add("nflag", main.getNflag());
        usqler.add("nunex", main.getNunex());
        usqler.add("nulen", main.getNulen());
        usqler.add("cudat", main.getCudat());
        usqler.add("uptim", main.getUptim());
        usqler.add("avtag", main.getAvtag());
        usqler.add("notes", main.getNotes());
        usqler.add("ornum", main.getOrnum());
        jdbcHelper.getTp().update(usqler.getSql(), usqler.getParams());
    }

    public void deleteById(String id) {
        jdbcHelper.update("delete from tool_num where id=?", id);
    }

    public boolean existsById(String id) {
        String sql="select count(1) from tool_num where id=?";
        Integer count = jdbcHelper.getTp().queryForObject(sql,Integer.class,id);
        return count != null && count != 0;
    }

    private final JdbcHelper jdbcHelper;


}
