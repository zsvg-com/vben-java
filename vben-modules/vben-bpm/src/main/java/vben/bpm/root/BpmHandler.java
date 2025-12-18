package vben.bpm.root;

import cn.hutool.script.ScriptRuntimeException;
import cn.hutool.script.ScriptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import vben.common.core.exception.ServiceException;
import vben.common.core.utils.StrUtils;
import vben.common.jdbc.dto.SidNamePid;
import vben.bpm.root.domain.Znode;
import vben.bpm.root.domain.Zproc;
import vben.bpm.root.domain.dto.BpmProcDto;

import javax.script.CompiledScript;
import javax.script.ScriptException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class BpmHandler {

    /**
     * 获取目标节点
     * @return
     */
    public List<Znode> getGoalNodes(BpmProcDto bpmProcDto, String exxml, String facno) throws Exception {
        SAXReader reader = new SAXReader();
        Reader stringReader = new StringReader(exxml);
        Document document = null;
        try {
            document = reader.read(stringReader);
        } catch (DocumentException e) {
            log.error(e.getMessage());
        }
        Element rootNode = document.getRootElement();

        Znode currNode = new Znode();
        currNode.setFacno(facno);

        Iterator<Element> it = rootNode.elementIterator();
        while (it.hasNext()) {
            Element node = it.next();
            if ("sequenceFlow".equals(node.getName())) {
                if (facno.equals(node.attribute("sourceRef").getValue())) {
                    currNode.setTarno(node.attribute("targetRef").getValue());
                    break;
                }
            }
        }
        List<Znode> passNodes = new ArrayList<>();
        List<Znode> goalNodes = new ArrayList<>();
        nodeFlow(bpmProcDto,rootNode, passNodes, goalNodes, currNode);
        return goalNodes;
    }


    /**
     * 获取某个节点的信息
     *
     * @param exxml
     * @param facno
     * @return
     */
    public Znode getNodeInfo(String exxml, String facno) {
        SAXReader reader = new SAXReader();
        Reader stringReader = new StringReader(exxml);
        Document document = null;
        try {
            document = reader.read(stringReader);
        } catch (DocumentException e) {
            System.out.println("xml解析异常");
            e.printStackTrace();
        }
        Element rootNode = document.getRootElement();
        Iterator<Element> it = rootNode.elementIterator();

        while (it.hasNext()) {
            Element node = it.next();
            if ("userTask".equals(node.getName()) || "task".equals(node.getName())) {
                if (facno.equals(node.attribute("id").getValue())) {
                    Znode znode = new Znode();
                    znode.setFacno(facno);
                    znode.setFacna(node.attribute("name").getValue());

                    znode.setExuids(node.attribute("hamen") != null ? node.attribute("hamen").getValue() : null);

                    znode.setFlway(node.attribute("flway") != null ? node.attribute("flway").getValue() : null);
                    znode.setFacty("review");
                    return znode;
                }
            }
        }
        return null;
    }


    /**
     * 流程流转
     */
    public void procFlow(Zproc zproc, List<Znode> passNodes, List<Znode> goalNodes, Znode currNode) {
        //根据prdid寻找xml的filename
        SAXReader reader = new SAXReader();
        Reader stringReader = new StringReader(zproc.getExxml());
        Document document = null;
        try {
            document = reader.read(stringReader);
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new ServiceException("xml解析异常："+e.getMessage());
        }
        Element rootNode = document.getRootElement();
        Iterator<Element> it = rootNode.elementIterator();
        //查找当前节点的目标节点，如果已有（比如是驳回返回的）则不需要额外查找
        if (currNode.getTarno() == null) {
            while (it.hasNext()) {
                Element node = it.next();
                if ("sequenceFlow".equals(node.getName())) {
                    if (currNode.getFacno().equals(node.attribute("sourceRef").getValue())) {
//                    nextNode.setId(node.attribute("targetRef").getValue());
                        currNode.setTarno(node.attribute("targetRef").getValue());
                        break;
                    }
                }
            }
        }
        BpmProcDto bpmProcDto = new BpmProcDto();
        bpmProcDto.setProid(zproc.getProid());
        bpmProcDto.setPrdid(zproc.getPrdid());
        bpmProcDto.setBusty(zproc.getBusty());
        //判断nextNode是否为审批节点
        nodeFlow(bpmProcDto, rootNode, passNodes, goalNodes, currNode);
    }

    //节点流转
    private void nodeFlow(BpmProcDto bpmProcDto, Element rootNode, List<Znode> passNodes, List<Znode> goalNodes, Znode currNode) {
        if ("NE".equals(currNode.getTarno())) {
            currNode.setTarna("结束节点");
            Znode endNode = new Znode();
            endNode.setFacno("NE");
            endNode.setFacna("结束节点");
            endNode.setFacty("end");
            goalNodes.add(endNode);
            return;
        }
        Iterator<Element> it = rootNode.elementIterator();
        String userIds = "";
        while (it.hasNext()) {
            Element xmlNode = it.next();
            if (currNode.getTarno().equals(xmlNode.attribute("id").getValue())) {
                if ("task".equals(xmlNode.getName()) || "userTask".equals(xmlNode.getName())) {
                    //判断节点是否为审批节点，如果为审批节点则取处理人
                    userTask(xmlNode, userIds, goalNodes, currNode);
                } else if ("exclusiveGateway".equals(xmlNode.getName()) && bpmProcDto.getProid() != null) {
                    //如果是互斥网关，则根据分支条件流转到下一个节点
                    exclusiveGateway(bpmProcDto, rootNode, passNodes, xmlNode, goalNodes, currNode);
                } else if ("parallelGateway".equals(xmlNode.getName())) {
                    parallelGateway(bpmProcDto, rootNode, passNodes, xmlNode, goalNodes, currNode);
                }
                break;
            }
        }
    }




    /**
     * 审批节点逻辑
     */
    private void userTask(Element xmlNode, String userIds, List<Znode> goalNodes, Znode currNode) {
        String hatyp = xmlNode.attribute("hatyp").getValue();
        if ("1".equals(hatyp)) {
            userIds = xmlNode.attribute("hamen").getValue();
        } else {
//                        if (zcond.getProid() != null && "$creator".equals(node.attribute("hamen").getValue())) {
//                            userIds = jdbcDao.findOneString("select cruid from bpm_proc_inst where id=?", zcond.getProid());
//                        }
//                        if (StrUtils.isBlank(userIds) && zcond.getPrdid() != null && "$creator".equals(node.attribute("hamen").getValue())) {
//                            userIds = XuserUtil.getUserId();
//                        }
        }

        currNode.setTarna(xmlNode.attribute("name").getValue());
        System.out.println(currNode);
        //list.add(znode);
        Znode nextNode = new Znode();
        nextNode.setFacno(currNode.getTarno());
        nextNode.setFacna(currNode.getTarna());
        nextNode.setFacty("review");
        nextNode.setExuids(userIds);
        nextNode.setFlway(xmlNode.attribute("flway").getValue());

        boolean flag = false;
        for (int i = 0; i < goalNodes.size(); i++) {
            if (goalNodes.get(i).getFacno().equals(currNode.getFacno())) {
                goalNodes.set(i, nextNode);
                flag = true;
                break;
            }
        }
        if (!flag) {
            goalNodes.add(nextNode);
        }
    }

    /**
     * 互斥网关逻辑
     */
    private void exclusiveGateway(BpmProcDto bpmProcDto, Element rootNode, List<Znode> passNodes,
                                  Element xmlNode, List<Znode> goalNodes, Znode currNode)  {
        String nextNodeId = "";
        currNode.setTarna(xmlNode.attribute("name").getValue());
        //list.add(znode);
        Iterator<Element> it = rootNode.elementIterator();
        while (it.hasNext()) {
            Element xnode = it.next();
            if ("sequenceFlow".equals(xnode.getName())) {
                if (currNode.getTarno().equals(xnode.attribute("sourceRef").getValue())) {

                    if (checkConds(bpmProcDto, xnode.attribute("conds").getValue())) {
                        nextNodeId = xnode.attribute("targetRef").getValue();
                        System.out.println("条件分支转到:" + nextNodeId);
                        Znode nextNode = new Znode();
                        nextNode.setFacno(currNode.getTarno());
                        nextNode.setFacna(currNode.getTarna());
                        nextNode.setFacty("condtion");
                        nextNode.setTarno(nextNodeId);
                        passNodes.add(nextNode);

                        boolean flag = false;
                        for (int i = 0; i < goalNodes.size(); i++) {
                            if (goalNodes.get(i).getFacno().equals(currNode.getFacno())) {
                                goalNodes.set(i, nextNode);
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            goalNodes.add(nextNode);
                        }
                        nodeFlow(bpmProcDto, rootNode, passNodes, goalNodes, nextNode);
                        return;
                    }
                }
            }
        }
        if (bpmProcDto.isSherr()) {
            throw new ServiceException("没有合适的条件分支满足流程流转");
        }
    }

    /**
     * 并行网关逻辑
     */
    private void parallelGateway(BpmProcDto bpmProcDto, Element rootNode, List<Znode> passNodes,
                                 Element xmlNode, List<Znode> goalNodes, Znode currNode) {
        //需要判断是并行开始还是并行结束
        Iterator<Element> pgList = xmlNode.elementIterator();
        int pgChildSize = 0;
        while (pgList.hasNext()) {
            Element pgNode = pgList.next();
            if ("outgoing".equals(pgNode.getName())) {
                pgChildSize++;
            }
        }
        if (pgChildSize <= 1) { //判断为并行结束节点
            Znode pendNode = new Znode();

            pendNode.setFacno(currNode.getTarno());
            pendNode.setFacna(xmlNode.attribute("name").getValue());
            pendNode.setFacty("pend");
            pendNode.setTarno(xmlNode.attribute("id").getValue());
            passNodes.add(pendNode);
            goalNodes.add(pendNode);

//            boolean flag = false;
//            for (int i = 0; i < goalNodes.size(); i++) {
//                if (goalNodes.get(i).getFacno().equals(currNode.getFacno())) {
//                    goalNodes.set(i, pendNode);
//                    flag = true;
//                    break;
//                }
//            }
//            if (!flag) {
//                goalNodes.add(pendNode);
//            }
//
            String sql = "select count(1) from bpm_proc_node where proid=? and facno<>? and facty <> 'pend'";
            int size = jdbcTemplate.queryForObject(sql,Integer.class, bpmProcDto.getProid(), currNode.getFacno());

            //判断是否都达到了，都达到则流程往下流
//            boolean isAllArrive = true;
//            Znode firstGoalNode = goalNodes.get(0);
//            for (Znode goalNode : goalNodes) {
//                if (goalNode.getNodid() != firstGoalNode.getNodid()) {
//                    isAllArrive = false;
//                }
//            }

            if (size == 0) {
                goalNodes.clear();
                String nextNodeId = "";
                Iterator<Element> it = rootNode.elementIterator();
                while (it.hasNext()) {
                    Element xnode = it.next();
                    if ("sequenceFlow".equals(xnode.getName())) {
                        if (pendNode.getTarno().equals(xnode.attribute("sourceRef").getValue())) {
                            nextNodeId = xnode.attribute("targetRef").getValue();
                            System.out.println("并行分支结束后转到:" + nextNodeId);
                            Znode nextNode = new Znode();
                            nextNode.setFacno(pendNode.getTarno());
                            nextNode.setFacna(pendNode.getTarna());
                            //nextNode.setFacty("pstart");
                            nextNode.setTarno(nextNodeId);
                            passNodes.add(nextNode);
                            goalNodes.add(nextNode);
                            nodeFlow(bpmProcDto, rootNode, passNodes, goalNodes, nextNode);
                            break;
                        }
                    }
                }
            }

        } else {//判断为并行开始节点
            String nextNodeId = "";
            currNode.setTarna(xmlNode.attribute("name").getValue());
            Iterator<Element> it = rootNode.elementIterator();
            while (it.hasNext()) {
                Element xnode = it.next();
                if ("sequenceFlow".equals(xnode.getName())) {
                    if (currNode.getTarno().equals(xnode.attribute("sourceRef").getValue())) {
                        nextNodeId = xnode.attribute("targetRef").getValue();
                        System.out.println("并行分支开始后转到:" + nextNodeId);
                        Znode nextNode = new Znode();
                        nextNode.setFacno(currNode.getTarno());
                        nextNode.setFacna(currNode.getTarna());
                        nextNode.setFacty("pstart");
                        nextNode.setTarno(nextNodeId);
                        passNodes.add(nextNode);
                        boolean flag = false;
                        for (int i = 0; i < goalNodes.size(); i++) {
                            if (goalNodes.get(i).getFacno().equals(currNode.getFacno())) {
                                goalNodes.set(i, nextNode);
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            goalNodes.add(nextNode);
                        }
                        nodeFlow(bpmProcDto, rootNode, passNodes, goalNodes, nextNode);
                    }
                }
            }
        }
    }

    //互斥网关判断条件
    private boolean checkConds(BpmProcDto bpmProcDto, String expression) {
        System.out.println("条件为：" + expression);
        if (StrUtils.isBlank(expression)) {
            return false;
        }

        String zformJson = "";
        if (bpmProcDto != null) {
            System.out.println("busty:" + bpmProcDto.getBusty());
            if ("oaFlow".equals(bpmProcDto.getBusty())) {
                String sql = "select fdata from oa_flow_main where id=?";
                zformJson = jdbcTemplate.queryForObject(sql, new Long[]{bpmProcDto.getProid()}, String.class);
                //zformJson = jdbcDao.findOneString(sql, zcond.getProid());
            } else if (bpmProcDto.getBusty().startsWith("app_")) {
                String sql = "select cond from bpm_proc_cond where id=?";
                zformJson = jdbcTemplate.queryForObject(sql, new Long[]{bpmProcDto.getProid()}, String.class);
                //zformJson = jdbcDao.findOneString(sql, zcond.getProid());
            }

        }
        if (StrUtils.isBlank(zformJson)) {
            zformJson = "{}";
        }
        String form = "var $=" + zformJson;
        ScriptUtil.compile("var $={\"createDept\":108}");
        System.out.println(form);
        CompiledScript script;
        if (expression.contains(";")) {
            script = ScriptUtil.compile(form + ";var z=false;" + expression + ";z");
        } else {
            script = ScriptUtil.compile(form + ";var z=" + expression + ";z");
//            script= ScriptUtil.compile(expression);
        }
        boolean flag;
        try {
            flag = (boolean) script.eval();
        } catch (ScriptException e) {
            throw new ScriptRuntimeException(e);
        }
        System.out.println("返回结果=" + flag);
        return flag;
    }

//    public Znode calcTarget(Zcond zcond, String exxml, String facno) throws Exception {
//        SAXReader reader = new SAXReader();
//        Reader stringReader = new StringReader(exxml);
//        Document document = null;
//        try {
//            document = reader.read(stringReader);
//        } catch (DocumentException e) {
//            System.out.println("xml解析异常");
//            e.printStackTrace();
//        }
//        Element rootNode = document.getRootElement();
//        Iterator<Element> it = rootNode.elementIterator();
//        Znode currNode = new Znode();
//        currNode.setFacno(facno);
//        while (it.hasNext()) {
//            Element node = it.next();
//            if ("sequenceFlow".equals(node.getName())) {
//                if (facno.equals(node.attribute("sourceRef").getValue())) {
//                    currNode.setTarno(node.attribute("targetRef").getValue());
//                    break;
//                }
//            }
//        }
//        List<Znode> list = new ArrayList<>();
//        List<Znode> nextNodeList = new ArrayList<>();
//        nodeFlow(zcond, rootNode, list, nextNodeList, currNode, false);
//        return nextNodeList.get(0);
//    }


    public List<SidNamePid> GetAllLineList(String xml) {
        //根据prdid寻找xml的filename
        xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>"
            + "\n<process" +
            xml.split("bpmn2:process")[1].replaceAll("bpmn2:", "").replaceAll("activiti:", "") + "process>";

        SAXReader reader = new SAXReader();
        Reader stringReader = new StringReader(xml);
        Document document = null;
        try {
            document = reader.read(stringReader);
        } catch (DocumentException e) {
            System.out.println("xml解析异常");
            e.printStackTrace();
        }
        Element rootNode = document.getRootElement();

        List<SidNamePid> list = new ArrayList<>();
        Iterator<Element> it = rootNode.elementIterator();
        while (it.hasNext()) {
            Element node = it.next();
            if ("sequenceFlow".equals(node.getName())) {
                SidNamePid zinp = new SidNamePid();
                zinp.setId(node.attribute("id").getValue() + "");
                zinp.setName(node.attribute("sourceRef").getValue() + "");
                zinp.setPid(node.attribute("targetRef").getValue() + "");
                list.add(zinp);
            }
        }
        return list;
    }

    private final JdbcTemplate jdbcTemplate;

}
