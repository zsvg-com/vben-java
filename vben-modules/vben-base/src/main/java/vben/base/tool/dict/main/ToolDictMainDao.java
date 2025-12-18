package vben.base.tool.dict.main;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.dto.SidName;
import vben.common.jdbc.root.Db;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.jdbc.sqler.Usqler;
import vben.base.tool.dict.data.ToolDictDataVo;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ToolDictMainDao {

    public PageData findPageData(Sqler sqler) {
        return jdbcHelper.findPageData(sqler);
    }

    public List<SidName> findList() {
        Sqler sqler = new Sqler("tool_dict_main");
        sqler.addOrder("t.ornum");
        return jdbcHelper.findSidNameList(sqler);
    }

    public List<ToolDictDataVo> findDictData(String code) {
        String sql = "select t.dalab,t.daval,t.shsty from tool_dict_data t inner join tool_dict_main m on m.id=t.dicid where m.code = ? and t.avtag="+ Db.True+" order by m.ornum";
//         jdbcHelper.getTp().query(sql, new BeanPropertyRowMapper<>(ToolDictDataVo.class), code);
        return jdbcHelper.getTp().query(sql, (rs, rowNum) -> {
            ToolDictDataVo vo = new ToolDictDataVo();
            vo.setLabel(rs.getString("dalab"));
            vo.setValue(rs.getString("daval"));
            vo.setShsty(rs.getString("shsty"));
            return vo;
        },code);
    }

    public ToolDictMain findById(Long id) {
        String sql = "select * from tool_dict_main where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(ToolDictMain.class), id);
    }

    public void insert(ToolDictMain main) {
        Isqler isqler = new Isqler("tool_dict_main");
        isqler.add("id", main.getId());
        isqler.add("name", main.getName());
        isqler.add("code", main.getCode());
        isqler.add("catid", main.getCatid());
        isqler.add("notes", main.getNotes());
        isqler.add("crtim", main.getCrtim());
        isqler.add("uptim", main.getCrtim());
        isqler.add("avtag", main.getAvtag());
        isqler.add("ornum", main.getOrnum());
        jdbcHelper.getTp().update(isqler.getSql(), isqler.getParams());
    }

    public void update(ToolDictMain main) {
        Usqler usqler = new Usqler("tool_dict_main");
        usqler.addWhere("id=?", main.getId());
        usqler.add("name", main.getName());
        usqler.add("code", main.getCode());
        usqler.add("catid", main.getCatid());
        usqler.add("notes", main.getNotes());
        usqler.add("uptim", main.getUptim());
        usqler.add("avtag", main.getAvtag());
        usqler.add("ornum", main.getOrnum());
        jdbcHelper.getTp().update(usqler.getSql(), usqler.getParams());
    }

    public void deleteById(Long id) {
        jdbcHelper.update("delete from tool_dict_main where id=?", id);
    }

    private final JdbcHelper jdbcHelper;

}
