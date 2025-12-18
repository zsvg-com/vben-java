package vben.common.mybatis.core.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.dto.*;
import vben.common.jdbc.root.Db;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.mybatis.core.domain.BaseCateEntity;
import vben.common.satoken.utils.LoginHelper;

import java.util.*;

//主数据Service基类，提供主数据Entity增删改查的通用方法
@Transactional(rollbackFor = Exception.class)
public abstract class BaseCateService<T extends BaseCateEntity> {

    //---------------------------------------查询-------------------------------------
    @Transactional(readOnly = true)
    public List<Ltree> findTreeList(Sqler sqler, String name, Long id) {
        sqler.addSelect("pid");
        sqler.addWhere("t.avtag = "+ Db.True);
        if (id != null) {
            sqler.addWhere("t.tier not like ?", "%_" + id + "_%");
            sqler.addWhere("t.id <> ?", id);
        }
        sqler.addLike("t.name", name);
        sqler.addOrder("t.ornum");
        return jdbcHelper.findLtreeList(sqler);
    }

    @Transactional(readOnly = true)
    public List<T> findList(Sqler sqler, Class<T> clazz) {
        sqler.selectCUinfo().addSelect("t.pid,t.ornum");
        sqler.addOrder("t.ornum");
        List<T> list = jdbcHelper.getTp().query(sqler.getSql(), sqler.getParams(),
            new BeanPropertyRowMapper<>(clazz));
        return list;
    }

    public List<SidNamePid> findInpList(String table, String name) {
        Sqler sqler = new Sqler(table);
        sqler.addSelect("t.pid");
        sqler.addLike("t.name", name);
        sqler.addOrder("t.ornum");
        return jdbcHelper.getTp().query(sqler.getSql(), new BeanPropertyRowMapper<>(SidNamePid.class));
    }

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
        sqler.selectCUinfo().addSelect("t.pid,t.ornum");
        sqler.addOrder("t.ornum");
        return jdbcHelper.findMapList(sqler);
    }

    //根据ID判断数据库实体是否存在
//    @Transactional(readOnly = true)
//    public boolean existsById(Long id) {
//        return mapper.exists(id);
//    }

    //查询单个实体详细信息
    @Transactional(readOnly = true)
    public T select(Long id) {
        T cate= mapper.selectById(id);
        //下面的可优化，从缓存读取
        String sql = "select name from sys_org where id = ?";
        if(cate.getCruid()!=null){
            cate.setCruna(jdbcHelper.findString(sql, cate.getCruid()));
        }
        if(cate.getUpuid()!=null){
            cate.setUpuna(jdbcHelper.findString(sql, cate.getUpuid()));
        }
        return cate;
    }

    //查询所有记录
