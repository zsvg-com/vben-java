package vben.common.jpa.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;
import vben.common.core.domain.model.LoginUser;
import vben.common.core.exception.ServiceException;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.dto.LidName;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.jpa.entity.BaseMainEntity;
import vben.common.satoken.utils.LoginHelper;

import java.util.Date;
import java.util.List;
import java.util.Map;

//主数据Service基类，提供主数据Entity增删改查的通用方法
@Transactional(rollbackFor = Exception.class)
public abstract class BaseMainService<T extends BaseMainEntity> {

    //---------------------------------------查询-------------------------------------
    //查询分页
    @Transactional(readOnly = true)
    public PageData findPageData(Sqler sqler) {
        if (sqler.getAutoType() == 1) {
            sqler.selectCUinfo();
            sqler.addOrder("t.crtim desc");
        }
        return jdbcHelper.findPageData(sqler);
    }

    @Transactional(readOnly = true)
    public PageData findPageDataByPerm(Sqler sqler) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        String userId = loginUser.getUserId();
        Integer dataScope = loginUser.getDataScope();
        if (!LoginHelper.isSuperAdmin(loginUser.getUserId())) {
            switch (dataScope) {
                case 1:  //仅本人数据权限
                    sqler.addEqual("t.bluid", userId);
                    break;
                case 2:  //本组织数据权限
                    sqler.addEqual("t.bloid", loginUser.getOrgid());
                    break;
                case 4:  //本组织及以下数据权限
                    sqler.addWhere("t.bloid in (select id from sys_org where tier like ?)",
                        "%" + loginUser.getOrgid() + "%");
                    break;
                case 8:  //本组织及以下或本人数据权限
                    sqler.addWhere("(t.bluid=? or t.bloid in (select id from sys_org where tier like ?))",
                        loginUser.getUserId(), "%" + loginUser.getOrgid() + "%");
                    break;
                case 16: //自定义数据权限
                    String aids = cacheHandler.getAids(userId);
                    sqler.addWhere("(t.bluid=? or t.bloid in " +
                        "(select o.aid from sys_role_org o inner join sys_role r on r.id=o.rid inner join sys_role_actor a on a.rid=r.id " +
                        "where r.scope=16 and a.aid in (" + aids + ")))", loginUser.getUserId());
                    break;
                case 32: //全部数据权限
                    break;
            }
        }
        if (sqler.getAutoType() == 1) {
            sqler.selectCUinfo();
            sqler.addOrder("t.crtim desc");
        }
        System.out.println(sqler.getSql());
        return jdbcHelper.findPageData(sqler);
    }

    @Transactional(readOnly = true)
    public void checkPerm(String bluid, String bloid) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        if (LoginHelper.isSuperAdmin(loginUser.getUserId())) {
            return;
        }
        String userId = loginUser.getUserId();
        Integer dataScope = loginUser.getDataScope();
        switch (dataScope) {
            case 1:  //仅本人数据权限
                if (userId.equals(bluid)) {
                    return;
                }
                break;
            case 2:  //本组织数据权限
                if (loginUser.getOrgid().equals(bloid)) {
                    return;
                }
                break;
            case 4:  //本组织及以下数据权限
                String sql = "select id from sys_org where tier like ?";
                List<String> slist = jdbcHelper.findSlist(sql, "%" + loginUser.getOrgid() + "%");
                if (!slist.isEmpty() && slist.contains(bloid)) {
                    return;
                }
                break;
            case 8:  //本组织及以下或本人数据权限
                if (loginUser.getUserId().equals(bluid)) {
                    return;
                }
                String sql2 = "select id from sys_org where tier like ?";
                List<String> slist2 = jdbcHelper.findSlist(sql2, "%" + loginUser.getOrgid() + "%");
                if (!slist2.isEmpty() && slist2.contains(bloid)) {
                    return;
                }
            case 16: //自定义数据权限
                if (userId.equals(bluid)) {
                    return;
                }
                String aids = cacheHandler.getAids(userId);
                String sql3 = "select o.aid from sys_role_org o inner join sys_role r on r.id=o.rid inner join sys_role_actor a on a.rid=r.id " +
                    "where r.scope=16 and a.aid in (" + aids + ")";
                List<String> slist3 = jdbcHelper.findSlist(sql3);
                if (!slist3.isEmpty() && slist3.contains(bloid)) {
                    return;
                }
                break;
            case 32: //全部数据权限
                return;
        }
        throw new ServiceException("没有此数据权限");
    }

    @Transactional(readOnly = true)
    public PageData findPageDataByViewPerm(Sqler sqler,String table) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        String userId = loginUser.getUserId();
        if (!LoginHelper.isSuperAdmin(loginUser.getUserId())) {
            String aids = cacheHandler.getAids(userId);
            sqler.addWhere("EXISTS (SELECT 1 FROM "+table+" v WHERE v.mid = t.id AND v.aid in (" + aids + "))");
        }
        if (sqler.getAutoType() == 1) {
            sqler.selectCUinfo();
            sqler.addOrder("t.crtim desc");
        }
        System.out.println(sqler.getSql());
        return jdbcHelper.findPageData(sqler);
    }

    @Transactional(readOnly = true)
    public void checkViewPerm(Long id,String table) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        if (LoginHelper.isSuperAdmin(loginUser.getUserId())) {
            return;
        }
        String userId = loginUser.getUserId();
        String aids = cacheHandler.getAids(userId);
        String sql = "SELECT count(1) FROM "+table+" v WHERE v.mid = ? AND v.aid in (" + aids + ")";
        Integer count = jdbcHelper.findInteger(sql, id);
        if (count != null && count >= 1) {
            return;
        }
        throw new ServiceException("没有此数据查看权限");
    }

    @Transactional(readOnly = true)
    public void checkEditPerm(Long id,String table) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        if (LoginHelper.isSuperAdmin(loginUser.getUserId())) {
            return;
        }
        String userId = loginUser.getUserId();
        String aids = cacheHandler.getAids(userId);
        String sql = "SELECT count(1) FROM "+table+" e WHERE e.mid = ? AND e.aid in (" + aids + ")";
        Integer count = jdbcHelper.findInteger(sql, id);
        if (count != null && count >= 1) {
            return;
        }
        throw new ServiceException("没有此数据编辑权限");
    }

    //查询MapList数据
    @Transactional(readOnly = true)
    public List<Map<String, Object>> findMapList(Sqler sqler) {
        return jdbcHelper.findMapList(sqler);
    }

    //根据ID判断数据库实体是否存在
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return repo.existsById(id);
    }

    //查询单个实体详细信息
    @Transactional(readOnly = true)
    public T select(Long id) {
        T main = repo.findById(id).get();
        //下面的可优化，从缓存读取
        String sql = "select name from sys_actor where id = ?";
        if (main.getCruid() != null) {
            main.setCruna(jdbcHelper.findString(sql, main.getCruid()));
        }
        if (main.getUpuid() != null) {
            main.setUpuna(jdbcHelper.findString(sql, main.getUpuid()));
        }
        return main;
    }


    //查询所有记录
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return repo.findAll();
    }

    //查询所有记录
    @Transactional(readOnly = true)
    public List<LidName> findIdNameList(Sqler sqler) {
        return jdbcHelper.getTp().query(sqler.getSql(), new BeanPropertyRowMapper<>(LidName.class), sqler.getParams());
    }

    //---------------------------------------增删改-------------------------------------
    //新增
    public Long insert(T main) {
        if (main.getId() == null) {
            main.setId(IdUtils.getSnowflakeNextId());
        }
//        main.setCrtim(new Date());
        main.setUptim(main.getCrtim());

        if (main.getCruid() == null) {
            String UserId = LoginHelper.getUserId();
            main.setCruid(UserId);
            main.setUpuid(UserId);
        }
        repo.save(main);
        return main.getId();
    }

    //修改
    public Long update(T main) {
        main.setUptim(new Date());
        main.setUpuid(LoginHelper.getUserId());
        repo.save(main);
        return main.getId();
    }

    //删除
    public int delete(Long[] ids) {
        for (Long id : ids) {
            repo.deleteById(id);
        }
        return ids.length;
    }

    //新增或修改
    public T save(T t) {
        return repo.save(t);
    }


    //---------------------------------------bean注入-------------------------------------
    @Autowired
    protected CacheHandler cacheHandler;

    @Autowired
    protected JdbcHelper jdbcHelper;

    @Setter
    protected JpaRepository<T, Long> repo;

}
