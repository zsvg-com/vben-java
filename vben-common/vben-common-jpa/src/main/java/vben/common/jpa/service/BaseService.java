package vben.common.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.jpa.entity.BaseEntity;

import java.util.List;
import java.util.Map;

//简单表Service基类，提供简单Entity增删改查的通用方法
@Transactional(rollbackFor = Exception.class)
public abstract class BaseService<T extends BaseEntity> {

    //---------------------------------------查询-------------------------------------
    //查询分页
    @Transactional(readOnly = true)
    public PageData findPageData(Sqler sqler) {
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
    public Long insert(T main) {
        if (main.getId() == null||"".equals(main.getId())) {
            main.setId(IdUtils.getSnowflakeNextId());
        }
        repo.save(main);
        return main.getId();
    }

    //修改
    public Long update(T main) {
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

    protected JpaRepository<T, String> repo;

    public void setRepo(JpaRepository<T, String> repo) {
        this.repo = repo;
    }


}
