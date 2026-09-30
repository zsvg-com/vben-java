package vben.base.pub.rece;


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
@RequestMapping("/pub/rece")
@SaIgnore
@RequiredArgsConstructor
public class PubReceApi {

    @GetMapping
    public R get(Integer type) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        String userId = LoginHelper.getUserId();
        if ((type & 1) != 0) {//组织
            Sqler orgSqler = new Sqler("t.aid as id", "sys_rece");
            orgSqler.addInnerJoin("o.name", "sys_org o", "o.id=t.aid");
            orgSqler.addEqual("t.useid", userId);
            mapList.addAll(jdbcHelper.findMapList(orgSqler));
        }
        if ((type & 2) != 0) {//用户
            Sqler userSqler = new Sqler("t.aid as id", "sys_rece");
            userSqler.addInnerJoin("u.name", "sys_user u", "u.id=t.aid");
            userSqler.addInnerJoin("o.name as org", "sys_org o", "o.id=u.orgid");
            userSqler.addEqual("t.useid", userId);
            mapList.addAll(jdbcHelper.findMapList(userSqler));
        }
        if ((type & 4) != 0) {//岗位
            Sqler postSqler = new Sqler("t.aid as id", "sys_rece");
            postSqler.addInnerJoin("p.name", "sys_post p", "p.id=t.aid");
            postSqler.addInnerJoin("o.name as org", "sys_org o", "o.id=p.orgid");
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
