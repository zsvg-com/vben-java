package vben.setup.root;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.common.jdbc.sqler.JdbcHelper;
import vben.setup.bpm.bus.BpmBusInitService;
import vben.setup.demo.root.DemoInitService;
import vben.setup.sys.config.SysConfigInitService;
import vben.setup.sys.notice.SysNoticeInitService;
import vben.setup.sys.dept.SysDeptInitService;
import vben.setup.sys.group.SysGroupInitService;
import vben.setup.sys.post.SysPostInitService;
import vben.setup.sys.user.SysUserInitService;
import vben.setup.sys.api.SysApiInitService;
import vben.setup.sys.menu.SysMenuInitService;
import vben.setup.sys.role.SysRoleInitService;
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

    private final SysDeptInitService deptInitService;

    private final SysUserInitService userInitService;

    private final SysPostInitService postInitService;

    private final SysGroupInitService groupInitService;

    private final SysMenuInitService menuInitService;

    private final SysApiInitService apiInitService;

    private final SysRoleInitService roleInitService;

    private final SysConfigInitService configInitService;

    private final SysNoticeInitService noticeInitService;

    private final ToolDictInitService dictInitService;

    private final ToolNumInitService numInitService;

    private final BpmBusInitService bpmBusInitService;

    private final DemoInitService demoInitService;


}
