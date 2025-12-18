package vben.bpm.proc.def;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.bpm.root.domain.Zproc;
import vben.bpm.root.domain.vo.BpmProcDefVo;


@Transactional(rollbackFor = Exception.class)
@Service
@RequiredArgsConstructor
public class BpmProcDefService {

    public BpmProcDefVo findVoById(Long id) {
        return dao.findVoById(id);
    }

    private final BpmProcDefDao dao;

    @Transactional(readOnly = true)
    public void setExxml(Zproc zproc) {
        String exxml = null;
        if (zproc.getPrdid() != null) {
            exxml = dao.selectExxmlById(zproc.getPrdid());
        } else if (zproc.getProid() != null) {
            exxml = dao.selectExxmlByProid(zproc.getProid());
        }
        zproc.setExxml(exxml);
    }

    /**
     * 获取流程展示图XML
     */
    @Transactional(readOnly = true)
    public String findDixmlById(Long prdid) {
        return dao.selectDixmlById(prdid);
    }

    /**
     * 获取流程执行图XML
     */
    @Transactional(readOnly = true)
    public String findExxmlById(Long prdid) {
        return dao.selectExxmlById(prdid);
    }

    /**
     * 获取流程展示图XML
     */
    @Transactional(readOnly = true)
    public String findDixmlByProid(Long proid) {
        return dao.selectDixmlByProid(proid);
    }

    /**
     * 获取流程执行图XML
     */
    @Transactional(readOnly = true)
    public String findExxmlByProid(Long proid) {
        return dao.selectExxmlByProid(proid);
    }
}
