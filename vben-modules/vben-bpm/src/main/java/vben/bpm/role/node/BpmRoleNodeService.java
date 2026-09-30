package vben.bpm.role.node;

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
public class BpmRoleNodeService {

    @Transactional(readOnly = true)
    public BpmRoleNode findById(String id) {
        return dao.findById(id);
    }

    public String insert(BpmRoleNode main) {
        if (main.getId() == null || "".equals(main.getId())) {
            main.setId(IdUtils.getSnowflakeNextIdStr());
        }
        if (main.getPid() == null ) {
            main.setTier( "_" + main.getId() + "_");
        } else {
            String tier = jdbcHelper.findString("select tier from bpm_role_node where id=?", main.getPid());
            main.setTier(tier + main.getId() + "_");
        }
        dao.insert(main);
        return main.getId();
    }


    public String update(BpmRoleNode main) throws Exception {
        main.setUptim(new Date());
        if (main.getPid()==null) {
            main.setTier("_"+ main.getId() + "_");
        } else {
            String tier = jdbcHelper.findString("select tier from bpm_role_node where id=?", main.getPid());
            main.setTier(tier + main.getId()+ "_");
            String[] arr = tier.split("_");
            for (String str : arr) {
                if (main.getId().equals(str)) {
                    throw new Exception("父层级不能为自己或者自己的子层级");
                }
            }
        }
        dao.update(main);
        String oldTier = jdbcHelper.findString("select tier from bpm_role_node where id=?", main.getId());
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
        String sql = "select id,tier as name from bpm_role_node where tier like ? and id<>?";
        List<SidName> list = jdbcHelper.findSidNameList(sql, oldTier + "%", id);
        String updateSql = "update bpm_role_node set tier=? where id=?";
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

    public List<BpmRoleNode> findAll(Sqler sqler) {
        List<BpmRoleNode> list = jdbcHelper.getTp().query(sqler.getSql(), sqler.getParams(), new BeanPropertyRowMapper<>(BpmRoleNode.class));
        return list;
    }

    public PageData findPageData(Sqler sqler) {
        return jdbcHelper.findPageData(sqler);
    }

    public List<BpmRoleNode> findTree(Sqler sqler) {
        List<BpmRoleNode> list = jdbcHelper.getTp().query(sqler.getSql(), sqler.getParams(), new BeanPropertyRowMapper<>(BpmRoleNode.class));
        return buildByRecursive(list);
    }

    //使用递归方法建树
    private List<BpmRoleNode> buildByRecursive(List<BpmRoleNode> nodes) {
        List<BpmRoleNode> list = new ArrayList<>();
        for (BpmRoleNode node : nodes) {
            if (node.getPid() == null) {
                list.add(findChildrenByTier(node, nodes));
            } else {
                boolean flag = false;
                for (BpmRoleNode node2 : nodes) {
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
    private BpmRoleNode findChildrenByTier(BpmRoleNode node, List<BpmRoleNode> nodes) {
        for (BpmRoleNode item : nodes) {
            if (node.getId().equals(item.getPid())) {
                if (node.getChildren() == null) {
                    node.setChildren(new ArrayList<>());
                }
                node.getChildren().add(findChildrenByTier(item, nodes));
            }
        }
        return node;
    }


//    public List<SysRoleTree> findWithoutItself(Sqler sqler, String id) {
//        List<SysRoleTree> list = jdbcHelper.getTp().query(sqler.getSql(), sqler.getParams(), new BeanPropertyRowMapper<>(SysRoleTree.class));
//        return buildByRecursiveWithoutItself(list, id);
//    }

    //使用递归方法建树不包含自己
//    private List<SysRoleTree> buildByRecursiveWithoutItself(List<SysRoleTree> nodes, String id) {
//        List<SysRoleTree> list = new ArrayList<>();
//        for (SysRoleTree node : nodes) {
//            if (node.getPid() == null && !node.getId().equals(id)) {
//                list.add(findChildrenByTierWithoutItself(node, nodes, id));
//            } else {
//                boolean flag = false;
//                for (SysRoleTree node2 : nodes) {
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
//    private SysRoleTree findChildrenByTierWithoutItself(SysRoleTree node, List<SysRoleTree> nodes, String id) {
//        for (SysRoleTree item : nodes) {
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
            String countSql="select count(1) from bpm_role_node where pid=?";
            Integer count = jdbcHelper.getTp().queryForObject(countSql, new Object[]{pid}, Integer.class);
            if(count==null){
                count=0;
            }
            return count;
        }else{
            String countSql="select count(1) from bpm_role_node where pid is null and treid=?";
            Integer count = jdbcHelper.getTp().queryForObject(countSql,new Object[]{treid}, Integer.class);
            if(count==null){
                count=0;
            }
            return count;
        }
    }


    public void move(Smove bo) throws Exception {
        BpmRoleNode dragNode= dao.findById(bo.getDraid());
        if(dragNode.getPid()!=null){
            dragNode.setPid(dragNode.getPid());
        }

        List<SidOrnum> list2;

        if(StrUtils.isNotBlank(dragNode.getPid())){
            String sql = "select id,ornum from sys_role_node where ornum>? and pid=?";
            list2 = jdbcHelper.getTp().query(sql,new Object[]{dragNode.getOrnum(),dragNode.getPid()},
                    new BeanPropertyRowMapper<>(SidOrnum.class));
        }else{
            String sql = "select id,ornum from sys_role_node where ornum>? and treid=? and pid is null";
            list2 = jdbcHelper.getTp().query(sql,new Object[]{dragNode.getOrnum(),dragNode.getTreid()},
                    new BeanPropertyRowMapper<>(SidOrnum.class));
        }

        String updateSql = "update sys_role_node set ornum=? where id=?";
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
            dragNode.setPid(bo.getDroid());
            Integer count=getCount(bo.getDroid(),dragNode.getTreid());
            dragNode.setOrnum(count+1);
        }
        else if ("before".equals(bo.getType()))
        {
            BpmRoleNode dropNode= dao.findById(bo.getDroid());
            if(dropNode.getPid()!=null){
                dropNode.setPid(dropNode.getPid());
                dragNode.setPid(dropNode.getPid());;
            }else{
                dragNode.setPid(null);
            }
            dragNode.setOrnum(dropNode.getOrnum());

            List<SidOrnum> list3;
            if(StrUtils.isNotBlank(dropNode.getPid())){
                String sql3 = "select id,ornum from sys_role_node where ornum>? and pid=?";
                list3 = jdbcHelper.getTp().query(sql3,new Object[]{dropNode.getOrnum(),dropNode.getPid()},
                        new BeanPropertyRowMapper<>(SidOrnum.class));
            }else{
                String sql3 = "select id,ornum from sys_role_node where ornum>? and treid=? and pid is null";
                list3 = jdbcHelper.getTp().query(sql3,new Object[]{dropNode.getOrnum(),dropNode.getTreid()},
                        new BeanPropertyRowMapper<>(SidOrnum.class));
            }

            String updateSql3 = "update sys_role_node set ornum=? where id=?";
            List<Object[]> updateList3 = new ArrayList<>();
            for (SidOrnum sidOrnum : list3) {
                Object[] arr=new Object[2];
                arr[0]= sidOrnum.getOrnum()+1;
                arr[1]= sidOrnum.getId();
                updateList3.add(arr);
            }
            jdbcHelper.batch(updateSql3, updateList3);
            dropNode.setOrnum(dropNode.getOrnum()+1);
            String updateSql4 = "update sys_role_node set ornum=? where id=?";
            jdbcHelper.update(updateSql4, dropNode.getOrnum(), dropNode.getId());
        }
        else if ("after".equals(bo.getType()))
        {
            BpmRoleNode dropNode= dao.findById(bo.getDroid());
            if(dropNode.getPid()!=null){
                dropNode.setPid(dropNode.getPid());
            }
            Integer count = getCount(dropNode.getPid(),dropNode.getTreid());
            if (dragNode.getPid()!=null&&dragNode.getPid().equals(dropNode.getPid()))
            {
                dragNode.setOrnum(count);
            }
            else
            {
                dragNode.setPid(dropNode.getPid());
                dragNode.setOrnum(count+1);
            }
        }
        update(dragNode);
    }

    private final JdbcHelper jdbcHelper;

    private final BpmRoleNodeDao dao;


}
