package vben.setup.sys.perm.menu;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门初始化
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SysPermMenuInitService {

    public void initBase() {
        List<SysPermMenuEntity> list=new ArrayList<>();
        SysPermMenuEntity m1000 = new SysPermMenuEntity();
        m1000.setId(1000L);
        m1000.setIcon("tdesign:system-setting");
        m1000.setName("系统管理");
        m1000.setOrnum(1000);
        m1000.setPath("sys");
        m1000.setPid(0L);
        m1000.setType("1");
        m1000.setComp("Layout");
        list.add(m1000);

        SysPermMenuEntity m1010 = new SysPermMenuEntity();
        m1010.setId(1010L);
        m1010.setComp("sys/org/dept/index");
        m1010.setIcon("mingcute:department-line");
        m1010.setName("部门管理");
        m1010.setOrnum(1010);
        m1010.setPath("org/dept");
        m1010.setPid(1000L);
        list.add(m1010);

        SysPermMenuEntity m1020 = new SysPermMenuEntity();
        m1020.setId(1020L);
        m1020.setComp("sys/org/user/index");
        m1020.setIcon("ant-design:user-outlined");
        m1020.setName("用户管理");
        m1020.setOrnum(1020);
        m1020.setPath("org/user");
        m1020.setPid(1000L);
        list.add(m1020);

        SysPermMenuEntity m1021 = new SysPermMenuEntity();
        m1021.setId(1021L);
        m1021.setComp("sys/org/user/tedit");
        m1021.setIcon("mingcute:user-edit-line");
        m1021.setName("用户编辑");
        m1021.setOrnum(1021);
        m1021.setPath("org/user/edit");
        m1021.setCatag(true);
        m1021.setShtag(false);
        m1021.setPid(1000L);
        list.add(m1021);

//        SysPermMenuEntity m1022 = new SysPermMenuEntity();
//        m1022.setId(1022L);
//        m1022.setComp("_core/profile/index");
//        m1022.setIcon("iconamoon:profile");
//        m1022.setName("个人中心");
//        m1022.setOrnum(1022);
//        m1022.setPath("profile");
//        m1022.setPid(1000L);
//        list.add(m1022);

        SysPermMenuEntity m1030 = new SysPermMenuEntity();
        m1030.setId(1030L);
        m1030.setComp("sys/org/post/index");
        m1030.setIcon("icon-park-outline:appointment");
        m1030.setName("岗位管理");
        m1030.setOrnum(1030);
        m1030.setPath("org/post");
        m1030.setPid(1000L);
        list.add(m1030);

        SysPermMenuEntity m1040 = new SysPermMenuEntity();
        m1040.setId(1040L);
        m1040.setComp("sys/org/group/index");
        m1040.setIcon("material-symbols:group-outline-rounded");
        m1040.setName("群组管理");
        m1040.setOrnum(1040);
        m1040.setPath("org/group");
        m1040.setPid(1000L);
        list.add(m1040);

        SysPermMenuEntity m1050 = new SysPermMenuEntity();
        m1050.setId(1050L);
        m1050.setComp("sys/perm/menu/index");
        m1050.setIcon("ri:menu-fold-2-fill");
        m1050.setName("菜单管理");
        m1050.setOrnum(1050);
        m1050.setPath("perm/menu");
        m1050.setPid(1000L);
        list.add(m1050);

        SysPermMenuEntity m1060 = new SysPermMenuEntity();
        m1060.setId(1060L);
        m1060.setComp("sys/perm/api/index");
        m1060.setIcon("ant-design:api-outlined");
        m1060.setName("接口管理");
        m1060.setOrnum(1060);
        m1060.setPath("perm/api");
        m1060.setPid(1000L);
        list.add(m1060);

        SysPermMenuEntity m1070 = new SysPermMenuEntity();
        m1070.setId(1070L);
        m1070.setComp("sys/perm/role/index");
        m1070.setIcon("eos-icons:role-binding-outlined");
        m1070.setName("角色管理");
        m1070.setOrnum(1070);
        m1070.setPath("perm/role");
        m1070.setPid(1000L);
        list.add(m1070);

        SysPermMenuEntity m1071 = new SysPermMenuEntity();
        m1071.setId(1071L);
        m1071.setComp("sys/perm/role/edit");
        m1071.setIcon("oui:app-users-roles");
        m1071.setName("角色编辑");
        m1071.setOrnum(1071);
        m1071.setPath("perm/role/edit");
        m1071.setCatag(true);
        m1071.setShtag(false);
        m1071.setPid(1000L);
        list.add(m1071);

        SysPermMenuEntity m1080 = new SysPermMenuEntity();
        m1080.setId(1080L);
        m1080.setComp("sys/config/index");
        m1080.setIcon("ant-design:setting-outlined");
        m1080.setName("参数设置");
        m1080.setOrnum(1080);
        m1080.setPath("config");
        m1080.setPid(1000L);
        list.add(m1080);

        SysPermMenuEntity m1090 = new SysPermMenuEntity();
        m1090.setId(1090L);
        m1090.setComp("sys/notice/index");
        m1090.setIcon("fe:notice-push");
        m1090.setName("通知公告");
        m1090.setOrnum(1090);
        m1090.setPath("notice");
        m1090.setPid(1000L);
        list.add(m1090);

        SysPermMenuEntity m2000 = new SysPermMenuEntity();
        m2000.setId(2000L);
        m2000.setIcon("eos-icons:monitoring");
        m2000.setName("监控中心");
        m2000.setOrnum(2000);
        m2000.setPath("mon");
        m2000.setPid(0L);
        m2000.setType("1");
        m2000.setComp("Layout");
        list.add(m2000);

        SysPermMenuEntity m2010 = new SysPermMenuEntity();
        m2010.setId(2010L);
        m2010.setComp("mon/online/user/index");
        m2010.setIcon("oui:online");
        m2010.setName("在线用户");
        m2010.setOrnum(2010);
        m2010.setPath("online/user");
        m2010.setPid(2000L);
        list.add(m2010);

        SysPermMenuEntity m2020 = new SysPermMenuEntity();
        m2020.setId(2020L);
        m2020.setComp("mon/login/log/index");
        m2020.setIcon("uiw:login");
        m2020.setName("登录日志");
        m2020.setOrnum(2020);
        m2020.setPath("login/log");
        m2020.setPid(2000L);
        list.add(m2020);

        SysPermMenuEntity m2030 = new SysPermMenuEntity();
        m2030.setId(2030L);
        m2030.setComp("mon/oper/log/index");
        m2030.setIcon("icon-park-outline:reverse-operation-in");
        m2030.setName("操作日志");
        m2030.setOrnum(2030);
        m2030.setPath("oper/log");
        m2030.setPid(2000L);
        list.add(m2030);

        SysPermMenuEntity m2040 = new SysPermMenuEntity();
        m2040.setId(2040L);
        m2040.setComp("mon/server/index");
        m2040.setIcon("mdi:server-outline");
        m2040.setName("服务监控");
        m2040.setOrnum(2040);
        m2040.setPath("server");
        m2040.setPid(2000L);
        list.add(m2040);

        SysPermMenuEntity m2050 = new SysPermMenuEntity();
        m2050.setId(2050L);
        m2050.setComp("mon/cache/index");
        m2050.setIcon("octicon:cache-24");
        m2050.setName("缓存监控");
        m2050.setOrnum(2050);
        m2050.setPath("cache");
        m2050.setPid(2000L);
        list.add(m2050);

        SysPermMenuEntity m2060 = new SysPermMenuEntity();
        m2060.setId(2060L);
        m2060.setComp("mon/job/main/index");
        m2060.setIcon("streamline:task-list");
        m2060.setName("定时任务");
        m2060.setOrnum(2060);
        m2060.setPath("job/main");
        m2060.setPid(2000L);
        list.add(m2060);

        SysPermMenuEntity m2061 = new SysPermMenuEntity();
        m2061.setId(2061L);
        m2061.setComp("mon/job/log/index");
        m2061.setIcon("ix:log");
        m2061.setName("任务日志");
        m2061.setOrnum(2061);
        m2061.setPath("job/log");
        m2061.setPid(2000L);
        m2061.setShtag(false);
        list.add(m2061);

        SysPermMenuEntity m3000 = new SysPermMenuEntity();
        m3000.setId(3000L);
        m3000.setIcon("ant-design:tool-outlined");
        m3000.setName("辅助工具");
        m3000.setOrnum(3000);
        m3000.setPath("tool");
        m3000.setPid(0L);
        m3000.setType("1");
        m3000.setComp("Layout");
        list.add(m3000);

        SysPermMenuEntity m3010 = new SysPermMenuEntity();
        m3010.setId(3010L);
        m3010.setComp("tool/dict/index");
        m3010.setIcon("fluent-mdl2:dictionary");
        m3010.setName("字典工具");
        m3010.setOrnum(3010);
        m3010.setPath("dict");
        m3010.setPid(3000L);
        list.add(m3010);

        SysPermMenuEntity m3020 = new SysPermMenuEntity();
        m3020.setId(3020L);
        m3020.setComp("tool/num/index");
        m3020.setIcon("streamline-sharp:steps-number");
        m3020.setName("编号工具");
        m3020.setOrnum(3020);
        m3020.setPath("num");
        m3020.setPid(3000L);
        list.add(m3020);

        SysPermMenuEntity m3030 = new SysPermMenuEntity();
        m3030.setId(3030L);
        m3030.setComp("tool/oss/main/index");
        m3030.setIcon("mdi:file-outline");
        m3030.setName("文件工具");
        m3030.setOrnum(3030);
        m3030.setPath("oss");
        m3030.setPid(3000L);
        list.add(m3030);

        SysPermMenuEntity m3040 = new SysPermMenuEntity();
        m3040.setId(3040L);
        m3040.setComp("tool/form/index");
        m3040.setIcon("fluent:form-20-regular");
        m3040.setName("在线表单");
        m3040.setOrnum(3040);
        m3040.setPath("form");
        m3040.setPid(3000L);
        list.add(m3040);

        SysPermMenuEntity m3041 = new SysPermMenuEntity();
        m3041.setId(3041L);
        m3041.setComp("tool/form/edit");
        m3041.setIcon("fluent:form-20-regular");
        m3041.setName("在线表单");
        m3041.setOrnum(3041);
        m3041.setPath("form/edit");
        m3041.setCatag(true);
        m3041.setShtag(false);
        m3041.setPid(3000L);
        list.add(m3041);

        SysPermMenuEntity m3050 = new SysPermMenuEntity();
        m3050.setId(3050L);
        m3050.setComp("tool/code/index");
        m3050.setIcon("humbleicons:code");
        m3050.setName("代码生成");
        m3050.setOrnum(3050);
        m3050.setPath("code");
        m3050.setPid(3000L);
        list.add(m3050);

        SysPermMenuEntity m3051 = new SysPermMenuEntity();
        m3051.setId(3051L);
        m3051.setComp("tool/code/edit");
        m3051.setIcon("humbleicons:code");
        m3051.setName("代码生成");
        m3051.setOrnum(3051);
        m3051.setPath("code/edit");
        m3051.setPid(3000L);
        m3051.setShtag(false);
        list.add(m3051);

        insert(list);
    }

    public void initBpm() {
        List<SysPermMenuEntity> list=new ArrayList<>();
        SysPermMenuEntity m6000 = new SysPermMenuEntity();
        m6000.setId(6000L);
        m6000.setIcon("streamline-sharp:text-flow-rows");
        m6000.setName("流程管理");
        m6000.setOrnum(6000);
        m6000.setPath("bpm");
        m6000.setPid(0L);
        m6000.setType("1");
        m6000.setComp("Layout");
        list.add(m6000);

        SysPermMenuEntity m6010 = new SysPermMenuEntity();
        m6010.setId(6010L);
        m6010.setComp("bpm/bus/cate/index");
        m6010.setIcon("tabler:category-plus");
        m6010.setName("流程分类");
        m6010.setOrnum(6010);
        m6010.setPath("bus/cate");
        m6010.setPid(6000L);
        list.add(m6010);

        SysPermMenuEntity m6020 = new SysPermMenuEntity();
        m6020.setId(6020L);
        m6020.setComp("bpm/bus/tmpl/index");
        m6020.setIcon("carbon:prompt-template");
        m6020.setName("流程模板");
        m6020.setOrnum(6020);
        m6020.setPath("bus/tmpl");
        m6020.setPid(6000L);
        list.add(m6020);

        SysPermMenuEntity m6021 = new SysPermMenuEntity();
        m6021.setId(6021L);
        m6021.setComp("bpm/bus/tmpl/edit");
        m6021.setIcon("carbon:prompt-template");
        m6021.setName("流程模板编辑");
        m6021.setOrnum(6021);
        m6021.setPath("bus/tmpl/edit");
        m6021.setCatag(true);
        m6021.setShtag(false);
        m6021.setPid(6000L);
        list.add(m6021);

        SysPermMenuEntity m6030 = new SysPermMenuEntity();
        m6030.setId(6030L);
        m6030.setComp("bpm/bus/main/index");
        m6030.setIcon("ri:instance-line");
        m6030.setName("流程清单");
        m6030.setOrnum(6030);
        m6030.setPath("bus/main");
        m6030.setPid(6000L);
        list.add(m6030);

        SysPermMenuEntity m6031 = new SysPermMenuEntity();
        m6031.setId(6031L);
        m6031.setComp("bpm/bus/main/edit");
        m6031.setIcon("ri:instance-line");
        m6031.setName("流程编辑");
        m6031.setOrnum(6031);
        m6031.setPath("bus/main/edit");
        m6031.setCatag(true);
        m6031.setShtag(false);
        m6031.setPid(6000L);
        list.add(m6031);

        SysPermMenuEntity m6032 = new SysPermMenuEntity();
        m6032.setId(6032L);
        m6032.setComp("bpm/bus/main/view");
        m6032.setIcon("ri:instance-line");
        m6032.setName("流程查看");
        m6032.setOrnum(6032);
        m6032.setPath("bus/main/view");
        m6032.setCatag(true);
        m6032.setShtag(false);
        m6032.setPid(6000L);
        list.add(m6032);

        SysPermMenuEntity m6040 = new SysPermMenuEntity();
        m6040.setId(6040L);
        m6040.setComp("bpm/todo/index");
        m6040.setIcon("ri:todo-line");
        m6040.setName("流程待办");
        m6040.setOrnum(6040);
        m6040.setPath("todo");
        m6040.setPid(6000L);
        list.add(m6040);

        SysPermMenuEntity m6050 = new SysPermMenuEntity();
        m6050.setId(6050L);
        m6050.setComp("bpm/org/tree/index");
        m6050.setIcon("mdi:workflow-outline");
        m6050.setName("流程组织");
        m6050.setOrnum(6050);
        m6050.setPath("org/tree");
        m6050.setPid(6000L);
        list.add(m6050);

        SysPermMenuEntity m6051 = new SysPermMenuEntity();
        m6051.setId(6051L);
        m6051.setComp("bpm/org/node/index");
        m6051.setIcon("mdi:workflow-outline");
        m6051.setName("流程组织节点");
        m6051.setOrnum(6051);
        m6051.setPath("org/node");
        m6051.setShtag(false);
        m6051.setPid(6000L);
        list.add(m6051);

        insert(list);
    }

    private final SysPermMenuRepo repo;


    private void insert(List<SysPermMenuEntity> list) {
        for (SysPermMenuEntity main : list) {
            main.setAvtag(true);
            if(main.getCatag()==null){
                main.setCatag(false);
            }
            if(main.getType()==null){
                main.setType("2");
            }
            if(main.getShtag()==null){
                main.setShtag(true);
            }
            if(main.getOutag()==null){
                main.setOutag(false);
            }
            main.setUptim(main.getCrtim());
        }
        repo.saveAll(list);
    }

}
