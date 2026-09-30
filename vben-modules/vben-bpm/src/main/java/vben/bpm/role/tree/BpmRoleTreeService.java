package vben.bpm.role.tree;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.base.sys.actor.Actor;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.bpm.role.main.BpmRole;
import vben.bpm.role.main.BpmRoleDao;

import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class BpmRoleTreeService {

    @Transactional(readOnly = true)
    public BpmRoleTree findById(String id) {
        return treedao.findById(id);
    }

    public String insert(BpmRoleTree main) {
        main.setId(IdUtils.getSnowflakeNextIdStr());
        treedao.insert(main);
        for (BpmRole role : main.getRoles()) {
            role.setTreid(main.getId());
            roleDao.insert(role);
        }
        //        List<SysActor> list = new ArrayList<>();
//        for (BpmRoleEntity role : main.getRoles()) {
//            SysActor sysActor = new SysActor(role.getId(), role.getName(), 32);
//            list.add(sysActor);
//        }
//        actorDao.saveAll(list);
        return main.getId();
    }

    public String update(BpmRoleTree main) {
        treedao.update(main);
        roleDao.deleteByTreid(main.getId());
        for (BpmRole role : main.getRoles()) {
            role.setTreid(main.getId());
            roleDao.insert(role);
        }

//        List<SysActor> list = new ArrayList<>();
//        for (BpmRole role : main.getRoles()) {
//            SysActor sysActor = new SysActor(role.getId(), role.getName(), 32);
//            list.add(sysActor);
//        }
//        actorDao.saveAll(list);
        return main.getId();
    }

    public int delete(String[] ids) {
        for (String id : ids) {
            roleDao.deleteByTreid(id);
            treedao.deleteById(id);
        }
        return ids.length;
    }


    public Actor calc(String useid, String rolid) {
        String sql = "select t.tier \"tier\",m.ornum \"ornum\" from bpm_role_node t " +
                "inner join bpm_role m on m.treid=t.treid where t.memid=? and m.id=?";
        Map<String, Object> map = jdbcHelper.findMap(sql, useid, rolid);
        if (map == null) {
            sql = "select t.tier \"tier\",m.ornum \"ornum\" from bpm_role_node t " +
                    "inner join bpm_role m on m.treid=t.treid " +
                    "inner join sys_user u on u.orgid=t.memid where u.id=? and m.id=?";
            map = jdbcHelper.findMap(sql, useid, rolid);
            if (map == null) {
                return null;
            }
        }
        String tier = (String) map.get("tier");
        Integer ornum =(Integer) map.get("ornum");
        String[] idArr = tier.split("_");
        String sql3 = "select a.id,a.name,a.type from bpm_role_node t inner join sys_actor a on a.id=t.memid where t.id=?";
        Actor actor = jdbcHelper.getTp().queryForObject(sql3,
                new Object[]{idArr[ornum]}, new BeanPropertyRowMapper<>(Actor.class));
        return actor;
    }

    private final BpmRoleDao roleDao;

    private final BpmRoleTreeDao treedao;

    private final JdbcHelper jdbcHelper;

}

