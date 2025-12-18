package vben.base.tool.dict.cate;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.jdbc.dto.SidName;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.jdbc.sqler.Usqler;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ToolDictCateDao {

    public List<SidName> findList() {
        Sqler sqler = new Sqler("tool_dict_cate");
        sqler.addOrder("t.ornum");
        return jdbcHelper.findSidNameList(sqler);
    }

    public ToolDictCate findById(Long id) {
        String sql = "select * from tool_dict_cate where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(ToolDictCate.class), id);
    }

    public void insert(ToolDictCate main) {
        Isqler isqler = new Isqler("tool_dict_cate");
        isqler.add("id", main.getId());
        isqler.add("name", main.getName());
        isqler.add("ornum", main.getOrnum());
        jdbcHelper.getTp().update(isqler.getSql(), isqler.getParams());
    }

    public void update(ToolDictCate main) {
        Usqler usqler = new Usqler("tool_dict_cate");
        usqler.addWhere("id=?", main.getId());
        usqler.add("name", main.getName());
        usqler.add("ornum", main.getOrnum());
        jdbcHelper.getTp().update(usqler.getSql(), usqler.getParams());
    }

    public void deleteById(Long id) {
        jdbcHelper.update("delete from tool_dict_cate where id=?", id);
    }

    private final JdbcHelper jdbcHelper;

}
