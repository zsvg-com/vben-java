package vben.base.sys.group;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.base.sys.actor.Actor;
import vben.base.sys.actor.ActorDao;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.dto.PageData;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.satoken.utils.LoginHelper;

import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class SysGroupService {

    @Transactional(readOnly = true)
    public SysGroup findById(String id) {
        return groupDao.findById(id);
    }

    @Transactional(readOnly = true)
    public PageData findPageData(Sqler sqler) {
        return jdbcHelper.findPageData(sqler);
    }

    public void insert(SysGroup main) {
        if (main.getId() == null) {
            main.setId("g"+IdUtils.getSnowflakeNextIdStr());
        }
        main.setCruid(LoginHelper.getUserId());
        Actor org = new Actor(main.getId(), main.getName(), 8);
        actorDao.insert(org);
        groupDao.insert(main);
    }

    public void update(SysGroup main) {
        main.setUptim(new Date());
        main.setUpuid(LoginHelper.getUserId());
        Actor org = new Actor(main.getId(), main.getName(), 8);
        actorDao.update(org);
        groupDao.update(main);
    }

    public int delete(String[] ids) {
        for (String id : ids) {
            groupDao.deleteById(id);
            actorDao.deleteById(id);
        }
        return ids.length;
    }

    private final JdbcHelper jdbcHelper;

    private final SysGroupDao groupDao;

    private final ActorDao actorDao;

}
