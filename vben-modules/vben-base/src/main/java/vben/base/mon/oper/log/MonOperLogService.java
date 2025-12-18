package vben.base.mon.oper.log;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.common.core.utils.IdUtils;
import vben.common.core.utils.ip.AddressUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;
import vben.common.log.event.OperLogEvent;

import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class MonOperLogService {

    @Transactional(readOnly = true)
    public PageData findPageData(Sqler sqler) {
        return dao.findPageData(sqler);
    }

    /**
     * 操作日志记录
     *
     * @param operLogEvent 操作日志事件
     */
    @Async
    @EventListener
    public void recordOper(OperLogEvent operLogEvent) {
        MonOperLog log = new MonOperLog();
        log.setTenid(operLogEvent.getTenantId());
        log.setOpmod(operLogEvent.getTitle());
        log.setButyp(operLogEvent.getBusinessType());
        log.setRemet(operLogEvent.getMethod());
        log.setReway(operLogEvent.getRequestMethod());
        log.setOpuna(operLogEvent.getOperName());
        log.setOpdna(operLogEvent.getDeptName());
        log.setReurl(operLogEvent.getOperUrl());
        log.setOpip(operLogEvent.getOperIp());
        log.setRepar(operLogEvent.getOperParam());
        log.setBapar(operLogEvent.getJsonResult());
        log.setSutag(operLogEvent.getStatus()==0);
        log.setErmsg(operLogEvent.getErrorMsg());
        log.setOptim(operLogEvent.getOperTime());
        log.setCotim(operLogEvent.getCostTime());
        log.setOptyp(operLogEvent.getOperatorType());
        // 远程查询操作地点
        log.setOploc(AddressUtils.getRealAddressByIP(log.getOpip()));
        insert(log);
    }


    public MonOperLog findById(Long id) {
        return dao.findById(id);
    }

    public void insert(MonOperLog main) {
        main.setOptim(new Date());
        main.setId(IdUtils.getSnowflakeNextId());
        dao.insert(main);
    }

    public int delete(Long[] ids) {
        for (Long id : ids) {
            dao.deleteById(id);
        }
        return ids.length;
    }

    private final MonOperLogDao dao;
}
