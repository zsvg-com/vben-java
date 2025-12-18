package vben.bpm.root;

import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vben.common.core.domain.R;
import vben.common.core.utils.StrUtils;
import vben.common.jdbc.dto.SidNamePid;
import vben.common.satoken.utils.LoginHelper;
import vben.common.web.core.BaseController;
import vben.bpm.org.tree.BpmUserService;
import vben.bpm.proc.audit.BpmProcAuditService;
import vben.bpm.proc.def.BpmProcDefService;
import vben.bpm.proc.node.BpmProcNodeHiService;
import vben.bpm.proc.param.BpmProcParamService;
import vben.bpm.proc.task.BpmProcTaskService;
import vben.bpm.root.domain.Znode;
import vben.bpm.root.domain.dto.BpmParamDto;
import vben.bpm.root.domain.dto.BpmProcDto;
import vben.bpm.root.domain.dto.BpmTaskDto;
import vben.bpm.root.domain.vo.*;

import java.util.*;


@RestController
@RequestMapping("/bpm")
@SaIgnore
@RequiredArgsConstructor
public class BpmApi extends BaseController {

    private final BpmProcDefService bpmProcDefService;

    private final BpmUserService bpmUserService;

    private final BpmProcAuditService bpmAuditService;

    private final BpmProcTaskService bpmProcTaskService;

    private final BpmProcNodeHiService bpmProcNodeHiService;

    private final BpmProcParamService bpmProcParamService;

    private final BpmHandler bpmHandler;

    /**
     * 获取流程初始化信息
     */
    @GetMapping("initInfo")
    public R<BpmInitVo> initInfo(Long prdid, String busty) throws Exception {

        String exxml = bpmProcDefService.findExxmlById(prdid);
        BpmProcDto bpmProcDto = new BpmProcDto();
        bpmProcDto.setPrdid(prdid);
        bpmProcDto.setBusty(busty);
//        bpmProcDto.setFacno("N1");
        List<Znode> goalNodes = bpmHandler.getGoalNodes(bpmProcDto,exxml,"N1");
        BpmInitVo vo = new BpmInitVo();

        if (goalNodes.isEmpty()) {
            BpmGoalVo goalVo = new BpmGoalVo();
            goalVo.setTarno("NX");
            goalVo.setTarna("未知节点");
            goalVo.setTamen("暂时无法计算");
            vo.getGoals().add(goalVo);
            return R.ok(vo);
        }
        for (Znode goalNode : goalNodes) {
            String tamen = bpmUserService.calcTamen(null, goalNode.getExuids());
            BpmGoalVo goalVo = new BpmGoalVo();
            goalVo.setTamen(tamen);
            goalVo.setTarna(goalNode.getFacna());
            goalVo.setTarno(goalNode.getFacno());
            vo.getGoals().add(goalVo);
        }
        return R.ok(vo);
    }


