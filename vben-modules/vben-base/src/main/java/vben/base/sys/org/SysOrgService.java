package vben.base.sys.org;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.base.sys.actor.Actor;
import vben.base.sys.actor.ActorDao;
import vben.common.core.exception.ServiceException;
import vben.common.core.utils.IdUtils;
import vben.common.core.utils.StrUtils;
import vben.common.jdbc.dto.*;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.satoken.utils.LoginHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class SysOrgService {

    private final JdbcHelper jdbcHelper;

    private final SysOrgDao deptDao;

    private final ActorDao actorDao;

    @Transactional(readOnly = true)
    public List<SysOrgVo> findVoList(Sqler sqler) {
        return jdbcHelper.getTp().query(sqler.getSql(),
            new BeanPropertyRowMapper<>(SysOrgVo.class), sqler.getParams());
    }

    @Transactional(readOnly = true)
    public PageData findPageData(Sqler sqler) {
        return jdbcHelper.findPageData(sqler);
    }

    @Transactional(readOnly = true)
    public SysOrg findById(String id) {
        return deptDao.findById(id);
    }

    public void insert(SysOrg main) {
        if (main.getId() == null) {
            main.setId("d" + IdUtils.getSnowflakeNextIdStr());
        }
        main.setCruid(LoginHelper.getUserId());
        if (main.getOrnum() == null) {
            main.setOrnum(deptDao.getCount(main.getPid()) + 1);
        }
        if (main.getTier() == null) {
            if (StrUtils.isEmpty(main.getPid())) {
                main.setTier("_" + main.getId() + "_");
            } else {
                String tier = deptDao.findTier(main.getPid());
                main.setTier(tier + main.getId() + "_");
            }
        }
        Actor org = new Actor(main.getId(), main.getName(), 1);
        actorDao.insert(org);
        deptDao.insert(main);
    }

    public void update(SysOrg main) {
        if (main.getOrnum() == null) {
            main.setOrnum(0);
        }
        main.setUptim(new Date());
        main.setUpuid(LoginHelper.getUserId());

        if (StrUtils.isEmpty(main.getPid())) {
            main.setTier("_"+main.getId()+"_");
        } else {
            String tier = deptDao.findTier(main.getPid());
            main.setTier(tier + main.getId() + "_");
            String[] arr = tier.split("_");
            for (String str : arr) {
                if (main.getId().equals(str)) {
                    throw new ServiceException("父组织不能为自己或者自己的子组织");
                }
            }
        }

        Actor org = new Actor(main.getId(), main.getName(), 1);
        deptDao.update(main);
        String oldTier = jdbcHelper.findString("select tier from sys_org where id=?", main.getId());
        if (!oldTier.equals(main.getTier())) {
            dealDeptTier(oldTier, main.getTier(), main.getId());
            dealUserTier(oldTier, main.getTier());
            dealPostTier(oldTier, main.getTier());
        }
        actorDao.update(org);
    }

    private void dealDeptTier(String oldTier, String newTier, String id) {
        String sql = "select id,tier as name from sys_org where tier like ? and id<>?";
        List<SidName> list = jdbcHelper.findSidNameList(sql, oldTier + "%", id);
        String updateSql = "update sys_org set tier=? where id=?";
        List<Object[]> updateList = new ArrayList<>();
        batchReady(oldTier, newTier, list, updateList);
        jdbcHelper.batch(updateSql, updateList);
    }

    private void dealUserTier(String oldTier, String newTier) {
        String sql = "select id,tier as name from sys_user where tier like ?";
        List<SidName> list = jdbcHelper.findSidNameList(sql, oldTier + "%");
        String updateSql = "update sys_user set tier=? where id=?";
        List<Object[]> updateList = new ArrayList<>();
        batchReady(oldTier, newTier, list, updateList);
        jdbcHelper.batch(updateSql, updateList);
    }

    private void dealPostTier(String oldTier, String newTier) {
        String sql = "select id,tier as name from sys_post where tier like ?";
        List<SidName> list = jdbcHelper.findSidNameList(sql, oldTier + "%");
        String updateSql = "update sys_post set tier=? where id=?";
        List<Object[]> updateList = new ArrayList<>();
        batchReady(oldTier, newTier, list, updateList);
        jdbcHelper.batch(updateSql, updateList);
    }

    private void batchReady(String oldTier, String newTier, List<SidName> list, List<Object[]> updateList) {
        for (SidName ztwo : list) {
            Object[] arr = new Object[2];
            arr[1] = ztwo.getId();
            arr[0] = ztwo.getName().replace(oldTier, newTier);
            updateList.add(arr);
        }
    }

    public int delete(String[] ids) {
        for (String str : ids) {
            deptDao.deleteById(str);
            actorDao.deleteById(str);
        }
        return ids.length;
    }

    public List<Stree> findTree(String id) {
        return deptDao.findTree(id);
    }

    public void move(Smove bo) {
        SysOrg dragDept = deptDao.findById(bo.getDraid());
        String sql = "select id,ornum from sys_org where ornum>? and pid=?";
        List<SidOrnum> list2 = jdbcHelper.getTp().query(sql, new BeanPropertyRowMapper<>(SidOrnum.class), dragDept.getOrnum(), dragDept.getPid());
        String updateSql = "update sys_org set ornum=? where id=?";
        List<Object[]> updateList = new ArrayList<>();
        for (SidOrnum sidOrnum : list2) {
            Object[] arr = new Object[2];
            arr[0] = sidOrnum.getOrnum() - 1;
            arr[1] = sidOrnum.getId();
            updateList.add(arr);
        }
        jdbcHelper.batch(updateSql, updateList);

        if ("inner".equals(bo.getType())) {
            dragDept.setPid(bo.getDroid());
            Integer count = deptDao.getCount(bo.getDroid());
            dragDept.setOrnum(count + 1);
        } else if ("before".equals(bo.getType())) {
            SysOrg dropDept = deptDao.findById(bo.getDroid());
            dragDept.setPid(dropDept.getPid());
            dragDept.setOrnum(dropDept.getOrnum());
            String sql3 = "select id,ornum from sys_org where ornum>? and pid=?";
            List<SidOrnum> list3 = jdbcHelper.getTp().query(sql3, new BeanPropertyRowMapper<>(SidOrnum.class), dropDept.getOrnum(), dropDept.getPid());
            String updateSql3 = "update sys_org set ornum=? where id=?";
            List<Object[]> updateList3 = new ArrayList<>();
            for (SidOrnum sidOrnum : list3) {
                Object[] arr = new Object[2];
                arr[0] = sidOrnum.getOrnum() + 1;
                arr[1] = sidOrnum.getId();
                updateList3.add(arr);
            }
            jdbcHelper.batch(updateSql3, updateList3);
            dropDept.setOrnum(dropDept.getOrnum() + 1);
            String updateSql4 = "update sys_org set ornum=? where id=?";
            jdbcHelper.update(updateSql4, dropDept.getOrnum(), dropDept.getId());
        } else if ("after".equals(bo.getType())) {
            SysOrg dropDept = deptDao.findById(bo.getDroid());
            Integer count = deptDao.getCount(dropDept.getPid());
            if (dragDept.getPid() != null && dragDept.getPid().equals(dropDept.getPid())) {
                dragDept.setOrnum(count);
            } else {
                dragDept.setPid(dropDept.getPid());
                dragDept.setOrnum(count + 1);
            }
        }
        update(dragDept);
    }

}
