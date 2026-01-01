package vben.base.pub;


import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vben.base.sys.rece.SysRece;
import vben.base.sys.rece.SysReceService;
import vben.common.core.domain.R;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.common.jdbc.sqler.Sqler;
import vben.common.satoken.utils.LoginHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/pub/org/rece")
@SaIgnore
@RequiredArgsConstructor
public class PubOrgReceApi {

    @GetMapping
    public R get(Integer type) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        String userId = LoginHelper.getUserId();
        if ((type & 1) != 0) {//部门
            Sqler deptSqler = new Sqler("t.oid as id", "sys_rece");
            deptSqler.addInnerJoin("o.name", "sys_dept o", "o.id=t.oid");
            deptSqler.addEqual("t.useid", userId);
            mapList.addAll(jdbcHelper.findMapList(deptSqler));
        }
        if ((type & 2) != 0) {//用户
            Sqler userSqler = new Sqler("t.oid as id", "sys_rece");
            userSqler.addInnerJoin("u.name", "sys_user u", "u.id=t.oid");
            userSqler.addInnerJoin("d.name as dept", "sys_dept d", "d.id=u.depid");
            userSqler.addEqual("t.useid", userId);
            mapList.addAll(jdbcHelper.findMapList(userSqler));
        }
        if ((type & 4) != 0) {//岗位
            Sqler postSqler = new Sqler("t.oid as id", "sys_rece");
            postSqler.addInnerJoin("p.name", "sys_post p", "p.id=t.oid");
            postSqler.addInnerJoin("d.name as dept", "sys_dept d", "d.id=p.depid");
            postSqler.addEqual("t.useid", userId);
            mapList.addAll(jdbcHelper.findMapList(postSqler));
        }
//        sqler.addDescOrder("t.uptim");
        return R.ok(mapList);
    }

    @PostMapping
    public R<Void> post(@RequestBody List<SysRece> reces) {
        String userId = LoginHelper.getUserId();
        scheduledExecutorService.schedule(() -> {
            if(reces!=null&&reces.size()>0){
                service.update(reces,userId);
            }
        }, 0, TimeUnit.SECONDS);
        return R.ok();
    }

    private final JdbcHelper jdbcHelper;

    private final SysReceService service;

    private final ScheduledExecutorService scheduledExecutorService;
}
