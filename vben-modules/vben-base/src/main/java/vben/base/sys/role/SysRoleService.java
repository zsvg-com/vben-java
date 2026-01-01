package vben.base.sys.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;
import vben.common.redis.utils.RedisUtils;
import vben.common.satoken.utils.LoginHelper;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class SysRoleService {

    private final SysRoleDao dao;

    @Transactional(readOnly = true)
    public PageData findPageData(Sqler sqler) {
        return dao.findPageData(sqler);
    }

    public SysRole findById(Long id) {
        return dao.findById(id);
    }

    public List<MenuVo> findMenuVoList() {
        return dao.findMenuVoList();
    }

    public void insert(SysRole main) {
        main.setId(IdUtils.getSnowflakeNextId());
        main.setCruid(LoginHelper.getUserId());
        dao.insert(main);
        clearCache();
    }

    public void update(SysRole main) {
        dao.update(main);
        main.setUpuid(LoginHelper.getUserId());
        clearCache();
    }

    public int delete(Long[] ids) {
        for (Long id : ids) {
            dao.deleteById(id);
        }
        clearCache();
        return ids.length;
    }

    public void clearCache() {
        RedisUtils.deleteKeys("rlist:*");
        RedisUtils.deleteKeys("oids:*");
    }
}
