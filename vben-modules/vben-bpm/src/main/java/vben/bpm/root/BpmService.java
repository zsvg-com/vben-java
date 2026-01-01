package vben.bpm.root;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.base.sys.org.Org;
import vben.common.core.exception.ServiceException;
import vben.common.core.utils.IdUtils;
import vben.common.core.utils.StrUtils;
import vben.common.jdbc.root.DbType;
import vben.common.jdbc.root.Db;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.satoken.utils.LoginHelper;
import vben.bpm.org.tree.BpmOrgTreeService;
import vben.bpm.proc.audit.BpmProcAuditService;
import vben.bpm.proc.def.BpmProcDefService;
import vben.bpm.proc.inst.BpmProcInst;
import vben.bpm.proc.inst.BpmProcInstService;
import vben.bpm.proc.node.BpmProcNode;
import vben.bpm.proc.node.BpmProcNodeHi;
import vben.bpm.proc.node.BpmProcNodeHiService;
import vben.bpm.proc.node.BpmProcNodeService;
import vben.bpm.proc.param.BpmProcParam;
import vben.bpm.proc.param.BpmProcParamService;
import vben.bpm.proc.task.BpmProcTask;
import vben.bpm.proc.task.BpmProcTaskHi;
import vben.bpm.proc.task.BpmProcTaskHiService;
import vben.bpm.proc.task.BpmProcTaskService;
import vben.bpm.root.domain.Znode;
import vben.bpm.root.domain.Zproc;
import vben.bpm.todo.main.BpmTodoMainService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class BpmService {

    //启动流程
    public void start(Zproc zproc) throws Exception {
        //1 保存流程实例
        BpmProcInst bpmProcInst = new BpmProcInst(zproc);
        processService.insert(bpmProcInst);

        //2 历史节点表保存开始节点
        nodeHiService.saveStartNode(zproc);

        //3 流程流转（收集流转过的节点，计算出下一个待审批节点）
        defService.setExxml(zproc);//查找并设置流程xml定义
        Znode draftNode = new Znode("N1");
        draftNode.setFacna("起草节点");
        draftNode.setFacty("draft");
        List<Znode> passNodes = new ArrayList<>();
        List<Znode> goalNodes = new ArrayList<>();
        bpmHandler.procFlow(zproc, passNodes, goalNodes, draftNode);//流转核心逻辑
        if (goalNodes.isEmpty()) {
            throw new Exception("未找到下一个可流的流程节点");
        }
        for (Znode goalNode : goalNodes) {
            exuidsTran(zproc.getInuid(), goalNode);//审批人转换
        }
        //Znode nextNode = goalNodes.get(0);

        //4.1 历史节点表保存起草节点
        draftNode.setNodid(IdUtils.getSnowflakeNextId());
        nodeHiService.saveDraftNode(zproc, draftNode);
        //4.2 评审表保存起草节点的评审信息
        auditService.saveDraftAudit(zproc, draftNode);
        //4.3 历史节点表保存其他已流节点（条件分支等非审批节点）
        nodeHiService.saveNodeList(zproc, passNodes);
        //4.4 当前节点表保存下一个待审批节点

        for (Znode goalNode : goalNodes) {
            BpmProcNode nodeMain = nodeService.saveNode(zproc, goalNode);
            goalNode.setNodid(nodeMain.getId());
            //4.5 历史节点表保存下一个待审批节点
            nodeHiService.saveNode(nodeMain);

            //5.1 当前任务表创建待审节点的任务
            List<BpmProcTask> mainTaskList = taskService.createTaskList(zproc, goalNode);
            //5.2 历史任务表创建待审节点的任务
            taskHiService.createTaskList(mainTaskList);
            //6 发起待办
            todoService.sendTodos(zproc, mainTaskList);
        }
    }

    //流转流程
    public String flow(Zproc zproc)  {
        //以防万一流转时未设置流程发起人
        if(zproc.getInuid()==null){
            String inuidSql = "select cruid from bpm_proc_inst where id=?";
            String inuid = jdbcHelper.findString(inuidSql, zproc.getProid());
            zproc.setInuid(inuid);
        }


        String state = "";
        switch (zproc.getOpkey()) {
            case "pass"://通过操作
                if (handlerPass(zproc)) {
                    state = "30";//终审通过状态
                } else {
                    state = "20";//待审状态
                }
                break;
            case "refuse"://驳回操作
                if ("N1".equals(zproc.getTarno())) {
                    state = "11";//驳回到起草人，则草稿状态
                }
                handlerRefuse(zproc);
                break;
            case "turn"://转办操作
                handlerTurn(zproc);
                break;
            case "communicate"://沟通操作
                handlerCommunicate(zproc);
                break;
            case "bacommunicate"://回复沟通操作
                handlerBacommunicate(zproc);
                break;
            case "cacommunicate"://取消沟通操作
                handlerCacommunicate(zproc);
                break;
            case "abandon"://废弃操作
                handlerAbandon(zproc);
                state = "00";//废弃状态
                break;
        }
        return state;
    }

    //通过流程
    private boolean handlerPass(Zproc zproc)  {
        zproc.setHauid(LoginHelper.getUserId());

        zproc.setOpkey("pass");
        zproc.setOpinf("通过");

        //1 评审表保存当前节点的评审信息
        auditService.saveAudit(zproc);
        if (zproc.getBacid()!=null) {
            paramService.delete(zproc.getBacid());
        }

        //2.1 将历史任务变成已办
        BpmProcTaskHi histTask = taskHiService.findOne(zproc.getTasid());
        histTask.setHauid(zproc.getHauid());
        histTask.setEntim(new Date());
        histTask.setState("30");
        taskHiService.update(histTask);
        //2.2 删除当前任务表记录
        taskService.delete(zproc.getTasid());

        //3 流程流转
        Znode currNode = new Znode(zproc.getFacno());
        currNode.setNodid(zproc.getNodid());
        currNode.setFacno(zproc.getFacno());
        currNode.setFacty("review");
        if (zproc.getBacid()!=null) {
            currNode.setTarno(zproc.getTarno());
            currNode.setTarna(zproc.getTarna());
        }
        BpmProcNode dbNode = nodeService.findOne(currNode.getNodid());
        if ("1".equals(dbNode.getFlway())) {
            List<BpmProcTask> bpmProcTaskList = taskService.findAllByNodidNotActive(dbNode.getId());
            if (bpmProcTaskList.size() > 0) {
                bpmProcTaskList.get(0).setActag(true);
                taskService.update(bpmProcTaskList.get(0));
                taskHiService.createTask(bpmProcTaskList.get(0));
                //7.1 删除之前的待办
                todoService.doneTodos(zproc);
                //7.2 发起新待办
                todoService.sendTodos(zproc, bpmProcTaskList);
                return false;
            }
        } else if ("2".equals(dbNode.getFlway())) {
            List<BpmProcTask> bpmProcTaskList = taskService.findAllByNodid(dbNode.getId());
            for (BpmProcTask bpmProcTask : bpmProcTaskList) {
                taskService.delete(bpmProcTask.getId());
                taskHiService.delete(bpmProcTask.getId());
            }
        } else if ("3".equals(dbNode.getFlway())) {
            List<BpmProcTask> bpmProcTaskList = taskService.findAllByNodid(dbNode.getId());
            if (!bpmProcTaskList.isEmpty()) {
                todoService.doneTodo(zproc);
                return false;
            }
        }


        defService.setExxml(zproc);//查找并设置流程xml定义
        List<Znode> passNodes = new ArrayList<>();
        List<Znode> goalNodes = new ArrayList<>();
        bpmHandler.procFlow(zproc, passNodes, goalNodes, currNode);//流转核心逻辑

        if (goalNodes.isEmpty()) {
            throw new ServiceException("未找到下一个可流的流程节点");
        }
        //Znode nextNode =goalNodes.get(0);
        for (Znode goalNode : goalNodes) {
            exuidsTran(zproc.getInuid(), goalNode);//审批人转换
        }


        //4.1 将历史节点变成已办
        BpmProcNodeHi histNode = nodeHiService.findOne(zproc.getNodid());
        histNode.setEntim(new Date());
        histNode.setState("30");
        histNode.setTarno(currNode.getTarno());
        histNode.setTarna(currNode.getTarna());
        nodeHiService.update(histNode);

        currNode.setFacna(histNode.getFacna());
        //4.2 历史节点表保存已流节点
        nodeHiService.saveNodeList(zproc, passNodes);
        //4.3 删除当前节点表记录
        nodeService.delete(zproc.getNodid());


        if (!"end".equals(goalNodes.get(0).getFacty())) {
            for (Znode goalNode : goalNodes) {
                //5.1 当前节点表保存下一个待审批节点
                BpmProcNode nodeMain = nodeService.saveNode(zproc, goalNode);
                goalNode.setNodid(nodeMain.getId());
                //5.2 历史节点表保存下一个待审批节点
                nodeHiService.saveNode(nodeMain);

                //6.1 当前任务表创建待审节点的任务
                List<BpmProcTask> mainTaskList = taskService.createTaskList(zproc, goalNode);
                //6.2 历史任务表创建待审节点的任务
                taskHiService.createTaskList(mainTaskList);

                //7.1 删除之前的待办
                todoService.doneTodos(zproc);
                //7.2 发起新待办
                todoService.sendTodos(zproc, mainTaskList);
            }
            return false;
        } else {
            //5 历史节点表保存结束节点
            Long endNodeId = nodeHiService.saveEndNode(zproc);

            //6 评审表保存结束节点的评审信息
            auditService.saveEndAudit(zproc, endNodeId);

            //7 删除之前的待办
            todoService.doneTodos(zproc);

            //8 将流程更新成完结
            processService.updateEndState(zproc);
            return true;
        }
    }

    //驳回流程
    private Znode handlerRefuse(Zproc zproc) {
        zproc.setHauid(LoginHelper.getUserId());

//        String sql = "select m.id \"proid\",m.name \"prona\" from bpm_inst_proc m where m.id=?";
//        Map<String, Object> map = jdbcDao.findMap(sql, zproc.getProid());
//        zproc.setProna((String) map.get("prona"));

        //驳回: "起草节点"（返回本人）
        if (zproc.getRetag()) {
            zproc.setOpinf("驳回: " + zproc.getTarno() + "." + zproc.getTarna() + "（返回本人）");
        } else {
            zproc.setOpinf("驳回: " + zproc.getTarno() + "." + zproc.getTarna());
        }

        //1 评审表保存当前节点的评审信息
        auditService.saveAudit(zproc);

        //2.1 将历史任务变成已办
        BpmProcTaskHi histTask = taskHiService.findOne(zproc.getTasid());
        histTask.setHauid(zproc.getHauid());
        histTask.setEntim(new Date());
        histTask.setState("30");
        taskHiService.update(histTask);
        //2.2 删除当前任务表记录
        taskService.deleteAllByProid(zproc.getProid());
        //2.3 删除历史任务未结束记录
        taskHiService.deleteAllByProidNotEnd(zproc.getProid());

        //3 创建驳回节点
//        Znode refuseNode = hand.getNodeInfo(zproc,zproc.getTarno());
//        Znode refuseNode = new Znode();
//        refuseNode.setFacno(zproc.getTarno());
//        refuseNode.setFacna(zproc.getTarna());
//        refuseNode.setExuids(zproc.getExuid());
//        refuseNode.setFacty("review");
        String xmlSql = "select t.exxml \"exxml\" from bpm_proc_def t inner join bpm_proc_inst m on m.prdid=t.id where m.id=?";
        String exxml = jdbcHelper.findString(xmlSql, zproc.getProid());
        Znode refuseNode = bpmHandler.getNodeInfo(exxml, zproc.getTarno());

        if ("N1".equals(refuseNode.getFacno())) {
            refuseNode.setExuids(zproc.getInuid());
        }
        exuidsTran(zproc.getInuid(), refuseNode);
        //4.1 将历史节点变成已办
        BpmProcNodeHi histNode = nodeHiService.findOne(zproc.getNodid());
        histNode.setTarno(zproc.getTarno());
        histNode.setTarna(zproc.getTarna());
        histNode.setEntim(new Date());
        histNode.setState("30");
        nodeHiService.update(histNode);
        //4.2 删除当前节点表记录
        nodeService.delete(zproc.getNodid());

        //5.1 当前节点表保存下一个待审批节点
        BpmProcNode nodeMain = nodeService.saveNode(zproc, refuseNode);
        refuseNode.setNodid(nodeMain.getId());
        //5.2 历史节点表保存下一个待审批节点
        nodeHiService.saveNode(nodeMain);
        //5.3 如果驳回时勾选了 驳回的节点通过后直接返回本节点
        if (zproc.getRetag()) {
            BpmProcParam param = new BpmProcParam();
            param.setId(IdUtils.getSnowflakeNextId());
            param.setProid(zproc.getProid());
            param.setOffty("proc");
            param.setOffid(zproc.getProid());
            param.setPakey(zproc.getTarno() + "#refuse");
            param.setPaval(zproc.getFacno());
            paramService.save(param);
        }

        //6.1 当前任务表创建待审节点的任务
        List<BpmProcTask> mainTaskList = taskService.createTaskList(zproc, refuseNode);
        //6.2 历史任务表创建待审节点的任务
        taskHiService.createTaskList(mainTaskList);

        //7.1 删除之前的待办
        todoService.doneTodos(zproc);
        //7.2 发起新待办
        todoService.sendTodos(zproc, mainTaskList);
        return refuseNode;
    }

    //转办流程
    private void handlerTurn(Zproc zproc) {
        zproc.setHauid(LoginHelper.getUserId());

//        String sql = "select m.id \"proid\",m.name \"prona\" from bpm_proc_inst m where m.id=?";
//        Map<String, Object> map = jdbcDao.findMap(sql, zproc.getProid());
//        zproc.setProna((String) map.get("prona"));

        String turnManSql = "select id,name,type from sys_org where id=?";
        Org org = jdbcHelper.getTp().queryForObject(turnManSql,new BeanPropertyRowMapper<>(Org.class), zproc.getTuuid());
        if (zproc.getTutag()) {
            zproc.setOpinf("转办: " + org.getName() + "（完整转办）");
        } else {
            zproc.setOpinf("转办: " + org.getName());
        }
        //1 评审表保存当前节点的评审信息
        auditService.saveAudit(zproc);

        BpmProcTask task = taskService.findOne(zproc.getTasid());
        task.setExuid(zproc.getTuuid());
        taskService.update(task);

        todoService.doneTodo(zproc);
        todoService.sendTodo(zproc, zproc.getTuuid());

    }

    //沟通流程
    private void handlerCommunicate(Zproc zproc) {
        zproc.setHauid(LoginHelper.getUserId());

//        String sql = "select m.id \"proid\",m.name \"prona\" from bpm_proc_inst m where m.id=?";
//        Map<String, Object> map = jdbcDao.findMap(sql, zproc.getProid());
//        zproc.setProna((String) map.get("prona"));

        String names = getOrgNames("'" + zproc.getCoids().replaceAll(";", "','") + "'");
//        Sqler sqler = new Sqler("sys_org");
//        sqler.addWhere("id in " + "(" + ids + ")");
//        if (DbType.MYSQL.equals(jdbcDao.getDbType())) {
//            sqler.addOrder("field(id," + ids + ")");
//        }else if(DbType.ORACLE.equals(jdbcDao.getDbType())){
//            sqler.addOrder("INSTR('"+ids.replaceAll("'","")+"',id)");
//        }else if(DbType.SQL_SERVER.equals(jdbcDao.getDbType())){
//            sqler.addOrder("CHARINDEX(id,'"+ids.replaceAll("'","")+"')");
//        }
//
//        sqler.addSelect("t.type");
//        List<SysOrg> list = jdbcDao.getTp().query(sqler.getSql(), sqler.getParams(),
//                new BeanPropertyRowMapper<>(SysOrg.class));
//        String names="";
//        for (SysOrg sysOrg : list) {
//            names += sysOrg.getName() + ";";
//        }
//        names = names.substring(0, names.length() - 1);
        if (zproc.getCotag()) {
            zproc.setOpinf("沟通：" + names + "（隐藏意见）");
        } else {
            zproc.setOpinf("沟通：" + names);
        }
        //1 评审表保存当前节点的评审信息
        auditService.saveAudit(zproc);

        Znode currNode = new Znode(zproc.getFacno());
        currNode.setNodid(zproc.getNodid());
        currNode.setFacno(zproc.getFacno());
        currNode.setFacty("communicate");
        currNode.setExuids(zproc.getCoids());
        currNode.setFlway("3");

        //6.1 将当前任务置为沟通状态
        jdbcHelper.update("update bpm_proc_task set type='to_communicate' where id=?", zproc.getTasid());
        //6.2 当前任务表创建待审节点的任务
        List<BpmProcTask> mainTaskList = taskService.createTaskList(zproc, currNode);
        //6.3 历史任务表创建待审节点的任务
        taskHiService.createTaskList(mainTaskList);

        //7.2 发起新待办
        todoService.sendTodos(zproc, mainTaskList);

    }

    //沟通回复
    private void handlerBacommunicate(Zproc zproc) {
        zproc.setHauid(LoginHelper.getUserId());

        zproc.setOpinf("沟通回复");
        //1 评审表保存当前节点的评审信息
        auditService.saveAudit(zproc);

        //2.1 将历史任务变成已办
        BpmProcTaskHi histTask = taskHiService.findOne(zproc.getTasid());
        histTask.setHauid(zproc.getHauid());
        histTask.setEntim(new Date());
        histTask.setState("30");
        taskHiService.update(histTask);

        //2.2 删除当前任务表记录
        taskService.delete(zproc.getTasid());

        //2.3 判断发起沟通的人那边是否所有沟通都有回复了，如果都回复，讲状态改为待审
        taskService.updateCommunicateState(zproc);

        todoService.doneTodo(zproc);

        //判断发起沟通的人那边是否所有沟通都有回复了，如果都回复，讲状态改为待审
//        String sql = "select count(1) from bpm_proc_task t where t.proid=? and t.type='communicate'";
//        Integer count = jdbcDao.getTp().queryForObject(sql, Integer.class,zproc.getProid());
//        if (count != null && count == 0) {
//            jdbcDao.update("update bpm_proc_task set type='review' where proid=?", zproc.getProid());
//        }

//        Znode currNode = new Znode(zproc.getFacno());
//        currNode.setNodid(zproc.getNodid());
//        currNode.setFacno(zproc.getFacno());
//        currNode.setFacty("communicate");
//        currNode.setExuids(zproc.getCoids());
//        currNode.setFlway("3");
//
//        //6.1 当前任务表创建待审节点的任务
//        List<BpmTaskMain> mainTaskList = taskMainService.createTaskList(zproc, currNode);
//        //6.2 历史任务表创建待审节点的任务
//        taskHistService.createTaskList(mainTaskList);
//        //7.2 发起新待办
//        todoService.sendTodos(zproc, mainTaskList);

    }

    //取消沟通
    private void handlerCacommunicate(Zproc zproc) {

        //2.1 将历史任务变成已办
        String[] taskIds = zproc.getCcids().split(",");
        String userIds = "";
        for (String taskId : taskIds) {
            BpmProcTaskHi histTask = taskHiService.findOne(Long.parseLong(taskId));
            histTask.setHauid(zproc.getHauid());
            histTask.setEntim(new Date());
            histTask.setState("30");
            userIds += histTask.getExuid() + ",";
            //2.2 删除当前任务表记录
            taskService.delete(Long.parseLong(taskId));
        }
        userIds = userIds.substring(0, userIds.length() - 1);
        zproc.setHauid(LoginHelper.getUserId());
        String names = getOrgNames("'" + userIds.replaceAll(",", "','") + "'");
        zproc.setOpinf("取消沟通：" + names);
        //1 评审表保存当前节点的评审信息
        auditService.saveAudit(zproc);

        todoService.doneTodosByTaskIds(taskIds);
        //repo.flush();

//        //2.3 判断发起沟通的人那边是否所有沟通都有回复了，如果都回复，讲状态改为待审
//        taskService.updateCommunicateState(zproc);

        String sql = "select count(1) from bpm_proc_task t where t.proid=? and t.type='communicate'";
        Integer count = jdbcHelper.getTp().queryForObject(sql,Integer.class, zproc.getProid());
        //如果取消了所有沟通人员，则更新状态
        if (count != null && count == 0) {
            jdbcHelper.update("update bpm_proc_task set type='review' where id=?", zproc.getTasid());
        }
//        Znode currNode = new Znode(zproc.getFacno());
//        currNode.setNodid(zproc.getNodid());
//        currNode.setFacno(zproc.getFacno());
//        currNode.setFacty("communicate");
//        currNode.setExuids(zproc.getCoids());
//        currNode.setFlway("3");
//
//        //6.1 当前任务表创建待审节点的任务
//        List<BpmTaskMain> mainTaskList = taskMainService.createTaskList(zproc, currNode);
//        //6.2 历史任务表创建待审节点的任务
//        taskHistService.createTaskList(mainTaskList);
//        //7.2 发起新待办
//        todoService.sendTodos(zproc, mainTaskList);

    }

    //废弃流程
    private void handlerAbandon(Zproc zproc) {
        zproc.setHauid(LoginHelper.getUserId());
//        String sql = "select m.id \"proid\",m.name \"prona\" from bpm_proc_inst m where m.id=?";
//        Map<String, Object> map = jdbcDao.findMap(sql, zproc.getProid());
//        zproc.setProna((String) map.get("prona"));

        zproc.setOpinf("废弃");
        //1 评审表保存当前节点的评审信息
        auditService.saveAudit(zproc);
        //2.1 将历史任务变成已办
        BpmProcTaskHi histTask = taskHiService.findOne(zproc.getTasid());
        histTask.setHauid(zproc.getHauid());
        histTask.setEntim(new Date());
        histTask.setState("30");
        //2.2 删除当前任务表记录
        taskService.deleteAllByProid(zproc.getProid());
        //2.3 删除历史任务未结束记录
        taskHiService.deleteAllByProidNotEnd(zproc.getProid());

        //4.1 将历史节点变成已办
        BpmProcNodeHi histNode = nodeHiService.findOne(zproc.getNodid());
        histNode.setTarno(zproc.getTarno());
        histNode.setTarna(zproc.getTarna());
        histNode.setEntim(new Date());
        histNode.setState("30");
        //4.2 删除当前节点表记录
        nodeService.delete(zproc.getNodid());

        //7.1 删除之前的待办
        todoService.doneTodos(zproc);
    }

    private void exuidsTran(String inuid, Znode znode) {
        String tauids = "";
        if (StrUtils.isNotBlank(znode.getExuids()) && !znode.getExuids().contains(";")) {
            String tauidsSql = "select t.id, t.name,t.type from sys_org t where t.id=?";
            Org org = jdbcHelper.getTp().queryForObject(tauidsSql,new BeanPropertyRowMapper<>(Org.class),znode.getExuids());
            if (org.getType() == 32) {
//                String cruid = LoginHelper.getUserId()+"";
//                if (proid != null) {
//                    String sql = "select cruid from bpm_proc_inst where id=?";
//                    cruid = jdbcHelper.findOneString(sql, proid);
//                }
                Org org2 = sysOrgRoleTreeService.calc(inuid, org.getId());
                tauids = org2.getId() + "";
            } else {
                tauids = org.getId() + "";
            }
        } else if (StrUtils.isNotBlank(znode.getExuids()) && znode.getExuids().contains(";")) {
            Sqler sqler = new Sqler("sys_org");
            String ids = znode.getExuids();
            ids = "'" + ids.replaceAll(";", "','") + "'";
            sqler.addWhere("id in " + "(" + ids + ")");
            if (DbType.MYSQL.equals(Db.Type)) {
                sqler.addOrder("field(id," + ids + ")");
            } else if (DbType.ORACLE.equals(Db.Type)) {
                sqler.addOrder("INSTR('" + ids.replaceAll("'", "") + "',id)");
            } else if (DbType.SQL_SERVER.equals(Db.Type)) {
                sqler.addOrder("CHARINDEX(id,'" + ids.replaceAll("'", "") + "')");
            }
            sqler.addSelect("t.type");
            System.out.println(sqler.getSql());
            List<Org> list = jdbcHelper.getTp().query(sqler.getSql(), sqler.getParams(),
                new BeanPropertyRowMapper<>(Org.class));
//            List<SidName> idNameList = jdbcDao.findIdNameList(sqler);
            for (Org sysOrg : list) {
                if (sysOrg.getType() == 32) {
//                    String cruid = LoginHelper.getUserId()+"";
//                    if (proid != null) {
//                        String sql = "select cruid from bpm_proc_inst where id=?";
//                        cruid = jdbcHelper.findOneString(sql, proid);
//                    }
                    Org org = sysOrgRoleTreeService.calc(inuid, sysOrg.getId());
                    tauids += org.getId() + ";";
                } else {
                    tauids += sysOrg.getId() + ";";
                }
            }
            tauids = tauids.substring(0, tauids.length() - 1);
        }
        znode.setExuids(tauids);
    }

    public Zproc getZproc(String proid) {
        String sql = "select t.id as tasid,t.nodid,t.exuid,t.proid,n.facno,n.facna from bpm_proc_task t" +
            " inner join bpm_proc_node n on n.id=t.nodid where t.proid=? and t.actag="+ Db.True;
        List<Zproc> list = jdbcHelper.getTp().query(sql,new BeanPropertyRowMapper<>(Zproc.class),proid);
        String userid = LoginHelper.getUserId();
        for (Zproc zproc : list) {
            if (userid.equals(zproc.getExuid())) {
                return zproc;
            }
        }
        return null;
    }

    private String getOrgNames(String ids) {
        Sqler sqler = new Sqler("sys_org");
        sqler.addWhere("id in " + "(" + ids + ")");
        if (DbType.MYSQL.equals(Db.Type)) {
            sqler.addOrder("field(id," + ids + ")");
        } else if (DbType.ORACLE.equals(Db.Type)) {
            sqler.addOrder("INSTR('" + ids.replaceAll("'", "") + "',id)");
        } else if (DbType.SQL_SERVER.equals(Db.Type)) {
            sqler.addOrder("CHARINDEX(id,'" + ids.replaceAll("'", "") + "')");
        }

        sqler.addSelect("t.type");
        List<Org> list = jdbcHelper.getTp().query(sqler.getSql(), sqler.getParams(),
            new BeanPropertyRowMapper<>(Org.class));
        String names = "";
        for (Org sysOrg : list) {
            names += sysOrg.getName() + ";";
        }
        names = names.substring(0, names.length() - 1);
        return names;
    }

    //----------bean注入------------
    private final JdbcHelper jdbcHelper;

    private final BpmOrgTreeService sysOrgRoleTreeService;

    private final BpmTodoMainService todoService;

    private final BpmProcTaskHiService taskHiService;

    private final BpmProcTaskService taskService;

    private final BpmProcNodeHiService nodeHiService;

    private final BpmProcNodeService nodeService;

    private final BpmProcAuditService auditService;

    private final BpmProcParamService paramService;

    private final BpmProcDefService defService;

    private final BpmProcInstService processService;

    private final BpmHandler bpmHandler;

}
