package vben.bpm.proc.inst;

import lombok.RequiredArgsConstructor;
import vben.bpm.root.domain.Zproc;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(rollbackFor = Exception.class)
@Service
@RequiredArgsConstructor
public class BpmProcInstService {

    private final BpmProcInstDao dao;

    public void insert(BpmProcInst process) {
        dao.insert(process);
    }

    public void updateEndState(Zproc zproc) {
        dao.updateEndState(zproc.getProid());
    }

}
