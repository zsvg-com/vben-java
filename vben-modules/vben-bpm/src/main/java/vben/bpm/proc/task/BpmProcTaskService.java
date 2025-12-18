package vben.bpm.proc.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.common.core.utils.StrUtils;
import vben.common.jdbc.dto.Stree;
import vben.common.jdbc.root.Db;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.bpm.root.domain.Znode;
import vben.bpm.root.domain.Zproc;
import vben.bpm.root.domain.dto.BpmTaskDto;
import vben.bpm.root.domain.vo.BpmCcInfoVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service
@RequiredArgsConstructor
public class BpmProcTaskService {

    private final BpmProcTaskDao dao;

    public List<BpmProcTask> findAllByNodidNotActive(Long nodid) {
        return dao.findAllByNodidAndActagOrderByOrnum(nodid);
    }

    public List<BpmProcTask> findAllByNodid(Long nodid) {
        return dao.findAllByNodidOrderByOrnum(nodid);
    }

    public BpmProcTask createTask(Zproc zproc, Znode znode) {
        BpmProcTask task = new BpmProcTask();
        task.setProid(zproc.getProid());
        task.setState("20");
        task.setExuid(znode.getExuids());
        task.setOrnum(0);
        task.setActag(true);
        task.setNodid(znode.getNodid());
        task.setType(znode.getFacty());
        dao.insert(task);
        return task;
    }

    public List<BpmProcTask> createTaskList(Zproc zproc, Znode znode) {
        List<BpmProcTask> list = new ArrayList<>();
        if (StrUtils.isNotBlank(znode.getExuids()) && !znode.getExuids().contains(";")) {
            BpmProcTask task = new BpmProcTask();
            task.setProid(zproc.getProid());
            task.setState("20");
            task.setExuid(znode.getExuids());
            task.setOrnum(0);
            task.setActag(true);
            task.setNodid(znode.getNodid());
            task.setType(znode.getFacty());
            dao.insert(task);
            list.add(task);
        } else if ("1".equals(znode.getFlway())) {
            String[] ids = znode.getExuids().split(";");
            for (int i = 0; i < ids.length; i++) {
                BpmProcTask task = new BpmProcTask();
                task.setProid(zproc.getProid());
                task.setState("20");
                task.setExuid(ids[i]);
                task.setOrnum(i);
                if (i == 0) {
                    task.setActag(true);
                } else {
                    task.setActag(false);
                }
                task.setNodid(znode.getNodid());
                task.setType(znode.getFacty());
                dao.insert(task);
                list.add(task);
            }
        } else if ("2".equals(znode.getFlway()) || "3".equals(znode.getFlway())) {
            String[] ids = znode.getExuids().split(";");
            for (int i = 0; i < ids.length; i++) {
                BpmProcTask task = new BpmProcTask();
                task.setProid(zproc.getProid());
                task.setState("20");
                task.setExuid(ids[i]);
                task.setOrnum(i);
                task.setActag(true);
                task.setNodid(znode.getNodid());
                task.setType(znode.getFacty());
                dao.insert(task);
                list.add(task);
            }
        }
        return list;
    }

    @Transactional(readOnly = true)
    public BpmProcTask findOne(Long id) {
        return dao.findById(id);
    }

    public void delete(Long id) {
        dao.deleteById(id);
    }

    public void deleteAllByProid(Long proid) {
        dao.deleteAllPyProid(proid);
    }

    public void update(BpmProcTask task) {
        dao.update(task);
    }

    public void findCurrentExmen(List<Map<String, Object>> itemList) {
        String ids = "(";
        for (Map<String, Object> map : itemList) {
            if (!"30".equals(map.get("state"))) {
                ids += "'" + map.get("id") + "',";
            }
        }
        if (!"(".equals(ids)) {
            ids = ids.substring(0, ids.length() - 1) + ")";
            Sqler sqler = new Sqler("n.id as tasid,t.id as nodid,o.name exnam,n.exuid,t.proid,t.facno,t.facna", "bpm_proc_node");
            sqler.addInnerJoin("", "bpm_proc_task n", "n.nodid=t.id");
            sqler.addInnerJoin("", "sys_org o", "o.id=n.exuid");
            sqler.addWhere("t.proid in " + ids + " and n.actag="+ Db.True);
            sqler.addOrder("t.proid");
            sqler.addOrder("n.ornum");

            List<Map<String, Object>> tasks = jdbcHelper.findMapList(sqler);

            List<Stree> list = new ArrayList<>();
            String proid = "";
            for (Map<String, Object> task : tasks) {
                if (!proid.equals(task.get("proid"))) {
                    Stree stree = new Stree();
                    stree.setId(task.get("proid")+"");
                    stree.setName(task.get("facno") + "." + task.get("facna"));
                    stree.setType((String) task.get("exnam"));
                    list.add(stree);
                } else {
                    list.get(list.size() - 1).setType(list.get(list.size() - 1).getPid() + ";" + task.get("exnam"));
                }
                proid = (String) task.get("proid");
            }

            for (Map<String, Object> map : itemList) {
                for (Stree stree : list) {
                    if (stree.getId().equals(map.get("id"))) {
                        map.put("facno", stree.getName());
                        map.put("exmen", stree.getPid());
                        break;
                    }
                }
            }
        }
    }

    public void updateCommunicateState(Zproc zproc){
        String sql = "select count(1) from bpm_proc_task t where t.proid=? and t.type='communicate'";
        Integer count = jdbcHelper.getTp().queryForObject(sql,Integer.class, zproc.getProid());
        if (count != null && count == 0) {
            jdbcHelper.update("update bpm_proc_task set type='review' where proid=?", zproc.getProid());
        }
    }

    public List<BpmTaskDto> findCurrentExmenByProid(Long proid) {

       return dao.findCurrentExmenByProid(proid);

    }

    public List<BpmCcInfoVo> findCanCancelCommunitMen(String proid) {
        return dao.findCanCancelCommunitMen(proid);
    }

    private final JdbcHelper jdbcHelper;
}
