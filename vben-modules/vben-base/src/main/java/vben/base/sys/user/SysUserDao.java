package vben.base.sys.user;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import vben.base.mon.login.log.MonLoginLog;
import vben.base.sys.user.bo.IdAvtagBo;
import vben.base.sys.user.vo.IdCatagTierVo;
import vben.common.core.exception.ServiceException;
import vben.common.core.utils.StrUtils;
import vben.common.jdbc.sqler.Isqler;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Usqler;

import java.util.List;


@Component
@RequiredArgsConstructor
public class SysUserDao {

    public void changeAvatg(IdAvtagBo bo) {
        String sql = "update sys_user set avtag = ?  where id = ?";
        jdbcHelper.update(sql, bo.avtag, bo.getId());
    }

    public void resetPassword(String id, String password) {
        String sql = "update sys_user set password = ?  where id = ?";
        jdbcHelper.update(sql, password, id);
    }

    public String findPassword(String id) {
        String sql = "select password from sys_user where id=?";
        return jdbcHelper.getTp().queryForObject(sql, String.class, id);
    }

    public IdCatagTierVo findCatagTierVo(String id) {
        String sql = "select id,catag,tier from sys_user where id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(IdCatagTierVo.class), id);
    }

    public void updateLogin(MonLoginLog log) {
        String sql = "update sys_user set loip = ? , lotim = ? where username = ?";
        jdbcHelper.update(sql, log.getLoip(), log.getLotim(), log.getUsername());
    }


    //获取组织架构可用集
    public String findOrgs(String id) {
        String tierSql = "select tier from sys_user where id = ?";
        String tier = jdbcHelper.findString(tierSql, id);

        StringBuilder orgs = new StringBuilder();
        //1. conds拼接父级id
        if (StrUtils.isNotBlank(tier)) {
            String[] pidArr = tier.split("_");
            for (int i = pidArr.length - 1; i >= 0; i--) {
                if (!"".equals(pidArr[i])) {
                    orgs.append("'").append(pidArr[i]).append("',");
                }
            }
        } else {
            orgs = new StringBuilder("'" + id + "',");
        }
        //2. conds拼接岗位id
        List<String> postList = findPostList(id);
        for (String str : postList) {
            orgs.append("'").append(str).append("',");
        }
        orgs = new StringBuilder(orgs.substring(0, orgs.length() - 1));//优化
        //3. conds拼接群组id
        List<String> groupList = findGroupList(orgs.toString());
        for (String str : groupList) {
            orgs.append(",'").append(str).append("'");
        }
        return orgs.toString();
    }

    //获取组织架构岗位id集合
    private List<String> findPostList(String uid) {
        String sql = "select pid as id from sys_post_org where oid=?";
        return jdbcHelper.findSlist(sql, uid);
    }

    //获取组织架构群组id集合
    private List<String> findGroupList(String orgs) {
        String sql = "select DISTINCT gid as id from sys_group_org where oid in (" + orgs + ")";
        return jdbcHelper.findSlist(sql);
    }

    public SysUser findById(String id) {
        String sql = "select t.*,o.name depna from sys_user t left join sys_org o on o.id=t.depid  where t.id = ?";
        return jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(SysUser.class), id);
    }

    public void insert(SysUser user) {
        Isqler sqler = new Isqler("sys_user");
        sqler.add("id", user.getId());
        sqler.add("name", user.getName());
        sqler.add("username", user.getUsername());
        sqler.add("password", user.getPassword());
        sqler.add("depid", user.getDepid());
        sqler.add("tier", user.getTier());
        sqler.add("notes", user.getNotes());
        sqler.add("ornum", user.getOrnum());
        sqler.add("crtim", user.getCrtim());
        sqler.add("uptim", user.getCrtim());
        sqler.add("cruid", user.getCruid());
        sqler.add("upuid", user.getCruid());
        sqler.add("avtag", user.getAvtag());
        sqler.add("label", user.getLabel());
        sqler.add("monum", user.getMonum());
        sqler.add("email", user.getEmail());
        sqler.add("gender", user.getGender());
        sqler.add("avatar", user.getAvatar());
        sqler.add("type", user.getType());
        sqler.add("jotyp", user.getJotyp());
        sqler.add("arnam", user.getArnam());
        sqler.add("arcod", user.getArcod());
        sqler.add("oftim", user.getOftim());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void update(SysUser user) {
        Usqler sqler = new Usqler("sys_user");
        sqler.addWhere("id=?", user.getId());
        sqler.add("name", user.getName());
        sqler.add("username", user.getUsername());
        sqler.add("depid", user.getDepid());
        sqler.add("tier", user.getTier());
        sqler.add("notes", user.getNotes());
        sqler.add("ornum", user.getOrnum());
        sqler.add("uptim", user.getUptim());
        sqler.add("upuid", user.getUpuid());
        sqler.add("avtag", user.getAvtag());
        sqler.add("label", user.getLabel());
        sqler.add("monum", user.getMonum());
        sqler.add("email", user.getEmail());
        sqler.add("gender", user.getGender());
        sqler.add("type", user.getType());
        sqler.add("jotyp", user.getJotyp());
        sqler.add("arnam", user.getArnam());
        sqler.add("arcod", user.getArcod());
        sqler.add("oftim", user.getOftim());
        jdbcHelper.getTp().update(sqler.getSql(), sqler.getParams());
    }

    public void deleteById(String id) {
        String sql = "delete from sys_user where id=?";
        jdbcHelper.getTp().update(sql, id);
    }

    public boolean existsByMonum(String monum) {
        String sql = "select count(1) from sys_user where monum=?";
        return jdbcHelper.getTp().queryForObject(sql, Integer.class, monum) > 0;
    }

    public SysUser findByUsername(String username) {
        String sql = "select t.*,o.name depna from sys_user t left join sys_org o on o.id=t.depid  where t.username = ?";
        SysUser user;
        try {
            user = jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(SysUser.class), username);
        }catch (Exception e) {
            throw new ServiceException("对不起, 您的账号："+username+" 不存在");
        }
        return user;
    }

    public SysUser findByMonum(String monum) {
        String sql = "select t.*,o.name depna from sys_user t left join sys_org o on o.id=t.depid  where t.monum = ?";
        SysUser user;
        try {
            user = jdbcHelper.getTp().queryForObject(sql, new BeanPropertyRowMapper<>(SysUser.class), monum);
        }catch (Exception e) {
            throw new ServiceException("对不起, 您的账号："+monum+" 不存在");
        }
        return user;
    }

    private final JdbcHelper jdbcHelper;

    public void updateAvatar(String id, String avatar) {
        String sql = "update sys_user set avatar = ?  where id = ?";
        jdbcHelper.update(sql, avatar, id);
    }
}
