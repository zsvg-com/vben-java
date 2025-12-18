package vben.bpm.org.node;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.common.core.utils.StrUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.dto.SidName;
import vben.common.jdbc.dto.SidOrnum;
import vben.common.jdbc.dto.Smove;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.core.utils.IdUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class BpmOrgNodeService {

    @Transactional(readOnly = true)
    public BpmOrgNode findById(String id) {
        return dao.findById(id);
    }

    public String insert(BpmOrgNode main) {
        if (main.getId() == null || "".equals(main.getId())) {
            main.setId(IdUtils.getSnowflakeNextIdStr());
        }
        if (main.getPid() == null ) {
            main.setTier( "_" + main.getId() + "_");
        } else {
            String tier = jdbcHelper.findString("select tier from bpm_org_node where id=?", main.getPid());
            main.setTier(tier + main.getId() + "_");
        }
        dao.insert(main);
        return main.getId();
    }


    public String update(BpmOrgNode main) throws Exception {
        main.setUptim(new Date());
        if (main.getPid()==null) {
            main.setTier("_"+ main.getId() + "_");
        } else {
            String tier = jdbcHelper.findString("select tier from bpm_org_node where id=?", main.getPid());
            main.setTier(tier + main.getId()+ "_");
            String[] arr = tier.split("_");
            for (String str : arr) {
                if (main.getId().equals(str)) {
                    throw new Exception("父层级不能为自己或者自己的子层级");
                }
            }
        }
        dao.update(main);
        String oldTier = jdbcHelper.findString("select tier from bpm_org_node where id=?", main.getId());
        if(!oldTier.equals(main.getTier())){
            dealTier(oldTier, main.getTier(), main.getId());
        }
        return main.getId();
    }

    public int delete(String[] ids) {
        for (String str : ids) {
            dao.deleteById(str);
        }
        return ids.length;
    }

    private void dealTier(String oldTier, String newTier, String id) {
        String sql = "select id,tier as name from bpm_org_node where tier like ? and id<>?";
        List<SidName> list = jdbcHelper.findSidNameList(sql, oldTier + "%", id);
        String updateSql = "update bpm_org_node set tier=? where id=?";
        List<Object[]> updateList = new ArrayList<Object[]>();
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

    public List<BpmOrgNode> findAll(Sqler sqler) {
        List<BpmOrgNode> list = jdbcHelper.getTp().query(sqler.getSql(), sqler.getParams(), new BeanPropertyRowMapper<>(BpmOrgNode.class));
        return list;
    }

    public PageData findPageData(Sqler sqler) {
        return jdbcHelper.findPageData(sqler);
    }

    public List<BpmOrgNode> findTree(Sqler sqler) {
        List<BpmOrgNode> list = jdbcHelper.getTp().query(sqler.getSql(), sqler.getParams(), new BeanPropertyRowMapper<>(BpmOrgNode.class));
        return buildByRecursive(list);
    }

    //使用递归方法建树
    private List<BpmOrgNode> buildByRecursive(List<BpmOrgNode> nodes) {
        List<BpmOrgNode> list = new ArrayList<>();
        for (BpmOrgNode node : nodes) {
            if (node.getPid() == null) {
                list.add(findChildrenByTier(node, nodes));
            } else {
                boolean flag = false;
                for (BpmOrgNode node2 : nodes) {
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
    private BpmOrgNode findChildrenByTier(BpmOrgNode node, List<BpmOrgNode> nodes) {
        for (BpmOrgNode item : nodes) {
            if (node.getId().equals(item.getPid())) {
                if (node.getChildren() == null) {
                    node.setChildren(new ArrayList<>());
                }
                node.getChildren().add(findChildrenByTier(item, nodes));
            }
        }
        return node;
    }


//    public List<SysOrgRoleTree> findWithoutItself(Sqler sqler, String id) {
//        List<SysOrgRoleTree> list = jdbcHelper.getTp().query(sqler.getSql(), sqler.getParams(), new BeanPropertyRowMapper<>(SysOrgRoleTree.class));
//        return buildByRecursiveWithoutItself(list, id);
//    }

    //使用递归方法建树不包含自己
//    private List<SysOrgRoleTree> buildByRecursiveWithoutItself(List<SysOrgRoleTree> nodes, String id) {
//        List<SysOrgRoleTree> list = new ArrayList<>();
//        for (SysOrgRoleTree node : nodes) {
//            if (node.getPid() == null && !node.getId().equals(id)) {
//                list.add(findChildrenByTierWithoutItself(node, nodes, id));
//            } else {
//                boolean flag = false;
//                for (SysOrgRoleTree node2 : nodes) {
//                    if (node.getPid() != null && node.getPid().equals(node2.getId())) {
//                        flag = true;
//                        break;
//                    }
//                }
//                if (!flag && !node.getId().equals(id)) {
//                    list.add(findChildrenByTierWithoutItself(node, nodes, id));
//                }
//            }
//        }
//        return list;
//    }

    //递归查找子节点不包含自己
//    private SysOrgRoleTree findChildrenByTierWithoutItself(SysOrgRoleTree node, List<SysOrgRoleTree> nodes, String id) {
//        for (SysOrgRoleTree item : nodes) {
//            if (node.getId().equals(item.getPid()) && (!item.getId().equals(id))) {
//                if (node.getChildren() == null) {
//                    node.setChildren(new ArrayList<>());
//                }
//                node.getChildren().add(findChildrenByTierWithoutItself(item, nodes, id));
//            }
//        }
//        return node;
//    }

    public Integer getCount(String pid,String treid){
        if(StrUtils.isNotBlank(pid)){
            String countSql="select count(1) from bpm_org_node where pid=?";
            Integer count = jdbcHelper.getTp().queryForObject(countSql, new Object[]{pid}, Integer.class);
            if(count==null){
                count=0;
            }
            return count;
        }else{
            String countSql="select count(1) from bpm_org_node where pid is null and treid=?";
            Integer count = jdbcHelper.getTp().queryForObject(countSql,new Object[]{treid}, Integer.class);
            if(count==null){
                count=0;
            }
            return count;
        }
    }


    public void move(Smove bo) throws Exception {
        BpmOrgNode dragDept= dao.findById(bo.getDraid());
        if(dragDept.getPid()!=null){
            dragDept.setPid(dragDept.getPid());
        }

        List<SidOrnum> list2;

        if(StrUtils.isNotBlank(dragDept.getPid())){
            String sql = "select id,ornum from sys_org_role_node where ornum>? and pid=?";
            list2 = jdbcHelper.getTp().query(sql,new Object[]{dragDept.getOrnum(),dragDept.getPid()},
                    new BeanPropertyRowMapper<>(SidOrnum.class));
        }else{
            String sql = "select id,ornum from sys_org_role_node where ornum>? and treid=? and pid is null";
            list2 = jdbcHelper.getTp().query(sql,new Object[]{dragDept.getOrnum(),dragDept.getTreid()},
                    new BeanPropertyRowMapper<>(SidOrnum.class));
        }

        String updateSql = "update sys_org_role_node set ornum=? where id=?";
        List<Object[]> updateList = new ArrayList<>();
        for (SidOrnum sidOrnum : list2) {
            Object[] arr=new Object[2];
            arr[0]= sidOrnum.getOrnum()-1;
            arr[1]= sidOrnum.getId();
            updateList.add(arr);
        }
        jdbcHelper.batch(updateSql, updateList);
        if ("inner".equals(bo.getType()))
        {
            dragDept.setPid(bo.getDroid());
            Integer count=getCount(bo.getDroid(),dragDept.getTreid());
            dragDept.setOrnum(count+1);
        }
        else if ("before".equals(bo.getType()))
        {
            BpmOrgNode dropDept= dao.findById(bo.getDroid());
            if(dropDept.getPid()!=null){
                dropDept.setPid(dropDept.getPid());
                dragDept.setPid(dropDept.getPid());;
            }else{
                dragDept.setPid(null);
            }
            dragDept.setOrnum(dropDept.getOrnum());

            List<SidOrnum> list3;
            if(StrUtils.isNotBlank(dropDept.getPid())){
                String sql3 = "select id,ornum from sys_org_role_node where ornum>? and pid=?";
                list3 = jdbcHelper.getTp().query(sql3,new Object[]{dropDept.getOrnum(),dropDept.getPid()},
                        new BeanPropertyRowMapper<>(SidOrnum.class));
            }else{
                String sql3 = "select id,ornum from sys_org_role_node where ornum>? and treid=? and pid is null";
                list3 = jdbcHelper.getTp().query(sql3,new Object[]{dropDept.getOrnum(),dropDept.getTreid()},
                        new BeanPropertyRowMapper<>(SidOrnum.class));
            }

            String updateSql3 = "update sys_org_role_node set ornum=? where id=?";
            List<Object[]> updateList3 = new ArrayList<>();
            for (SidOrnum sidOrnum : list3) {
                Object[] arr=new Object[2];
                arr[0]= sidOrnum.getOrnum()+1;
                arr[1]= sidOrnum.getId();
                updateList3.add(arr);
            }
            jdbcHelper.batch(updateSql3, updateList3);
            dropDept.setOrnum(dropDept.getOrnum()+1);
            String updateSql4 = "update sys_org_role_node set ornum=? where id=?";
            jdbcHelper.update(updateSql4, dropDept.getOrnum(), dropDept.getId());
        }
        else if ("after".equals(bo.getType()))
        {
            BpmOrgNode dropDept= dao.findById(bo.getDroid());
            if(dropDept.getPid()!=null){
                dropDept.setPid(dropDept.getPid());
            }
            Integer count = getCount(dropDept.getPid(),dropDept.getTreid());
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
        update(dragDept);
    }

    private final JdbcHelper jdbcHelper;

    private final BpmOrgNodeDao dao;


}
