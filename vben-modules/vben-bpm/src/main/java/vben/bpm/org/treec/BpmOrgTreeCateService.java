package vben.bpm.org.treec;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.dto.SidOrnum;
import vben.common.jdbc.dto.Stree;
import vben.common.jdbc.dto.Smove;
import vben.common.jdbc.sqler.JdbcHelper;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class BpmOrgTreeCateService {

    public List<Stree> findTreeList(String id) {
        return cateDao.findTreeList(id);
    }

    @Transactional(readOnly = true)
    public BpmOrgTreeCate findById(String id) {
        return cateDao.findById(id);
    }

    public void insert(BpmOrgTreeCate cate) {
        cate.setId(IdUtils.getSnowflakeNextIdStr());
        cate.setOrnum(cateDao.getCount(cate.getPid()) + 1);
        cateDao.insert(cate);
    }

    public void update(BpmOrgTreeCate cate) {
        cateDao.update(cate);
    }

    public Integer delete(String id) {
        Integer size=1;
        String sql = "select id from bpm_org_tree_cate where pid=?";
        List<String> list = jdbcHelper.getTp().queryForList(sql, String.class, id);
        for (String l : list) {
            cateDao.deleteById(l);
            size++;
        }
        cateDao.deleteById(id);
        return size;
    }

    public void move(Smove bo) {
        BpmOrgTreeCate dragCate = cateDao.findById(bo.getDraid());
        String sql = "select id,ornum from bpm_org_tree_cate where ornum>? and pid=?";
        List<SidOrnum> list2 = jdbcHelper.getTp().query(sql,new BeanPropertyRowMapper<>(SidOrnum.class),dragCate.getOrnum(), dragCate.getPid());
        String updateSql = "update bpm_org_tree_cate set ornum=? where id=?";
        List<Object[]> updateList = new ArrayList<>();
        for (SidOrnum sidOrnum : list2) {
            Object[] arr = new Object[2];
            arr[0] = sidOrnum.getOrnum() - 1;
            arr[1] = sidOrnum.getId();
            updateList.add(arr);
        }
        jdbcHelper.batch(updateSql, updateList);
        if ("inner".equals(bo.getType())) {
            dragCate.setPid(bo.getDroid());
            Integer count = cateDao.getCount(bo.getDroid());
            dragCate.setOrnum(count + 1);
        } else if ("before".equals(bo.getType())) {
            BpmOrgTreeCate dropCate = cateDao.findById(bo.getDroid());
            dragCate.setPid(dropCate.getPid());
            dragCate.setOrnum(dropCate.getOrnum());
            String sql3 = "select id,ornum from bpm_org_tree_cate where ornum>? and pid=?";
            List<SidOrnum> list3 = jdbcHelper.getTp().query(sql3, new BeanPropertyRowMapper<>(SidOrnum.class),dropCate.getOrnum(), dropCate.getPid());
            String updateSql3 = "update bpm_org_tree_cate set ornum=? where id=?";
            List<Object[]> updateList3 = new ArrayList<>();
            for (SidOrnum sidOrnum : list3) {
                Object[] arr = new Object[2];
                arr[0] = sidOrnum.getOrnum() + 1;
                arr[1] = sidOrnum.getId();
                updateList3.add(arr);
            }
            jdbcHelper.batch(updateSql3, updateList3);
            dropCate.setOrnum(dropCate.getOrnum() + 1);
            String updateSql4 = "update bpm_org_tree_cate set ornum=? where id=?";
            jdbcHelper.update(updateSql4, dropCate.getOrnum(), dropCate.getId());
        } else if ("after".equals(bo.getType())) {
            BpmOrgTreeCate dropCate = cateDao.findById(bo.getDroid());
            dragCate.setPid(dropCate.getPid());
            dragCate.setOrnum(dropCate.getOrnum() + 1);
            String sql3 = "select id,ornum from bpm_org_tree_cate where ornum>? and pid=?";
            List<SidOrnum> list3 = jdbcHelper.getTp().query(sql3,new BeanPropertyRowMapper<>(SidOrnum.class),dropCate.getOrnum(), dropCate.getPid());
            String updateSql3 = "update bpm_org_tree_cate set ornum=? where id=?";
            List<Object[]> updateList3 = new ArrayList<>();
            for (SidOrnum sidOrnum : list3) {
                Object[] arr = new Object[2];
                arr[0] = sidOrnum.getOrnum() + 1;
                arr[1] = sidOrnum.getId();
                updateList3.add(arr);
            }
            jdbcHelper.batch(updateSql3, updateList3);
        }
        update(dragCate);
    }

    private final JdbcHelper jdbcHelper;

    private final BpmOrgTreeCateDao cateDao;
}
