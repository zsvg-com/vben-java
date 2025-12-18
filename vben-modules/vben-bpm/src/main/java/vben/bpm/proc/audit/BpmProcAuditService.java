package vben.bpm.proc.audit;


import lombok.RequiredArgsConstructor;
import vben.bpm.root.domain.Zproc;
import vben.bpm.root.domain.Znode;
import vben.bpm.root.domain.vo.BpmRefuseInfoVo;
import vben.bpm.root.domain.vo.BpmAuditVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
@RequiredArgsConstructor
public class BpmProcAuditService {

    private final BpmProcAuditDao dao;

    public BpmProcAudit saveDraftAudit(Zproc zproc, Znode znode) {
        BpmProcAudit audit = new BpmProcAudit();
        audit.setFacno(znode.getFacno());
        audit.setFacna(znode.getFacna());
        audit.setNodid(znode.getNodid());
        audit.setHauid(zproc.getHauid());
        audit.setProid(zproc.getProid());
        audit.setOpnot(zproc.getOpnot());
        audit.setAtids(zproc.getAtids());
        audit.setOpkey("dsubmit");
        audit.setOpinf("起草人提交");
        dao.insert(audit);
        return audit;
    }

    public BpmProcAudit saveAudit(Zproc zproc) {
        BpmProcAudit audit = new BpmProcAudit();
        audit.setFacno(zproc.getFacno());
        audit.setFacna(zproc.getFacna());
        audit.setNodid(zproc.getNodid());
        audit.setHauid(zproc.getHauid());
        audit.setProid(zproc.getProid());
        audit.setOpnot(zproc.getOpnot());
        audit.setOpkey(zproc.getOpkey());
        audit.setOpinf(zproc.getOpinf());
        audit.setTasid(zproc.getTasid());
        audit.setAtids(zproc.getAtids());
        dao.insert(audit);
        return audit;
    }

    public BpmProcAudit saveEndAudit(Zproc zproc, Long nodid) {
        BpmProcAudit audit = new BpmProcAudit();
        audit.setFacno("NE");
        audit.setFacna("结束节点");
        audit.setNodid(nodid);
        audit.setProid(zproc.getProid());
        audit.setOpkey("end");
        audit.setOpinf("结束流程");
        dao.insert(audit);
        return audit;
    }

    public List<BpmAuditVo> findListByProid(Long proid) {
       return dao.findListByProid(proid);
    }

    public List<BpmRefuseInfoVo> findCanRefuseNodeList(Long proid) {
        return dao.findCanRefuseNodeList(proid);
    }
}
