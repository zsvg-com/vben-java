package vben.common.mybatis.core.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.mybatis.core.domain.BaseMainEntity;
import vben.common.satoken.utils.LoginHelper;

import java.util.*;

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

    //查询MapList数据
    @Transactional(readOnly = true)
    public List<Map<String, Object>> findMapList(Sqler sqler) {
        return jdbcHelper.findMapList(sqler);
    }

    //根据ID判断数据库实体是否存在
//    @Transactional(readOnly = true)
//    public boolean existsById(Long id) {
//        return mapper.exists(id);
//    }

    //查询单个实体详细信息
//    @Transactional(readOnly = true)
    public T select(Long id) {
        T main= mapper.selectById(id);
        //下面的可优化，从缓存读取
        String sql = "select name from sys_org where id = ?";
        if(main.getCruid()!=null){
            main.setCruna(jdbcHelper.findString(sql, main.getCruid()));
        }
        if(main.getUpuid()!=null){
            main.setUpuna(jdbcHelper.findString(sql, main.getUpuid()));
        }
        return main;
    }

    //查询所有记录
//    @Transactional(readOnly = true)
//    public List<T> findAll() {
//        return mapper.findAll();
//    }

    //---------------------------------------增删改-------------------------------------
    //新增
    public Long insert(T main) {
        if (main.getId() == null) {
            main.setId(IdUtils.getSnowflakeNextId());
        }
        main.setCrtim(new Date());
        main.setUptim(main.getCrtim());
        if (main.getCruid() == null) {
            String UserId=LoginHelper.getUserId();
            main.setCruid(UserId);
            main.setUpuid(UserId);
        }
        mapper.insert(main);
        return main.getId();
    }

    //修改
    public Long update(T main) {
        main.setUptim(new Date());
        main.setUpuid(LoginHelper.getUserId());
        mapper.updateById(main);
        return main.getId();
    }

    //删除
    public Integer delete(Long[] ids) {
        return mapper.deleteByIds(Arrays.asList(ids));
    }

    @Autowired
    protected JdbcHelper jdbcHelper;

    @Setter
    protected BaseMapper<T> mapper;

}
