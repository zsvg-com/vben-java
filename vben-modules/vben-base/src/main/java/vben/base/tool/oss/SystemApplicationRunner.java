package vben.base.tool.oss;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import vben.base.tool.oss.service.SysOssConfigService;
import vben.common.redis.utils.RedisUtils;

/**
 * 初始化 system 模块对应业务数据
 *
 * @author Lion Li
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class SystemApplicationRunner implements ApplicationRunner {

    private final SysOssConfigService ossConfigService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        ossConfigService.init();
        RedisUtils.deleteKeys("rlist:*");
        RedisUtils.deleteKeys("oids:*");
        RedisUtils.deleteKeys("sys_dict");
        log.info("初始化OSS配置成功");

    }

}
