package vben.base.pub.actor;

import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vben.common.core.domain.R;
import vben.common.core.utils.StrUtils;
import vben.common.jdbc.root.DbType;
import vben.common.jdbc.root.Db;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;

import java.util.ArrayList;
import java.util.List;


/**
 * 系统参与者（成员）查询
 */
@RestController
@RequestMapping("/pub/actor")
@SaIgnore
@RequiredArgsConstructor
public class PubActorApi {


    //根据组织ID，查询下级所有的组织,岗位,用户
    @GetMapping()
    public R get(String orgid, Integer type, String name) {
        List<IdNameAvatarVo> mapList = new ArrayList<>();
        if (StrUtils.isBlank(name) && StrUtils.isBlank(orgid)) {
            return R.ok(mapList);
        }
        if ((type & 1) != 0) {//部门
            Sqler orgSqler = new Sqler("id,name","sys_org");
            if (StrUtils.isNotBlank(name)) {
                orgSqler.addLike("t.name", name);
            } else {
                orgSqler.addEqual("t.pid", orgid);
            }
            List<IdNameAvatarVo> list = jdbcHelper.getTp().query(orgSqler.getSql(),
                new BeanPropertyRowMapper<>(IdNameAvatarVo.class),orgSqler.getParams());
            mapList.addAll(list);
        }
        if ((type & 2) != 0) {//用户
            Sqler userSqler = new Sqler("id,name,avatar","sys_user");
            if (StrUtils.isNotBlank(name)) {
                userSqler.addLike("t.name", name);
            } else {
                userSqler.addEqual("t.orgid", orgid);
            }
            List<IdNameAvatarVo> list = jdbcHelper.getTp().query(userSqler.getSql(),
                new BeanPropertyRowMapper<>(IdNameAvatarVo.class),userSqler.getParams());
            mapList.addAll(list);
        }
        if ((type & 4) != 0) {//岗位
            Sqler postSqler = new Sqler("id,name","sys_post");
            if (StrUtils.isNotBlank(name)) {
                postSqler.addLike("t.name", name);
            } else {
                postSqler.addEqual("t.orgid", orgid);
            }
            List<IdNameAvatarVo> list = jdbcHelper.getTp().query(postSqler.getSql(),
                new BeanPropertyRowMapper<>(IdNameAvatarVo.class),postSqler.getParams());
            mapList.addAll(list);
        }
        return R.ok(mapList);
    }

    //根据参与者ids获取SysActor对象数组
    @GetMapping("list")
    public R getActorList(String ids) {
        Sqler sqler = new Sqler("sys_actor");
        if (ids.contains(";")) {
//            ids = "('" + ids.replaceAll(";", "','") + "')";
//            sqler.addWhere("id in " + ids);
            ids = "'" + ids.replaceAll(";", "','") + "'";
            sqler.addWhere("id in " + "(" + ids + ")");
            if (DbType.MYSQL.equals(Db.Type)) {
                sqler.addOrder("field(id," + ids + ")");
            } else if (DbType.ORACLE.equals(Db.Type)) {
                sqler.addOrder("INSTR('" + ids.replaceAll("'", "") + "',id)");
            } else if (DbType.SQL_SERVER.equals(Db.Type)) {
                sqler.addOrder("CHARINDEX(id,'" + ids.replaceAll("'", "") + "')");
            }
        } else {
            sqler.addEqual("id", ids);
        }
        return R.ok(jdbcHelper.findSidNameList(sqler));
    }

    private final JdbcHelper jdbcHelper;

}