//    @Transactional(readOnly = true)
//    public List<T> findAll() {
//        return mapper.findAll();
//    }

    //---------------------------------------增删改-------------------------------------
    //新增
    public Long insert(T cate,String table) {
        if(cate.getPid()==null){
            cate.setPid(0L);
        }
        if(cate.getOrnum()==null){
            cate.setOrnum(getCount(cate.getPid(), table) + 1);
        }
        if (cate.getId() == null) {
            cate.setId(IdUtils.getSnowflakeNextId());
        }
        cate.setCrtim(new Date());
        cate.setUptim(cate.getCrtim());
        if (cate.getCruid() == null) {
            String UserId=LoginHelper.getUserId();
            cate.setCruid(UserId);
            cate.setUpuid(UserId);
        }
        if (cate.getPid() != 0) {
            T parent = mapper.selectById(cate.getPid());
            cate.setTier( parent.getTier()  + cate.getId() + "_");
        } else {
            cate.setTier("_" + cate.getId() + "_");
        }
        mapper.insert(cate);
        return cate.getId();
    }

    //修改
    public Long update(T cate) {
        if(cate.getPid()==null){
            cate.setPid(0L);
        }
        cate.setUptim(new Date());
        cate.setUpuid(LoginHelper.getUserId());
        mapper.updateById(cate);
        return cate.getId();
    }

    //级联更新
    public Long update(T cate, String table) {
        if(cate.getPid()==null){
            cate.setPid(0L);
        }
        cate.setUptim(new Date());
        cate.setUpuid(LoginHelper.getUserId());
        Long newPid = null;
        if (cate.getPid() != 0L) {
            newPid = cate.getPid();
        }
        String ptSql="select pid,tier from "+table+" where id=?";
        LpidTier oldCate = jdbcHelper.getTp().queryForObject(ptSql, new BeanPropertyRowMapper<>(LpidTier.class), cate.getId());
        Long oldPid = 0L;
        if (oldCate.getPid() != 0L) {
            oldPid = oldCate.getPid();
        }
        String oldTier = oldCate.getTier();
        if (Objects.equals(oldPid, newPid)) {//如果父节点没有变，则只更新自己
//            System.out.println("父节点没有变");
            cate.setTier(oldTier);
            mapper.updateById(cate);
        } else {//如果父节点变了，则另外需要更新自己孩子节点的tier层级
            String newTier ="_" + cate.getId() + "_";
            if (cate.getPid() != 0L) {
//                System.out.println("父节点变成其他点了");
                T newParentCate = mapper.selectById(cate.getPid());
                newTier = newParentCate.getTier() + cate.getId()+ "_";
            }
            cate.setTier(newTier);
            mapper.updateById(cate);
            //更新子节点
            String sql = "select id,tier as name from " + table + " where tier like ? and id<>?";
//            System.out.println(sql);
            List<SidName> list = jdbcHelper.findSidNameList(sql, oldTier + "%", cate.getId());

            String updateSql = "update " + table + " set tier=? where id=?";
            List<Object[]> updateList = new ArrayList<Object[]>();
            for (SidName ztwo : list) {
                Object[] arr = new Object[2];
                arr[0] = ztwo.getName().replace(oldTier, newTier);
                arr[1] = ztwo.getId();
                updateList.add(arr);
            }
            jdbcHelper.batch(updateSql, updateList);
        }
        return cate.getId();
    }

    //删除
    public Integer delete(Long[] ids) {
        return mapper.deleteByIds(Arrays.asList(ids));
    }

    //---------------------------------------构建树形结构辅助方法-------------------------------------

    //使用递归方法建树
    public List<T> buildByRecursive(List<T> nodes) {
        List<T> list = new ArrayList<>();
        for (T node : nodes) {
            if (node.getPid() == 0L) {
                list.add(findChildrenByTier(node, nodes));
            } else {
                boolean flag = false;
                for (BaseCateEntity node2 : nodes) {
                    if (Objects.equals(node.getPid(),(node2.getId()))) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    list.add(findChildrenByTier(node, nodes));
                }
            }
        }
        return list;
    }

    //递归查找子节点
    private T findChildrenByTier(T node, List<T> nodes) {
        for (T item : nodes) {
            if (Objects.equals(node.getId(),item.getPid())) {
                if (node.getChildren() == null) {
                    node.setChildren(new ArrayList<>());
                }
                node.getChildren().add(findChildrenByTier(item, nodes));
            }
        }
        return node;
    }

    //---------------------------------------bean注入-------------------------------------
    public Integer getCount(Long pid, String table) {
        if (pid != null) {
            String countSql = "select count(1) from " + table + " where pid=?";
            Integer count = jdbcHelper.getTp().queryForObject(countSql, Integer.class, pid);
            if (count == null) {
                count = 0;
            }
            return count;
        } else {
            String countSql = "select count(1) from " + table + " where pid is null";
            Integer count = jdbcHelper.getTp().queryForObject(countSql, Integer.class);
            if (count == null) {
                count = 0;
            }
            return count;
        }
    }

    /**
     * 节点移动
     * @param bo
     * @param table
     * draid 拖动节点ID
     * droid 放下时目标节点ID
     */
    public void move(Lmove bo, String table) {
        T dragCate = mapper.selectById(bo.getDraid());

        //节点移动后，节点同级的下方节点ornum--
        String sql = "select id,ornum from " + table + " where ornum>? and pid=?";
        List<SidOrnum> list2 = jdbcHelper.getTp().query(sql, new BeanPropertyRowMapper<>(SidOrnum.class), dragCate.getOrnum(), dragCate.getPid());
        String updateSql = "update " + table + " set ornum=? where id=?";
        List<Object[]> updateList = new ArrayList<>();
        for (SidOrnum sidOrnum : list2) {
            Object[] arr = new Object[2];
            arr[0] = sidOrnum.getOrnum() - 1;
            arr[1] = sidOrnum.getId();
            updateList.add(arr);
        }
        jdbcHelper.batch(updateSql, updateList);

        if ("inner".equals(bo.getType())) {
            Integer count = getCount(bo.getDroid(), table);
            dragCate.setPid(bo.getDroid());
            dragCate.setOrnum(count + 1);
        } else if ("before".equals(bo.getType())) {
            T dropCate = mapper.selectById(bo.getDroid());
            Integer dropCateOrnum = dropCate.getOrnum();
            //目标节点与同级下方节点ornum++
            String sql3 = "select id,ornum from " + table + " where ornum>=? and pid=?";
            List<SidOrnum> list3 = jdbcHelper.getTp().query(sql3, new BeanPropertyRowMapper<>(SidOrnum.class), dropCate.getOrnum(), dropCate.getPid());
            String updateSql3 = "update " + table + " set ornum=? where id=?";
            List<Object[]> updateList3 = new ArrayList<>();
            for (SidOrnum sidOrnum : list3) {
                Object[] arr = new Object[2];
                arr[0] = sidOrnum.getOrnum() + 1;
                arr[1] = sidOrnum.getId();
                updateList3.add(arr);
            }
            jdbcHelper.batch(updateSql3, updateList3);
            dragCate.setPid(dropCate.getPid());
            dragCate.setOrnum(dropCateOrnum);
        } else if ("after".equals(bo.getType())) {
            T dropCate = mapper.selectById(bo.getDroid());
            Integer dropCateOrnum = dropCate.getOrnum();
            //目标节点同级下方节点ornum++
            String sql4 = "select id,ornum from " + table + " where ornum>? and pid=?";
            List<SidOrnum> list4 = jdbcHelper.getTp().query(sql4, new BeanPropertyRowMapper<>(SidOrnum.class), dropCate.getOrnum(), dropCate.getPid());
            String updateSql4 = "update " + table + " set ornum=? where id=?";
            List<Object[]> updateList4 = new ArrayList<>();
            for (SidOrnum sidOrnum : list4) {
                Object[] arr = new Object[2];
                arr[0] = sidOrnum.getOrnum() + 1;
                arr[1] = sidOrnum.getId();
                updateList4.add(arr);
            }
            jdbcHelper.batch(updateSql4, updateList4);
            dragCate.setPid(dropCate.getPid());
            dragCate.setOrnum(dropCateOrnum+1);
        }
        update(dragCate, table);
    }

    @Autowired
    protected JdbcHelper jdbcHelper;

    @Setter
    protected BaseMapper<T> mapper;

}
