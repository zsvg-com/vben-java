package vben.common.jpa.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;
import vben.common.core.utils.StrUtils;
import vben.common.jdbc.dto.*;
import vben.common.jdbc.root.Db;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.core.utils.IdUtils;
import vben.common.jpa.entity.BaseStrCateEntity;
import vben.common.jpa.entity.SysOrg;
import vben.common.satoken.utils.LoginHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//分类Service基类，提供分类Entity增删改查的通用方法
@Transactional(rollbackFor = Exception.class)
public abstract class BaseStrCateService<T extends BaseStrCateEntity> {

    //---------------------------------------查询-------------------------------------
    //查询Tree树形集合数据
//    public List<Stree> findTreeList(String table, String name) {
//        Sqler sqler = new Sqler(table);
//        sqler.addSelect("t.pid");
//        sqler.addLike("t.name", name);
//        sqler.addOrder("t.ornum");
//        List<Stree> list = jdbcHelper.findTreeList(sqler);
//        return list;
//    }

//    @Transactional(readOnly = true)
//    public List<Stree> findTreeList(String table, String name){
//        Sqler sqler = new Sqler(table);
//        sqler.addSelect("pid");
//        sqler.addWhere("t.avtag = "+Db.True);
//        sqler.addLike("t.name", name);
//        sqler.addOrder("t.ornum");
//        return jdbcHelper.findTreeList(sqler);
//    }

    @Transactional(readOnly = true)
    public List<Stree> findTreeList(Sqler sqler, String name, String id){
        sqler.addSelect("pid");
        sqler.addWhere("t.avtag = "+ Db.True);
        if (StrUtils.isNotBlank(id)) {
            sqler.addWhere("t.tier not like ?", "%_" + id + "_%");
            sqler.addWhere("t.id <> ?", id);
        }
        sqler.addLike("t.name", name);
        sqler.addOrder("t.ornum");
        return jdbcHelper.findStreeList(sqler);
    }

    @Transactional(readOnly = true)
    public List<T> findList(Sqler sqler,Class<T> clazz) {
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
        sqler.selectCUinfo().addSelect("t.label");
        sqler.addOrder("t.ornum");
        return jdbcHelper.findPageData(sqler);
    }

    //查询SidName集合
    @Transactional(readOnly = true)
    public List<SidName> findIdNameRes(Sqler sqler) {
        return jdbcHelper.findSidNameList(sqler);
    }


    //用户编辑时选择父类别，不能选择自己及孩子节点，否则会造成无限循环
//    @Transactional(readOnly = true)
//    public List<Stree> inpChoose(String table, String id, String name){
//        Sqler sqler = new Sqler(table);
//        sqler.addSelect("pid");
//        sqler.addWhere("t.avtag = "+Db.True);
//        if (StrUtils.isNotBlank(id)) {
//            sqler.addWhere("t.tier not like ?", "%_" + id + "_%");
//            sqler.addWhere("t.id <> ?", id);
//        }
//        if(StrUtils.isNotBlank(name)){
//            sqler.addLike("t.name", name);
//        }
//        sqler.addOrder("t.ornum");
//        return jdbcDao.findInpList(sqler);
//    }


//    @Transactional(readOnly = true)
//    public R inpFind(String table){
//        Sqler sqlHelper = new Sqler(table);
//        sqlHelper.addSelect("pid");
//        sqlHelper.addWhere("t.avtag = "+Db.True);
//        sqlHelper.addOrder("t.ornum");
//        return R.ok(jdbcDao.findInpList(sqlHelper));
//    }
//
//    public R inpDele(String table, String id){
//        String sql = "select id from " + table + " where tier like ? order by tier";
//        List<String> stringList = jdbcDao.findStringList(sql, "%;" + id + ";%");
//        for (String sid : stringList) {
//            repo.deleteById(sid);
//        }
//        repo.deleteById(id);
//        return R.ok();
//    }

    //查询单个实体详细信息
    @Transactional(readOnly = true)
    public T findOne(String id) {
        return repo.findById(id).get();
    }

