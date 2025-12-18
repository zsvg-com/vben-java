package vben.bpm.proc.param;


import lombok.RequiredArgsConstructor;
import vben.bpm.root.domain.dto.BpmParamDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@Service
@RequiredArgsConstructor
public class BpmProcParamService {

    private final BpmProcParamDao dao;

    public void save(BpmProcParam param) {
        dao.insert(param);
    }

    public void delete(Long id) {
        dao.deleteById(id);
    }

    public BpmParamDto findParam(Long proid, String pakey) {
        return dao.findParam(proid, pakey);
    }
}
