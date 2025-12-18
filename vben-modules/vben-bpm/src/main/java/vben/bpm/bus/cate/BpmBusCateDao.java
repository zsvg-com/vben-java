package vben.bpm.bus.cate;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.dto.Stree;
import vben.common.jdbc.root.Db;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.jdbc.sqler.Usqler;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BpmBusCateDao {

    public BpmBusCate findById(Long id) {
        String sql = "select * from bpm_bus_cate where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(BpmBusCate.class), id);
    }

    public void insert(BpmBusCate cate) {
        cate.setId(IdUtils.getSnowflakeNextId());
        Isqler isqler = new Isqler("bpm_bus_cate");
        isqler.add("id", cate.getId());
        isqler.add("name", cate.getName());
        isqler.add("pid", cate.getPid());
        isqler.add("ornum", cate.getOrnum());
        isqler.add("crtim", cate.getCrtim());
        isqler.add("cruid", cate.getCruid());
        isqler.add("upuid", cate.getCruid());
        isqler.add("uptim", cate.getCrtim());
        isqler.add("avtag", cate.getAvtag());
        isqler.add("notes", cate.getNotes());
        jdbcHelper.getTp().update(isqler.getSql(), isqler.getParams());
    }

    public void update(BpmBusCate cate)  {
        Usqler usqler = new Usqler("bpm_bus_cate");
        usqler.addWhere("id=?", cate.getId());
        usqler.add("name", cate.getName());
        usqler.add("pid", cate.getPid());
        usqler.add("ornum", cate.getOrnum());
        usqler.add("uptim", cate.getUptim());
        usqler.add("upuid", cate.getUpuid());
        usqler.add("avtag", cate.getAvtag());
        usqler.add("notes", cate.getNotes());
        jdbcHelper.getTp().update(usqler.getSql(), usqler.getParams());
    }

    public void deleteById(Long id) {
        String sql = "delete from bpm_bus_cate where id=?";
        jdbcHelper.getTp().update(sql, id);
    }

    public List<Stree> findTree(Long id){
        Sqler sqler = new Sqler("bpm_bus_cate");
        sqler.addSelect("pid");
        sqler.addWhere("t.avtag = "+ Db.True);
        if (id!=null) {
//            sqler.addWhere("t.tier not like ?", "%_" + id + "_%");
            sqler.addWhere("t.id <> ?", id);
        }
        sqler.addOrder("t.ornum");
        return jdbcHelper.findStreeList(sqler);
    }

    public List<BpmBusCate> findList(Sqler sqler) {
        return jdbcHelper.getTp().query(sqler.getSql(), sqler.getParams(),
            new BeanPropertyRowMapper<>(BpmBusCate.class));
    }

    private final JdbcHelper jdbcHelper;

}