    //---------------------------------------增删改-------------------------------------
    //新增
    public String insert(T cate) {
        if(cate.getId()==null||"".equals(cate.getId())){
            cate.setId(IdUtils.getSnowflakeNextIdStr());
        }
        if(cate.getCrman()==null){
            cate.setCrman(new SysOrg(LoginHelper.getUserId()));
            cate.setUpman(new SysOrg(LoginHelper.getUserId()));
        }
        if (cate.getPid() != null) {
            T parent = repo.findById(cate.getPid()).get();
            cate.setTier(parent.getTier()+cate.getId()+"_");
        } else {
            cate.setTier("_"+cate.getId()+"_");
        }
        cate.setUptim(cate.getCrtim());
        repo.save(cate);
        return cate.getId();
    }

    //更新
    public String update(T cate){
        cate.setUptim(new Date());
        cate.setUpman(new SysOrg(LoginHelper.getUserId()));
        repo.save(cate);
        return cate.getId();
    }

    //级联更新
    public String update(T cate,String table,boolean tierChange){
        cate.setUptim(new Date());
        if(cate.getUpman()==null){
            cate.setUpman(new SysOrg(LoginHelper.getUserId()));
        }
        String newPid = "";
        if(cate.getPid()!=null){
            newPid=cate.getPid();
        }
        T oldCate=repo.findById(cate.getId()).get();
        String oldPid="";
        if(oldCate.getPid()!=null){
            oldPid  =oldCate.getPid();
        }
        String oldTier=oldCate.getTier();
        if(oldPid.equals(newPid)&&!tierChange){//如果父节点没有变，则只更新自己
//            System.out.println("父节点没有变");
            cate.setTier(oldTier);
            repo.save(cate);
        }else{//如果父节点变了，则另外需要更新自己孩子节点的tier层级
            String newTier = cate.getId();
            if(cate.getPid()!=null){
//                System.out.println("父节点变成其他点了");
                T newParentCate=repo.findById(cate.getPid()).get();
                newTier =newParentCate.getTier()+"_"+cate.getId();
            }
            cate.setTier(newTier);
            repo.save(cate);
            //更新子节点
            String sql = "select id,tier as name from " + table + " where tier like ? and id<>?";
//            System.out.println(sql);
            List<SidName> list = jdbcHelper.findSidNameList(sql, oldTier+"%",cate.getId());

            String updateSql = "update " + table + " set tier=? where id=?";
            List<Object[]> updateList = new ArrayList<Object[]>();
            for (SidName ztwo : list) {
                Object[] arr = new Object[2];
                arr[0] = ztwo.getName().replace(oldTier,newTier);
                arr[1] = ztwo.getId();
                updateList.add(arr);
            }
            jdbcHelper.batch(updateSql, updateList);
        }
        return cate.getId();
    }

    //删除
    public int delete(String[] ids) {
        for (String id : ids) {
            repo.deleteById(id);
        }
        return ids.length;
    }

    //---------------------------------------构建树形结构辅助方法-------------------------------------

