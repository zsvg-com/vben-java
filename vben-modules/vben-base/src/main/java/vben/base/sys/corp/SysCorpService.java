package vben.base.sys.corp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.base.sys.org.Org;
import vben.base.sys.org.OrgDao;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.satoken.utils.LoginHelper;

import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class SysCorpService {

    @Transactional(readOnly = true)
    public SysCorp findById(String id) {
        return dao.findById(id);
    }

    @Transactional(readOnly = true)
    public PageData findPageData(Sqler sqler) {
        return jdbcHelper.findPageData(sqler);
    }

    public void insert(SysCorp main) {
        if (main.getId() == null) {
            main.setId("g"+IdUtils.getSnowflakeNextIdStr());
        }
        main.setCruid(LoginHelper.getUserId());
        Org org = new Org(main.getId(), main.getName(), 8);
        orgDao.insert(org);
        dao.insert(main);
    }

    public void update(SysCorp main) {
        main.setUptim(new Date());
        main.setUpuid(LoginHelper.getUserId());
        Org org = new Org(main.getId(), main.getName(), 8);
        orgDao.update(org);
        dao.update(main);
    }

    public int delete(String[] ids) {
        for (String id : ids) {
            dao.deleteById(id);
            orgDao.deleteById(id);
        }
        return ids.length;
    }

    private final JdbcHelper jdbcHelper;

    private final SysCorpDao dao;

    private final OrgDao orgDao;

}
