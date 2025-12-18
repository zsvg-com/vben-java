package vben.base.tool.dict.data;

import lombok.RequiredArgsConstructor;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.jdbc.sqler.Usqler;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ToolDictDataDao {

    public PageData findPageData(Sqler sqler) {
        return jdbcHelper.findPageData(sqler);
    }

    public ToolDictData findById(Long id) {
        String sql = "select * from tool_dict_data where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(ToolDictData.class), id);
    }

    public void insert(ToolDictData main) {
        Isqler isqler = new Isqler("tool_dict_data");
        isqler.add("id", main.getId());
        isqler.add("dalab", main.getDalab());
        isqler.add("daval", main.getDaval());
        isqler.add("dicid", main.getDicid());
        isqler.add("notes", main.getNotes());
        isqler.add("crtim", main.getCrtim());
        isqler.add("uptim", main.getCrtim());
        isqler.add("avtag", main.getAvtag());
        isqler.add("ornum", main.getOrnum());
        isqler.add("detag", main.getDetag());
        isqler.add("shsty", main.getShsty());
        jdbcHelper.getTp().update(isqler.getSql(), isqler.getParams());
    }

    public void update(ToolDictData main) {
        Usqler usqler = new Usqler("tool_dict_data");
        usqler.addWhere("id=?", main.getId());
        usqler.add("dalab", main.getDalab());
        usqler.add("daval", main.getDaval());
        usqler.add("dicid", main.getDicid());
        usqler.add("notes", main.getNotes());
        usqler.add("uptim", main.getUptim());
        usqler.add("avtag", main.getAvtag());
        usqler.add("ornum", main.getOrnum());
        usqler.add("detag", main.getDetag());
        usqler.add("shsty", main.getShsty());
        jdbcHelper.getTp().update(usqler.getSql(), usqler.getParams());
    }

    public void deleteById(Long id) {
        jdbcHelper.update("delete from tool_dict_data where id=?", id);
    }

    private final JdbcHelper jdbcHelper;

}