    //使用递归方法建树
    public List<T> buildByRecursive(List<T> nodes) {
        List<T> list = new ArrayList<>();
        for (T node : nodes) {
            if (node.getPid() == null) {
                list.add(findChildrenByTier(node, nodes));
            } else {
                boolean flag = false;
                for (BaseStrCateEntity node2 : nodes) {
                    if (node.getPid().equals(node2.getId())) {
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
            if (node.getId().equals(item.getPid())) {
                if (node.getChildren() == null) {
                    node.setChildren(new ArrayList<>());
                }
                node.getChildren().add(findChildrenByTier(item, nodes));
            }
        }
        return node;
    }

    //---------------------------------------bean注入-------------------------------------
    public Integer getCount(String pid,String table){
        if(StrUtils.isNotBlank(pid)){
            String countSql="select count(1) from "+table+" where pid=?";
            Integer count = jdbcHelper.getTp().queryForObject(countSql,Integer.class,pid);
            if(count==null){
                count=0;
            }
            return count;
        }else{
            String countSql="select count(1) from "+table+" where pid is null";
            Integer count = jdbcHelper.getTp().queryForObject(countSql, Integer.class);
            if(count==null){
                count=0;
            }
            return count;
        }
    }

    public void move(Smove bo, String table) throws Exception {
        T dragDept= repo.findById(bo.getDraid()).get();
        List<SidOrnum> list2;
        if(StrUtils.isNotBlank(dragDept.getPid())){
            String sql = "select id,ornum from "+table+" where ornum>? and pid=?";
            list2 = jdbcHelper.getTp().query(sql,new BeanPropertyRowMapper<>(SidOrnum.class),dragDept.getOrnum(),dragDept.getPid());
        }else{
            String sql = "select id,ornum from "+table+" where ornum>? and pid is null";
            list2 = jdbcHelper.getTp().query(sql,new BeanPropertyRowMapper<>(SidOrnum.class),dragDept.getOrnum());
        }

        String updateSql = "update "+table+" set ornum=? where id=?";
        List<Object[]> updateList = new ArrayList<>();
        for (SidOrnum sidOrnum : list2) {
            Object[] arr=new Object[2];
            arr[0]= sidOrnum.getOrnum()-1;
            arr[1]= sidOrnum.getId();
            updateList.add(arr);
        }
        jdbcHelper.batch(updateSql, updateList);

        boolean tierChange=true;
        if ("inner".equals(bo.getType()))
        {
            if(bo.getDroid().equals(dragDept.getPid())){
                tierChange=false;
            }
            dragDept.setPid(bo.getDroid());
            Integer count=getCount(bo.getDroid(),table);
            dragDept.setOrnum(count+1);
        }
        else if ("before".equals(bo.getType()))
        {
            T dropDept= repo.findById(bo.getDroid()).get();
            if(dropDept.getPid()!=null){
                if(dropDept.getPid().equals(dragDept.getPid())){
                    tierChange=false;
                }
                dropDept.setPid(dropDept.getPid());
                dragDept.setPid(dropDept.getPid());;
            }else{
                if(dragDept.getPid()==null){
                    tierChange=false;
                }
                dragDept.setPid(null);
            }
            dragDept.setOrnum(dropDept.getOrnum());

            List<SidOrnum> list3;
            if(StrUtils.isNotBlank(dropDept.getPid())){
                String sql3 = "select id,ornum from "+table+" where ornum>? and pid=?";
                list3 = jdbcHelper.getTp().query(sql3,new BeanPropertyRowMapper<>(SidOrnum.class),dropDept.getOrnum(),dropDept.getPid());
            }else{
                String sql3 = "select id,ornum from "+table+" where ornum>? and pid is null";
                list3 = jdbcHelper.getTp().query(sql3,new BeanPropertyRowMapper<>(SidOrnum.class),dropDept.getOrnum());
            }
            String updateSql3 = "update "+table+" set ornum=? where id=?";
            List<Object[]> updateList3 = new ArrayList<>();
            for (SidOrnum sidOrnum : list3) {
                Object[] arr=new Object[2];
                arr[0]= sidOrnum.getOrnum()+1;
                arr[1]= sidOrnum.getId();
                updateList3.add(arr);
            }
            jdbcHelper.batch(updateSql3, updateList3);
            dropDept.setOrnum(dropDept.getOrnum()+1);
            String updateSql4 = "update "+table+" set ornum=? where id=?";
            jdbcHelper.update(updateSql4, dropDept.getOrnum(), dropDept.getId());
        }
        else if ("after".equals(bo.getType()))
        {
            T dropDept= repo.findById(bo.getDroid()).get();
            if(dropDept.getPid()!=null){
                if(dropDept.getPid().equals(dragDept.getPid())){
                    tierChange=false;
                }
                dropDept.setPid(dropDept.getPid());
            }else{
                if(dragDept.getPid()==null){
                    tierChange=false;
                }
            }
            Integer count = getCount(dropDept.getPid(),table);
            if (dragDept.getPid()!=null&&dragDept.getPid().equals(dropDept.getPid()))
            {
                dragDept.setOrnum(count);
            }
            else
            {
                dragDept.setPid(dropDept.getPid());
                dragDept.setOrnum(count+1);
            }
        }
        update(dragDept,table,tierChange);
    }


    @Autowired
    protected JdbcHelper jdbcHelper;

    @Setter
    protected JpaRepository<T,String> repo;

}
