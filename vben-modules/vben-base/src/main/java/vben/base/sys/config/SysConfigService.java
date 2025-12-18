package vben.base.sys.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.common.core.constant.CacheNames;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;
import vben.common.redis.utils.CacheUtils;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class SysConfigService {

    @Cacheable(cacheNames = CacheNames.SYS_CONFIG, key = "#kenam")
    public String findValue(String kenam) {
        return dao.findValue(kenam);
    }

    @Transactional(readOnly = true)
    public PageData findPageData(Sqler sqler) {
        return dao.findPageData(sqler);
    }

    public SysConfig findById(Long id) {
        return dao.findById(id);
    }

    @CachePut(cacheNames = CacheNames.SYS_CONFIG, key = "#main.kenam")
    public Long insert(SysConfig main) {
        main.setId(IdUtils.getSnowflakeNextId());
        dao.insert(main);
        return main.getId();
    }

    @CachePut(cacheNames = CacheNames.SYS_CONFIG, key = "#main.kenam")
    public Long update(SysConfig main) {
        dao.update(main);
        return main.getId();
    }

    public int delete(Long[] ids) {
        for (Long id : ids) {
            SysConfig sysConfig = dao.findById(id);
            CacheUtils.evict(CacheNames.SYS_CONFIG, sysConfig.getKenam());
            dao.deleteById(id);
        }
        return ids.length;
    }

    public void clearCache() {
        CacheUtils.clear(CacheNames.SYS_CONFIG);
    }

    private final SysConfigDao dao;
}
