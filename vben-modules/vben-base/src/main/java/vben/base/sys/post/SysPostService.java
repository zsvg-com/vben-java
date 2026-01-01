package vben.base.sys.post;

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
public class SysPostService {

    public PageData findPageData(Sqler sqler) {
        return jdbcHelper.findPageData(sqler);
    }

    public void insert(SysPost main) {
        if (main.getId() == null ) {
            main.setId("p"+IdUtils.getSnowflakeNextIdStr());
        }
        main.setCruid(LoginHelper.getUserId());
        String tier = jdbcHelper.findString("select tier from sys_dept where id=?", main.getDepid());
        main.setTier(tier + main.getId() + "_");
        Org org = new Org(main.getId(), main.getName(),4);
        orgDao.insert(org);
        postDao.insert(main);
    }

    public void update(SysPost main) {
        main.setUptim(new Date());
        main.setUpuid(LoginHelper.getUserId());
        String tier = jdbcHelper.findString("select tier from sys_dept where id=?", main.getDepid());
        main.setTier(tier  +main.getId() + "_");
        Org org = new Org(main.getId(), main.getName(),4);
        postDao.update(main);
        orgDao.update(org);
    }

    public int delete(String[] ids) {
        for (String id : ids) {
            postDao.deleteById(id);
            orgDao.deleteById(id);
        }
        return ids.length;
    }

    @Transactional(readOnly = true)
    public SysPost findById(String id) {
        return postDao.findById(id);
    }

    private final JdbcHelper jdbcHelper;

    private final SysPostDao postDao;

    private final OrgDao orgDao;

}
