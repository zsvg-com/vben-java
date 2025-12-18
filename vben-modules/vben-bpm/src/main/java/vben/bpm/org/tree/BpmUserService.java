package vben.bpm.org.tree;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vben.base.sys.org.root.Org;
import vben.common.core.utils.StrUtils;
import vben.common.jdbc.root.DbType;
import vben.common.jdbc.root.Db;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.satoken.utils.LoginHelper;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
@RequiredArgsConstructor
public class BpmUserService {

    private final JdbcHelper jdbcHelper;

    private final BpmOrgTreeService sysOrgRoleTreeService;

    /**
     * 计算目标节点处理人
     *
     * @param proid
     * @param exuids
     * @return
     */
    public String calcTamen(Long proid, String exuids) {
        String tamen = "";
        if (StrUtils.isNotBlank(exuids) && exuids.contains("$")) {
            if ("$creator".equals(exuids)) {
                String sql = "select cruid from bpm_proc_inst where id=?";
                exuids = jdbcHelper.findString(sql, proid);
            }
        }
        if (StrUtils.isNotBlank(exuids) && !exuids.contains(";")) {
            String tamenSql = "select t.id, t.name,t.type from sys_org t where t.id=?";
            Org sysOrg = jdbcHelper.getTp().queryForObject(tamenSql,new BeanPropertyRowMapper<>(Org.class),exuids);
            if (sysOrg.getType() == 32) {
                String cruid = LoginHelper.getUserId()+"";
                if (proid != null) {
                    String sql = "select cruid from bpm_proc_inst where id=?";
                    cruid = jdbcHelper.findString(sql, proid);
                }
                Org org = sysOrgRoleTreeService.calc(cruid, sysOrg.getId());
                tamen = org.getName();
            } else {
                tamen = sysOrg.getName();
            }
        } else if (StrUtils.isNotBlank(exuids) && exuids.contains(";")) {
            Sqler sqler = new Sqler("sys_org");
            String ids = exuids;
            ids = "'" + ids.replaceAll(";", "','") + "'";
            sqler.addWhere("id in " + "(" + ids + ")");
            if (DbType.MYSQL.equals(Db.Type)) {
                sqler.addOrder("field(id," + ids + ")");
            } else if (DbType.ORACLE.equals(Db.Type)) {
                sqler.addOrder("INSTR('" + ids.replaceAll("'", "") + "',id)");
            } else if (DbType.SQL_SERVER.equals(Db.Type)) {
                sqler.addOrder("CHARINDEX(id,'" + ids.replaceAll("'", "") + "')");
            }

            sqler.addSelect("t.type");
            System.out.println(sqler.getSql());
            List<Org> list = jdbcHelper.getTp().query(sqler.getSql(),
                new BeanPropertyRowMapper<>(Org.class),sqler.getParams());
//            List<SidName> idNameList = jdbcDao.findIdNameList(sqler);
            for (Org sysOrg : list) {
                if (sysOrg.getType() == 32) {
                    String cruid = LoginHelper.getUserId()+"";
                    if (proid != null) {
                        String sql = "select cruid from bpm_proc_inst where id=?";
                        cruid = jdbcHelper.findString(sql, proid);
                    }
                    Org org = sysOrgRoleTreeService.calc(cruid, sysOrg.getId());
                    tamen += org.getName() + ";";
                } else {
                    tamen += sysOrg.getName() + ";";
                }
            }
            tamen = tamen.substring(0, tamen.length() - 1);
        }
        return tamen;
    }


    public List<String> findPostIdList(String userId) {
        String postSql = "select pid as id from sys_org_post_org where oid=?";
        return jdbcHelper.findSlist(postSql, userId);
    }
}
