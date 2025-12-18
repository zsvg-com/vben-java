package vben.base.sys.perm.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.Sqler;


@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class SysPermApiService {

    private final SysPermApiDao dao;

    @Transactional(readOnly = true)
    public PageData findPageData(Sqler sqler) {
        return dao.findPageData(sqler);
    }

    @Transactional(readOnly = true)
    public SysPermApi findById(Long id) {
        return dao.findById(id);
    }

    public void insert(SysPermApi main) {
        dao.insert(main);
    }

    public void update(SysPermApi main) {
        dao.update(main);
    }

    public int delete(Long[] ids) {
        for (Long id : ids) {
            dao.deleteById(id);
        }
        return ids.length;
    }

}
