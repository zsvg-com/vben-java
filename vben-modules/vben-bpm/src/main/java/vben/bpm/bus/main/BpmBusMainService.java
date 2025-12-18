package vben.bpm.bus.main;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.common.core.exception.ServiceException;
import vben.common.core.utils.IdUtils;
import vben.common.core.utils.StrUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;
import vben.common.satoken.utils.LoginHelper;
import vben.bpm.root.BpmService;
import vben.bpm.root.domain.Zproc;

import java.util.Date;


@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BpmBusMainService {

    @Transactional(readOnly = true)
    public PageData findPageData(Sqler sqler) {
        return dao.findPageData(sqler);
    }

    public Long insert(BpmBusMain main) {
        if(main.getCruid() == null) {
            main.setCruid(LoginHelper.getUserId());
        }
        main.setState("20");
        if(main.getId() == null) {
            main.setId(IdUtils.getSnowflakeNextId());
        }
        dao.insert(main);
        //启动流程
        Zproc zproc = new Zproc();
        zproc.setBusty("bus");
        zproc.setProid(main.getId());
        zproc.setProna(main.getName());
        zproc.setInuid(main.getCruid());
        zproc.setPrdid(main.getPrdid());
        zproc.setAtids(main.getBpmStartBo().getAtids());
        zproc.setOpnot(main.getBpmStartBo().getOpnot());
        zproc.setOpurg(main.getBpmStartBo().getOpurg());

        try {
            bpmService.start(zproc);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("流程启动异常："+e.getMessage());
        }
        return main.getId();
    }

    public Long update(BpmBusMain main)  {
        main.setUpuid(LoginHelper.getUserId());
        main.setUptim(new Date());
        dao.update(main);
        main.getZproc().setBusty("bus");
        main.getZproc().setProna(main.getName());
        main.getZproc().setProid(main.getId());
        main.getZproc().setInuid(main.getCruid());
        String state= bpmService.flow(main.getZproc());
        if(StrUtils.isNotBlank(state)){
            main.setState(state);
            dao.update(main);
        }
        return main.getId();
    }

    @Transactional(readOnly = true)
    public BpmBusMain findById(Long id) {
        return dao.findById(id);
    }


    public int delete(Long[] ids) {
        for (Long str : ids) {
            dao.deleteById(str);
        }
        return ids.length;
    }

    private final BpmService bpmService;

    private final BpmBusMainDao dao;

}

