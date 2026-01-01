package vben.base.mon.login.log;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.base.sys.user.SysUserDao;
import vben.common.core.constant.Constants;
import vben.common.core.utils.IdUtils;
import vben.common.core.utils.ServletUtils;
import vben.common.core.utils.StrUtils;
import vben.common.core.utils.ip.AddressUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;
import vben.common.log.event.LogininforEvent;

import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class MonLoginLogService {

    @Transactional(readOnly = true)
    public PageData findPageData(Sqler sqler) {
        return dao.findPageData(sqler);
    }

    /**
     * 记录登录信息
     *
     * @param logininforEvent 登录事件
     */
    @Async
    @EventListener
    public void recordLogininfor(LogininforEvent logininforEvent) {
        HttpServletRequest request = logininforEvent.getRequest();
        final UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        final String ip = ServletUtils.getClientIP(request);
        // 客户端信息
//        String clientId = request.getHeader(LoginHelper.CLIENT_KEY);
//        SysClientVo client = null;
//        if (StrUtils.isNotBlank(clientId)) {
//            client = clientService.queryByClientId(clientId);
//        }

        String address = AddressUtils.getRealAddressByIP(ip);
        StringBuilder s = new StringBuilder();
        s.append(getBlock(ip));
        s.append(address);
        s.append(getBlock(logininforEvent.getUsername()));
        s.append(getBlock(logininforEvent.getStatus()));
        s.append(getBlock(logininforEvent.getMessage()));
        // 打印信息到日志
        log.info(s.toString(), logininforEvent.getArgs());
        // 获取客户端操作系统
        String os = userAgent.getOs().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        // 封装对象
        MonLoginLog log = new MonLoginLog();
        log.setTenid(logininforEvent.getTenantId());
        log.setUsername(logininforEvent.getUsername());
//        if (ObjectUtils.isNotNull(client)) {
//            logininfor.setClientKey(client.getClientKey());
//            logininfor.setDeviceType(client.getDeviceType());
//        }
        log.setLoip(ip);
        log.setLoloc(address);
        log.setBrowser(browser);
        log.setOs(os);
        log.setHimsg(logininforEvent.getMessage());
        // 日志状态
        if (StrUtils.equalsAny(logininforEvent.getStatus(), Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
            log.setSutag(true);
        } else if (Constants.LOGIN_FAIL.equals(logininforEvent.getStatus())) {
            log.setSutag(false);
        }
        // 插入数据
        insert(log);
        // 更新登录信息
        userDao.updateLogin(log);
    }

    private String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }


    public MonLoginLog findById(Long id) {
        return dao.findById(id);
    }

    public void insert(MonLoginLog main) {
        main.setLotim(new Date());
        main.setId(IdUtils.getSnowflakeNextId());
        dao.insert(main);
    }

    public int delete(Long[] ids) {
        for (Long id : ids) {
            dao.deleteById(id);
        }
        return ids.length;
    }

    private final MonLoginLogDao dao;

    private final SysUserDao userDao;
}