    @GetMapping("viewInfo")
    public R<Map<String, Object>> viewInfo(Long proid) {
        Map<String, Object> map = new HashMap<>();

        List<BpmAuditVo> audits = bpmAuditService.findListByProid(proid);
        map.put("audits", audits);

        //历史处理人
        String hiHamen = "";
        for (BpmAuditVo audit : audits) {
            if (audit.getHauna() != null && !hiHamen.contains(audit.getHauna())) {
                hiHamen += audit.getHauna() + ";";
            }
        }
        if (hiHamen.contains(";")) {
            hiHamen = hiHamen.substring(0, hiHamen.length() - 1);
        }
        map.put("hiHamen", hiHamen);

        boolean cutag = false;

        //当前用户是否在当前处理人中
        List<BpmTaskDto> bpmTaskDtoList = bpmProcTaskService.findCurrentExmenByProid(proid);
//        String cuExmen = "";
        String userId = LoginHelper.getUserId();

//        Zproc zproc = new Zproc();
        //当前节点，当用户同时有一个流程的两个待办时，可能有多个节点
        List<BpmTaskVo> cuTasks = new ArrayList<>();
        for (BpmTaskDto task : bpmTaskDtoList) {
//            if (zproc.getProid() == null) {
//                zproc.setProid(proid);
//                zproc.setNodid(task.getNodid());
//                zproc.setFacno(task.getNodno());
//                zproc.setFacna(task.getNodna());
//            }
//            cuExmen += task.getExuna() + ";";

            BpmTaskVo taskVo = new BpmTaskVo();
            taskVo.setNodid(task.getNodid());
            taskVo.setNodno(task.getNodno());
            taskVo.setNodna(task.getNodna());
            taskVo.setTasty(task.getType());
            taskVo.setTasid(task.getId());
            taskVo.setExuna(task.getExuna());
            taskVo.setExuid(task.getExuid());
            cuTasks.add(taskVo);
            if (!cutag) {
                if (userId.equals(task.getExuid())) {
                    taskVo.setCrtag(true);
                    cutag = true;
                }
            }
        }
        //根据岗位来查询当前用户是否在当前处理人中
        if (!cutag) {
            List<String> postIdList = bpmUserService.findPostIdList(userId);
            for (BpmTaskVo cuTask : cuTasks) {
                for (String postId : postIdList) {
                    if (postId.equals(cuTask.getExuid())) {
                        cutag = true;
                        cuTask.setCrtag(true);
                        break;
                    }
                }
                if (cutag) {
                    break;
                }
            }
//            for (BpmTaskDto task : bpmTaskDtoList) {
//
//            }
        }
//        if (cuExmen.contains(";")) {
//            cuExmen = cuExmen.substring(0, cuExmen.length() - 1);
//        }
//        map.put("cuExmen", cuExmen);//当前处理人
        map.put("cutag", cutag);//当前用户是否在当前处理人中
        map.put("cuTasks", cuTasks);//当前节点编号
//        map.put("zproc", zproc);
        return R.ok(map);
    }

    /**
     * 获取流程通过后的走向信息
     *
     * @param proid
     * @param facno
     * @param busty
     * @return
     */
    @GetMapping("passInfo")
    public R<Map<String, Object>> passInfo(Long proid, String facno, String busty) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<BpmGoalVo> goals = new ArrayList<>();

        Znode nextNode;
        //如果是之前被驳回的节点则，通过后要判断是否直接返回驳回的节点
        BpmParamDto paramDto = bpmProcParamService.findParam(proid, facno + "#refuse");
        String exxml = bpmProcDefService.findExxmlByProid(proid);
        if (paramDto != null && StrUtils.isNotBlank(paramDto.getPaval())) {
            Znode backRefuseNode = bpmHandler.getNodeInfo(exxml, paramDto.getPaval());
            String tamen = bpmUserService.calcTamen(proid, backRefuseNode.getExuids());
            BpmGoalVo goalVo = new BpmGoalVo();
            goalVo.setTamen(tamen);
            goalVo.setTarna(backRefuseNode.getFacna());
            goalVo.setTarno(backRefuseNode.getFacno());
            goals.add(goalVo);
            map.put("goals", goals);
            map.put("bacid", paramDto.getId());
            return R.ok(map);
        }

