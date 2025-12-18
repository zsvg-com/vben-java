package vben.setup.root;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.setup.bpm.bus.BpmBusInitService;
import vben.setup.demo.root.DemoInitService;
import vben.setup.sys.config.SysConfigInitService;
import vben.setup.sys.notice.SysNoticeInitService;
import vben.setup.sys.org.dept.SysOrgDeptInitService;
import vben.setup.sys.org.group.SysOrgGroupInitService;
import vben.setup.sys.org.post.SysOrgPostInitService;
import vben.setup.sys.org.user.SysOrgUserInitService;
import vben.setup.sys.perm.api.SysPermApiInitService;
import vben.setup.sys.perm.menu.SysPermMenuInitService;
import vben.setup.sys.perm.role.SysPermRoleInitService;
import vben.setup.tool.dict.ToolDictInitService;
import vben.setup.tool.num.ToolNumInitService;

/**
 * 数据初始化
 */
@Service
@RequiredArgsConstructor
public class DataInitService {

    public void init() {
        String sql="select count(1) from sys_org where id='u1'";
        Integer count = jdbcHelper.getTp().queryForObject(sql, Integer.class);
        if(count!=null&&count!=0){
            return;
        }
        //1为机构,2为部门,4为岗位,8为用户,16为常用群组,32为流程组织角色
        deptInitService.init();
        userInitService.init();
        postInitService.init();
        groupInitService.init();

        menuInitService.initBase();
        menuInitService.initBpm();
        apiInitService.initBase();
        apiInitService.initBpm();
        roleInitService.init();

        configInitService.init();
        noticeInitService.init();

        dictInitService.init();
        numInitService.init();

        //流程相关数据
        bpmBusInitService.init();
        demoInitService.init();
    }

    private final JdbcHelper jdbcHelper;

    private final SysOrgDeptInitService deptInitService;

    private final SysOrgUserInitService userInitService;

    private final SysOrgPostInitService postInitService;

    private final SysOrgGroupInitService groupInitService;

    private final SysPermMenuInitService menuInitService;

    private final SysPermApiInitService apiInitService;

    private final SysPermRoleInitService roleInitService;

    private final SysConfigInitService configInitService;

    private final SysNoticeInitService noticeInitService;

    private final ToolDictInitService dictInitService;

    private final ToolNumInitService numInitService;

    private final BpmBusInitService bpmBusInitService;

    private final DemoInitService demoInitService;


}
