package vben.bpm.org.tree;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.base.sys.org.Org;
import vben.common.core.utils.IdUtils;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.bpm.org.role.BpmOrgRole;
import vben.bpm.org.role.BpmOrgRoleDao;

import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class BpmOrgTreeService {

    @Transactional(readOnly = true)
    public BpmOrgTree findById(String id) {
        return treedao.findById(id);
    }

    public String insert(BpmOrgTree main) {
        main.setId(IdUtils.getSnowflakeNextIdStr());
        treedao.insert(main);
        for (BpmOrgRole role : main.getRoles()) {
            role.setTreid(main.getId());
            roleDao.insert(role);
        }
        //        List<SysOrg> list = new ArrayList<>();
//        for (BpmOrgRoleEntity role : main.getRoles()) {
//            SysOrg sysOrg = new SysOrg(role.getId(), role.getName(), 32);
//            list.add(sysOrg);
//        }
//        orgRepo.saveAll(list);
        return main.getId();
    }

    public String update(BpmOrgTree main) {
        treedao.update(main);
        roleDao.deleteByTreid(main.getId());
        for (BpmOrgRole role : main.getRoles()) {
            role.setTreid(main.getId());
            roleDao.insert(role);
        }

//        List<SysOrg> list = new ArrayList<>();
//        for (BpmOrgRoleEntity role : main.getRoles()) {
//            SysOrg sysOrg = new SysOrg(role.getId(), role.getName(), 32);
//            list.add(sysOrg);
//        }
//        orgRepo.saveAll(list);
        return main.getId();
    }

    public int delete(String[] ids) {
        for (String id : ids) {
            roleDao.deleteByTreid(id);
            treedao.deleteById(id);
        }
        return ids.length;
    }


    public Org calc(String useid, String rolid) {
        String sql = "select t.tier \"tier\",m.ornum \"ornum\" from bpm_org_tree_node t " +
                "inner join bpm_org_role m on m.treid=t.treid where t.memid=? and m.id=?";
        Map<String, Object> map = jdbcHelper.findMap(sql, useid, rolid);
        if (map == null) {
            sql = "select t.tier \"tier\",m.ornum \"ornum\" from bpm_org_tree_node t " +
                    "inner join bpm_org_role m on m.treid=t.treid " +
                    "inner join sys_user u on u.depid=t.memid where u.id=? and m.id=?";
            map = jdbcHelper.findMap(sql, useid, rolid);
            if (map == null) {
                return null;
            }
        }
        String tier = (String) map.get("tier");
        Integer ornum =(Integer) map.get("ornum");
        String[] idArr = tier.split("_");
        String sql3 = "select o.id,o.name,o.type from bpm_org_tree_node t inner join sys_org o on o.id=t.memid where t.id=?";
        Org org = jdbcHelper.getTp().queryForObject(sql3,
                new Object[]{idArr[ornum]}, new BeanPropertyRowMapper<>(Org.class));
        return org;
    }

    private final BpmOrgRoleDao roleDao;

    private final BpmOrgTreeDao treedao;

    private final JdbcHelper jdbcHelper;

}

