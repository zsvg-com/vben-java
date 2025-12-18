package vben.bpm.proc.node;

import lombok.RequiredArgsConstructor;
import vben.bpm.root.domain.Zproc;
import vben.bpm.root.domain.Znode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@Service
@RequiredArgsConstructor
public class BpmProcNodeService {

    private final BpmProcNodeDao dao;

    public BpmProcNode saveNode(Zproc zproc, Znode znode) {
        BpmProcNode node = new BpmProcNode();
        node.setFacno(znode.getFacno());
        node.setFacna(znode.getFacna());
        node.setFacty(znode.getFacty());
        node.setFlway(znode.getFlway());
        node.setProid(zproc.getProid());
        node.setState("20");
        dao.insert(node);
        return node;
    }

    @Transactional(readOnly = true)
    public BpmProcNode findOne(Long id) {
        return dao.findById(id);
    }

    public void delete(Long id) {
        dao.deleteById(id);
    }


}
