package vben.bpm.bus.leave;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.common.core.utils.DateUtils;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;
import vben.common.satoken.utils.LoginHelper;
import vben.bpm.bus.main.BpmBusMain;
import vben.bpm.bus.main.BpmBusMainService;

import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class BpmBusLeaveService {

    public PageData findPageData(Sqler sqler) {
        return dao.findPageData(sqler);
    }

    public BpmBusLeave findById(Long id) {
        return dao.findById(id);
    }

    public Long insert(BpmBusLeave main) {
        if(main.getSttim() != null&&main.getEntim()!=null) {
            long day = DateUtils.betweenDay(main.getSttim(), main.getEntim(), true);
            main.setDays((int) day + 1);
        }
        main.setId(IdUtils.getSnowflakeNextId());
        main.setCruid(LoginHelper.getUserId());
        dao.insert(main);


        //启动流程
        BpmBusMain flowBo = main.getFlowBo();
        flowBo.setId(Long.valueOf(main.getId()));
        flowBo.setCruid(main.getCruid());
        bpmBusMainService.insert(flowBo);
        return main.getId();
    }

    public Long update(BpmBusLeave main) {
        main.setUpuid(LoginHelper.getUserId());
        main.setUptim(new Date());
        dao.update(main);


        //流转流程
        BpmBusMain flowBo = main.getFlowBo();
        bpmBusMainService.update(flowBo);
        return main.getId();
    }

    public int delete(Long[] ids) {
        for (Long id : ids) {
            dao.deleteById(id);
        }
        return ids.length;
    }

    private final BpmBusMainService bpmBusMainService;

    private final BpmBusLeaveDao dao;
}
