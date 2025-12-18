package vben.common.jpa.service;

import lombok.Setter;
import vben.common.jdbc.dto.PageData;
import vben.common.jpa.entity.BaseStrMainEntity;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.core.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import vben.common.jpa.entity.SysOrg;
import vben.common.satoken.utils.LoginHelper;

import java.util.Date;
import java.util.List;
import java.util.Map;

//主数据Service基类，提供主数据Entity增删改查的通用方法
@Transactional(rollbackFor = Exception.class)
public abstract class BaseStrMainService<T extends BaseStrMainEntity> {

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

    //查询MapList数据
    @Transactional(readOnly = true)
    public List<Map<String, Object>> findMapList(Sqler sqler) {
        return jdbcHelper.findMapList(sqler);
    }

    //根据ID判断数据库实体是否存在
    @Transactional(readOnly = true)
    public boolean existsById(String id) {
        return repo.existsById(id);
    }

    //查询单个实体详细信息
    @Transactional(readOnly = true)
    public T findOne(String id) {
        return repo.findById(id).get();
    }

    //查询所有记录
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return repo.findAll();
    }

    //---------------------------------------增删改-------------------------------------
    //新增
    public String insert(T main) {
        if (main.getId() == null || "".equals(main.getId())) {
            main.setId(IdUtils.getSnowflakeNextIdStr());
        }
//        main.setCrtim(new Date());
        main.setUptim(main.getCrtim());
        if (main.getCrman() == null) {
            main.setCrman(new SysOrg(LoginHelper.getUserId()));
        }
        repo.save(main);
        return main.getId();
    }

    //修改
    public String update(T main) {
        main.setUptim(new Date());
        main.setUpman(new SysOrg(LoginHelper.getUserId()));
        repo.save(main);
        return main.getId();
    }

    //删除
    public int delete(String[] ids) {
        for (String id : ids) {
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
    protected JdbcHelper jdbcHelper;

    @Setter
    protected JpaRepository<T, String> repo;

}
