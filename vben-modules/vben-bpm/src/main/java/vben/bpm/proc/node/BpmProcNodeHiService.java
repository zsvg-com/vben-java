package vben.bpm.proc.node;

import lombok.RequiredArgsConstructor;

import vben.bpm.root.domain.Zproc;
import vben.bpm.root.domain.Znode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
@RequiredArgsConstructor
public class BpmProcNodeHiService {

    private final BpmProcNodeHiDao dao;

    public void saveStartNode(Zproc zproc) {
        BpmProcNodeHi startNode = new BpmProcNodeHi();
        startNode.setFacno("NS");
        startNode.setFacna("开始节点");
        startNode.setFacty("start");
        startNode.setProid(zproc.getProid());
        startNode.setState("30");
        startNode.setEntim(new Date());
        startNode.setTarno("N1");
        startNode.setTarna("起草节点");
        dao.insert(startNode);
    }

    public void saveDraftNode(Zproc zproc, Znode znode) {
        BpmProcNodeHi draftNode = new BpmProcNodeHi();
        draftNode.setFacno("N1");
        draftNode.setFacna("起草节点");
        draftNode.setFacty("draft");
        draftNode.setProid(zproc.getProid());
        draftNode.setState("30");
        draftNode.setId(znode.getNodid());
        draftNode.setEntim(new Date());
        draftNode.setTarno(znode.getTarno());
        draftNode.setTarna(znode.getTarna());
        dao.insert(draftNode);
    }

    public Long saveEndNode(Zproc zproc) {
        BpmProcNodeHi endNode = new BpmProcNodeHi();
        endNode.setFacno("NE");
        endNode.setFacna("结束节点");
        endNode.setFacty("end");
        endNode.setProid(zproc.getProid());
        endNode.setState("30");
        endNode.setEntim(new Date());
        dao.insert(endNode);
        return endNode.getId();
    }

    public void saveNodeList(Zproc zproc, List<Znode> list) {
        for (Znode znode : list) {
            BpmProcNodeHi node = new BpmProcNodeHi();
            node.setFacno(znode.getFacno());
            node.setFacna(znode.getFacna());
            node.setFacty(znode.getFacty());
            node.setProid(zproc.getProid());
            node.setTarno(znode.getTarno());
            node.setTarna(znode.getTarna());
            node.setState("30");
            node.setEntim(new Date());
            dao.insert(node);
        }
    }

    public BpmProcNodeHi saveNode(BpmProcNode main) {
        BpmProcNodeHi node = new BpmProcNodeHi();
        node.setId(main.getId());
        node.setFacno(main.getFacno());
        node.setFacna(main.getFacna());
        node.setFacty(main.getFacty());
        node.setFlway(main.getFlway());
        node.setProid(main.getProid());
        node.setState("20");
        dao.insert(node);
        return node;
    }

    @Transactional(readOnly = true)
    public BpmProcNodeHi findOne(Long id) {
        return dao.findById(id);
    }

    public void delete(Long id) {
        dao.deleteById(id);
    }

    public List<String> findFacnoListByProid(Long proid) {
        return dao.findFacnoListByProid(proid);
    }

    public void update(BpmProcNodeHi nodeHi) {
       dao.update(nodeHi);
    }
}
