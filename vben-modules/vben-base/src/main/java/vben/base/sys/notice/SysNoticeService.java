package vben.base.sys.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;

import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class SysNoticeService {

    public PageData findPageData(Sqler sqler) {
        return dao.findPageData(sqler);
    }

    public SysNotice findById(Long id) {
        return dao.findById(id);
    }

    public void insert(SysNotice main) {
        main.setId(IdUtils.getSnowflakeNextId());
        main.setUptim(main.getCrtim());
        dao.insert(main);
    }

    public void update(SysNotice main) {
        main.setUptim(new Date());
        dao.update(main);
    }

    public int delete(Long[] ids) {
        for (Long id : ids) {
            dao.deleteById(id);
        }
        return ids.length;
    }

    private final SysNoticeDao dao;
}