        BpmProcDto bpmProcDto = new BpmProcDto();
        bpmProcDto.setProid(proid);
        bpmProcDto.setBusty(busty);
//        bpmProcDto.setFacno(facno);
//        List<Znode> goalNodes = bpmHandler.getGoalNodes(null, proid, busty, exxml, facno);
        List<Znode> goalNodes = bpmHandler.getGoalNodes(bpmProcDto, exxml,facno);
        if (goalNodes.isEmpty()) {
            BpmGoalVo goalVo = new BpmGoalVo();
            goalVo.setTarno("NX");
            goalVo.setTarna("未知节点");
            goalVo.setTamen("暂时无法计算");
            goals.add(goalVo);
            map.put("goals", goals);
            return R.ok(map);
        }
        for (Znode goalNode : goalNodes) {
            String tamen = bpmUserService.calcTamen(null, goalNode.getExuids());
            BpmGoalVo goalVo = new BpmGoalVo();
            goalVo.setTamen(tamen);
            goalVo.setTarna(goalNode.getFacna());
            goalVo.setTarno(goalNode.getFacno());
            goals.add(goalVo);
        }
        map.put("goals", goals);
        return R.ok(map);

//        else {
//            List<BpmTask> bpmTaskList = bpmTaskService.findAllByProidNotActive(proid);
//            if (bpmTaskList.size() > 0) {
//                nextNode = bpmndler.getNodeInfo(exxml, facno);
//            } else {
//                Zcond zcond = new Zcond();
//                zcond.setProid(proid);
//                zcond.setBusty(busty);
//                nextNode = bpmHandler.calcTarget(zcond, exxml, facno);
//            }
//        }
//        String tamen = "暂时无法计算";
//        if (nextNode == null) {
//            nextNode = new Znode();
//            nextNode.setFacno("NX");
//            nextNode.setFacna("未知节点");
//        } else {
//            tamen = bpmUserService.calcTamen(proid, nextNode.getExuids());
//        }
//        map.put("tarno", nextNode.getFacno());
//        map.put("tarna", nextNode.getFacna());
//        map.put("tamen", tamen);
//        if (paramDto != null) {
//            map.put("bacid", paramDto.getId());
//        }
        //return R.ok(map);
    }

    /**
     * 获取流程初始化XML
     */
    @GetMapping("initXml")
    public R<String> initXml(Long prdid) {
        return R.ok("操作成功", bpmProcDefService.findDixmlById(prdid));
    }

    /**
     * 流程流转时获取流程图XML信息
     *
     * @param proid
     * @return
     */
    @GetMapping("viewXml")
    public R viewXml(Long proid) {
        Map<String, Object> map = new HashMap<>();
        String dixml = bpmProcDefService.findDixmlByProid(proid);
        List<String> nodeList = bpmProcNodeHiService.findFacnoListByProid(proid);
        List<SidNamePid> allLineList = bpmHandler.GetAllLineList(dixml);
        HashSet<String> lineSet = new HashSet<>();
        for (SidNamePid zinp : allLineList) {
            for (String node : nodeList) {
                if (node.equals(zinp.getName())) {
                    for (String node2 : nodeList) {
                        if (node2.equals(zinp.getPid())) {
                            lineSet.add(zinp.getId());
                            break;
                        }
                    }
                    break;
                }
            }
        }

        map.put("xml", dixml);
        map.put("nodeList", nodeList);
        map.put("lineList", lineSet);
        return R.ok(map);
    }


    /**
     * 获取流程驳回时可驳回的节点
     *
     * @param proid
     * @param facno
     * @return
     */
    @GetMapping("refuseInfo")
    public R<List<BpmRefuseInfoVo>> refuseInfo(Long proid, String facno) {
        List<BpmRefuseInfoVo> refuseNodeList = bpmAuditService.findCanRefuseNodeList(proid);
        List<BpmRefuseInfoVo> list = new ArrayList<>();
        for (BpmRefuseInfoVo node : refuseNodeList) {
            if (facno.equals(node.getRefno())) {
                break;
            }
            boolean flag = false;
            for (BpmRefuseInfoVo node2 : list) {
                if (node.getRefno().equals(node2.getRefno())) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                list.add(node);
            }
        }
        return R.ok(list);
    }

    /**
     * 取消沟通时获取可取消的沟通人员
     *
     * @param proid
     * @return
     */
    @GetMapping("ccInfo")
    public R ccInfo(String proid) {
        return R.ok(bpmProcTaskService.findCanCancelCommunitMen(proid));
    }


    @GetMapping("def/{id}")
    public R<BpmProcDefVo> getInfo(@PathVariable("id") Long id) {
        return R.ok(bpmProcDefService.findVoById(id));
    }



}
