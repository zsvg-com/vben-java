package vben.base.mon.server;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vben.common.core.domain.R;

/**
 * 服务器监控
 */
@RestController
@RequestMapping("mon/server")
public class MonServerApi {


    /**
     * 服务器信息查询
     * 这个执行一般要1~2秒时间，为演示时快速返回，启用了24小时缓存
     * @return 服务器信息对象
     * @throws Exception 服务器信息收集异常
     */
    @SaCheckPermission("mon:server:query")
    @GetMapping
    @Cacheable(cacheNames = "server#24h")
    public R<Server> get() throws Exception {
        Server server = new Server();
        server.copyTo();
        return R.ok(server);
    }

}
