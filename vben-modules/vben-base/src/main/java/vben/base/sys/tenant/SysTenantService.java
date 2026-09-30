package vben.base.sys.tenant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class SysTenantService {

    @Transactional(readOnly = true)
    public PageData findPageData(Sqler sqler) {
        return dao.findPageData(sqler);
    }

    public SysTenant findById(Long id) {
        return dao.findById(id);
    }

    public Long insert(SysTenant main) {
        main.setId(IdUtils.getSnowflakeNextId());
        dao.insert(main);
        return main.getId();
    }

    public Long update(SysTenant main) {
        dao.update(main);
        return main.getId();
    }

    public int delete(Long[] ids) {
        for (Long id : ids) {
            dao.deleteById(id);
        }
        return ids.length;
    }

    private final SysTenantDao dao;
}
