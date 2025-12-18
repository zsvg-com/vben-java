package vben.base.mon.cache;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vben.common.core.domain.R;
import vben.common.core.utils.StrUtils;

import java.util.*;

/**
 * 缓存监控
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/mon/cache")
public class MonCacheApi {

    private final RedissonConnectionFactory connectionFactory;

    /**
     * 获取缓存监控列表
     */
    @SaCheckPermission("mon:cache:query")
    @GetMapping()
    public R<CacheListInfoVo> getInfo()  {

        RedisConnection connection = connectionFactory.getConnection();
        try {
            Properties commandStats = connection.commands().info("commandstats");
            List<Map<String, String>> pieList = new ArrayList<>();
        if (commandStats != null) {
            commandStats.stringPropertyNames().forEach(key -> {
                Map<String, String> data = new HashMap<>(2);
                String property = commandStats.getProperty(key);
                data.put("name", StrUtils.removePrefix(key, "cmdstat_"));
                data.put("value", StrUtils.subBetween(property, "calls=", ",usec"));
                pieList.add(data);
            });
        }
        return R.ok(new CacheListInfoVo(
            connection.commands().info(),
            connection.commands().dbSize(), pieList));
        } finally {
            // 归还连接给连接池
            RedisConnectionUtils.releaseConnection(connection, connectionFactory);
        }
    }

    /**
     * 缓存监控列表信息
     *
     * @param info         信息
     * @param dbSize       数据库
     * @param commandStats 命令统计
     */
    public record CacheListInfoVo(Properties info, Long dbSize, List<Map<String, String>> commandStats) {}

}
