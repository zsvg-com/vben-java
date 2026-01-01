package vben.setup.sys.api;

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
public class SysApiInitService {

    public void initBase() {
        List<SysApiEntity> list = new ArrayList<>();

        SysApiEntity a101001 = new SysApiEntity();
        a101001.setId(101001L);
        a101001.setOrnum(101001);
        a101001.setName("部门查询");
        a101001.setMenid(1010L);
        a101001.setPerm("sys:dept:query");
        list.add(a101001);

        SysApiEntity a101002 = new SysApiEntity();
        a101002.setId(101002L);
        a101002.setOrnum(101002);
        a101002.setName("部门编辑");
        a101002.setMenid(1010L);
        a101002.setPerm("sys:dept:edit");
        list.add(a101002);

        SysApiEntity a101003 = new SysApiEntity();
        a101003.setId(101003L);
        a101003.setOrnum(101003);
        a101003.setName("部门删除");
        a101003.setMenid(1010L);
        a101003.setPerm("sys:dept:delete");
        list.add(a101003);

        SysApiEntity a102001 = new SysApiEntity();
        a102001.setId(102001L);
        a102001.setOrnum(102001);
        a102001.setName("用户查询");
        a102001.setMenid(1020L);
        a102001.setPerm("sys:user:query");
        list.add(a102001);

        SysApiEntity a102002 = new SysApiEntity();
        a102002.setId(102002L);
        a102002.setOrnum(102002);
        a102002.setName("用户编辑");
        a102002.setMenid(1020L);
        a102002.setPerm("sys:user:edit");
        list.add(a102002);

        SysApiEntity a102003 = new SysApiEntity();
        a102003.setId(102003L);
        a102003.setOrnum(102003);
        a102003.setName("用户删除");
        a102003.setMenid(1020L);
        a102003.setPerm("sys:user:delete");
        list.add(a102003);

        SysApiEntity a102004 = new SysApiEntity();
        a102004.setId(102004L);
        a102004.setOrnum(102004);
        a102004.setName("用户启用禁用");
        a102004.setMenid(1020L);
        a102004.setPerm("sys:user:avtag");
        list.add(a102004);

        SysApiEntity a102005 = new SysApiEntity();
        a102005.setId(102005L);
        a102005.setOrnum(102005);
        a102005.setName("用户密码修改");
        a102005.setMenid(1020L);
        a102005.setPerm("sys:user:password");
        list.add(a102005);

        SysApiEntity a103001 = new SysApiEntity();
        a103001.setId(103001L);
        a103001.setOrnum(103001);
        a103001.setName("岗位查询");
        a103001.setMenid(1030L);
        a103001.setPerm("sys:post:query");
        list.add(a103001);

        SysApiEntity a103002 = new SysApiEntity();
        a103002.setId(103002L);
        a103002.setOrnum(103002);
        a103002.setName("岗位编辑");
        a103002.setMenid(1030L);
        a103002.setPerm("sys:post:edit");
        list.add(a103002);

        SysApiEntity a103003 = new SysApiEntity();
        a103003.setId(103003L);
        a103003.setOrnum(103003);
        a103003.setName("岗位删除");
        a103003.setMenid(1030L);
        a103003.setPerm("sys:post:delete");
        list.add(a103003);

        SysApiEntity a104001 = new SysApiEntity();
        a104001.setId(104001L);
        a104001.setOrnum(104001);
        a104001.setName("群组查询");
        a104001.setMenid(1040L);
        a104001.setPerm("sys:group:query");
        list.add(a104001);

        SysApiEntity a104002 = new SysApiEntity();
        a104002.setId(104002L);
        a104002.setOrnum(104002);
        a104002.setName("群组编辑");
        a104002.setMenid(1040L);
        a104002.setPerm("sys:group:edit");
        list.add(a104002);

        SysApiEntity a104003 = new SysApiEntity();
        a104003.setId(104003L);
        a104003.setOrnum(104003);
        a104003.setName("群组删除");
        a104003.setMenid(1040L);
        a104003.setPerm("sys:group:delete");
        list.add(a104003);

        SysApiEntity a104004 = new SysApiEntity();
        a104004.setId(104004L);
        a104004.setOrnum(104004);
        a104004.setName("群组分类查询");
        a104004.setMenid(1040L);
        a104004.setPerm("sys:groupc:query");
        list.add(a104004);

        SysApiEntity a104005 = new SysApiEntity();
        a104005.setId(104005L);
        a104005.setOrnum(104005);
        a104005.setName("群组分类编辑");
        a104005.setMenid(1040L);
        a104005.setPerm("sys:groupc:edit");
        list.add(a104005);

        SysApiEntity a104006 = new SysApiEntity();
        a104006.setId(104006L);
        a104006.setOrnum(104006);
        a104006.setName("群组分类删除");
        a104006.setMenid(1040L);
        a104006.setPerm("sys:groupc:delete");
        list.add(a104006);

        SysApiEntity a105001 = new SysApiEntity();
        a105001.setId(105001L);
        a105001.setOrnum(105001);
        a105001.setName("菜单查询");
        a105001.setMenid(1050L);
        a105001.setPerm("sys:menu:query");
        list.add(a105001);

        SysApiEntity a105002 = new SysApiEntity();
        a105002.setId(105002L);
        a105002.setOrnum(105002);
        a105002.setName("菜单编辑");
        a105002.setMenid(1050L);
        a105002.setPerm("sys:menu:edit");
        list.add(a105002);

        SysApiEntity a105003 = new SysApiEntity();
        a105003.setId(105003L);
        a105003.setOrnum(105003);
        a105003.setName("菜单删除");
        a105003.setMenid(1050L);
        a105003.setPerm("sys:menu:delete");
        list.add(a105003);

        SysApiEntity a106001 = new SysApiEntity();
        a106001.setId(106001L);
        a106001.setOrnum(106001);
        a106001.setName("接口查询");
        a106001.setMenid(1060L);
        a106001.setPerm("sys:api:query");
        list.add(a106001);

        SysApiEntity a106002 = new SysApiEntity();
        a106002.setId(106002L);
        a106002.setOrnum(106002);
        a106002.setName("接口编辑");
        a106002.setMenid(1060L);
        a106002.setPerm("sys:api:edit");
        list.add(a106002);

        SysApiEntity a106003 = new SysApiEntity();
        a106003.setId(106003L);
        a106003.setOrnum(106003);
        a106003.setName("接口删除");
        a106003.setMenid(1060L);
        a106003.setPerm("sys:api:delete");
        list.add(a106003);

        SysApiEntity a107001 = new SysApiEntity();
        a107001.setId(107001L);
        a107001.setOrnum(107001);
        a107001.setName("角色查询");
        a107001.setMenid(1070L);
        a107001.setPerm("sys:role:query");
        list.add(a107001);

        SysApiEntity a107002 = new SysApiEntity();
        a107002.setId(107002L);
        a107002.setOrnum(107002);
        a107002.setName("角色编辑");
        a107002.setMenid(1070L);
        a107002.setPerm("sys:role:edit");
        list.add(a107002);

        SysApiEntity a107003 = new SysApiEntity();
        a107003.setId(107003L);
        a107003.setOrnum(107003);
        a107003.setName("角色删除");
        a107003.setMenid(1070L);
        a107003.setPerm("sys:role:delete");
        list.add(a107003);

        SysApiEntity a108001 = new SysApiEntity();
        a108001.setId(108001L);
        a108001.setOrnum(108001);
        a108001.setName("参数查询");
        a108001.setMenid(1080L);
        a108001.setPerm("sys:config:query");
        list.add(a108001);

        SysApiEntity a108002 = new SysApiEntity();
        a108002.setId(108002L);
        a108002.setOrnum(108002);
        a108002.setName("参数编辑");
        a108002.setMenid(1080L);
        a108002.setPerm("sys:config:edit");
        list.add(a108002);

        SysApiEntity a108003 = new SysApiEntity();
        a108003.setId(108003L);
        a108003.setOrnum(108003);
        a108003.setName("参数删除");
        a108003.setMenid(1080L);
        a108003.setPerm("sys:config:delete");
        list.add(a108003);

        SysApiEntity a109001 = new SysApiEntity();
        a109001.setId(109001L);
        a109001.setOrnum(109001);
        a109001.setName("通知查询");
        a109001.setMenid(1090L);
        a109001.setPerm("sys:notice:query");
        list.add(a109001);

        SysApiEntity a109002 = new SysApiEntity();
        a109002.setId(109002L);
        a109002.setOrnum(109002);
        a109002.setName("通知编辑");
        a109002.setMenid(1090L);
        a109002.setPerm("sys:notice:edit");
        list.add(a109002);

        SysApiEntity a109003 = new SysApiEntity();
        a109003.setId(109003L);
        a109003.setOrnum(109003);
        a109003.setName("通知删除");
        a109003.setMenid(1090L);
        a109003.setPerm("sys:notice:delete");
        list.add(a109003);

        SysApiEntity a201001 = new SysApiEntity();
        a201001.setId(201001L);
        a201001.setOrnum(201001);
        a201001.setName("在线用户查询");
        a201001.setMenid(2010L);
        a201001.setPerm("mon:online:query");
        list.add(a201001);

        SysApiEntity a201002 = new SysApiEntity();
        a201002.setId(201002L);
        a201002.setOrnum(201002);
        a201002.setName("在线用户强退");
        a201002.setMenid(2010L);
        a201002.setPerm("mon:online:delete");
        list.add(a201002);

        SysApiEntity a202001 = new SysApiEntity();
        a202001.setId(202001L);
        a202001.setOrnum(202001);
        a202001.setName("登录日志查询");
        a202001.setMenid(2020L);
        a202001.setPerm("mon:login:query");
        list.add(a202001);

        SysApiEntity a202002 = new SysApiEntity();
        a202002.setId(202002L);
        a202002.setOrnum(202002);
        a202002.setName("登录日志删除");
        a202002.setMenid(2020L);
        a202002.setPerm("mon:login:delete");
        list.add(a202002);

        SysApiEntity a203001 = new SysApiEntity();
        a203001.setId(203001L);
        a203001.setOrnum(203001);
        a203001.setName("操作日志查询");
        a203001.setMenid(2030L);
        a203001.setPerm("mon:oper:query");
        list.add(a203001);

        SysApiEntity a203002 = new SysApiEntity();
        a203002.setId(203002L);
        a203002.setOrnum(203002);
        a203002.setName("操作日志删除");
        a203002.setMenid(2030L);
        a203002.setPerm("mon:oper:delete");
        list.add(a203002);

        SysApiEntity a204001 = new SysApiEntity();
        a204001.setId(204001L);
        a204001.setOrnum(204001);
        a204001.setName("服务器信息查询");
        a204001.setMenid(2040L);
        a204001.setPerm("mon:server:query");
        list.add(a204001);

        SysApiEntity a205001 = new SysApiEntity();
        a205001.setId(205001L);
        a205001.setOrnum(205001);
        a205001.setName("缓存信息查询");
        a205001.setMenid(2050L);
        a205001.setPerm("mon:cache:query");
        list.add(a205001);

        SysApiEntity a206001 = new SysApiEntity();
        a206001.setId(206001L);
        a206001.setOrnum(206001);
        a206001.setName("定时任务查询");
        a206001.setMenid(2060L);
        a206001.setPerm("monjob:main:query");
        list.add(a206001);

        SysApiEntity a206002 = new SysApiEntity();
        a206002.setId(206002L);
        a206002.setOrnum(206002);
        a206002.setName("定时任务修改");
        a206002.setMenid(2060L);
        a206002.setPerm("monjob:main:edit");
        list.add(a206002);

        SysApiEntity a206003 = new SysApiEntity();
        a206003.setId(206003L);
        a206003.setOrnum(206003);
        a206003.setName("定时任务执行");
        a206003.setMenid(2060L);
        a206003.setPerm("monjob:main:run");
        list.add(a206003);

        SysApiEntity a206101 = new SysApiEntity();
        a206101.setId(206101L);
        a206101.setOrnum(206101);
        a206101.setName("定时任务日志查询");
        a206101.setMenid(2061L);
        a206101.setPerm("monjob:log:query");
        list.add(a206101);

        SysApiEntity a206102 = new SysApiEntity();
        a206102.setId(206102L);
        a206102.setOrnum(206102);
        a206102.setName("定时任务日志删除");
        a206102.setMenid(2061L);
        a206102.setPerm("monjob:log:delete");
        list.add(a206102);

        SysApiEntity a301001 = new SysApiEntity();
        a301001.setId(301001L);
        a301001.setOrnum(301001);
        a301001.setName("字典查询");
        a301001.setMenid(3010L);
        a301001.setPerm("tooldict:main:query");
        list.add(a301001);

        SysApiEntity a301002 = new SysApiEntity();
        a301002.setId(301002L);
        a301002.setOrnum(301002);
        a301002.setName("字典修改");
        a301002.setMenid(3010L);
        a301002.setPerm("tooldict:main:edit");
        list.add(a301002);

        SysApiEntity a301003 = new SysApiEntity();
        a301003.setId(301003L);
        a301003.setOrnum(301003);
        a301003.setName("字典删除");
        a301003.setMenid(3010L);
        a301003.setPerm("tooldict:main:delete");
        list.add(a301003);

        SysApiEntity a301004 = new SysApiEntity();
        a301004.setId(301004L);
        a301004.setOrnum(301004);
        a301004.setName("字典数据查询");
        a301004.setMenid(3010L);
        a301004.setPerm("tooldict:data:query");
        list.add(a301004);

        SysApiEntity a301005 = new SysApiEntity();
        a301005.setId(301005L);
        a301005.setOrnum(301005);
        a301005.setName("字典数据编辑");
        a301005.setMenid(3010L);
        a301005.setPerm("tooldict:data:edit");
        list.add(a301005);

        SysApiEntity a301006 = new SysApiEntity();
        a301006.setId(301006L);
        a301006.setOrnum(301006);
        a301006.setName("字典数据删除");
        a301006.setMenid(3010L);
        a301006.setPerm("tooldict:data:delete");
        list.add(a301006);

        SysApiEntity a302001 = new SysApiEntity();
        a302001.setId(302001L);
        a302001.setOrnum(302001);
        a302001.setName("编号查询");
        a302001.setMenid(3020L);
        a302001.setPerm("tool:num:query");
        list.add(a302001);

        SysApiEntity a302002 = new SysApiEntity();
        a302002.setId(302002L);
        a302002.setOrnum(302002);
        a302002.setName("编号编辑");
        a302002.setMenid(3020L);
        a302002.setPerm("tool:num:edit");
        list.add(a302002);

        SysApiEntity a302003 = new SysApiEntity();
        a302003.setId(302003L);
        a302003.setOrnum(302003);
        a302003.setName("编号删除");
        a302003.setMenid(3020L);
        a302003.setPerm("tool:num:delete");
        list.add(a302003);

        insert(list);
    }

    public void initBpm() {
        List<SysApiEntity> list = new ArrayList<>();

        SysApiEntity a603001 = new SysApiEntity();
        a603001.setId(603001L);
        a603001.setOrnum(603001);
        a603001.setName("流程查询");
        a603001.setMenid(6030L);
        a603001.setPerm("bpmbus:main:query");
        list.add(a603001);

        SysApiEntity a603002 = new SysApiEntity();
        a603002.setId(603002L);
        a603002.setOrnum(603002);
        a603002.setName("流程新增");
        a603002.setMenid(6030L);
        a603002.setPerm("bpmbus:main:add");
        list.add(a603002);

        SysApiEntity a603003 = new SysApiEntity();
        a603003.setId(603003L);
        a603003.setOrnum(603003);
        a603003.setName("流程编辑");
        a603003.setMenid(6030L);
        a603003.setPerm("bpmbus:main:edit");
        list.add(a603003);

        insert(list);
    }


    private final SysApiRepo repo;

    private void insert(List<SysApiEntity> list) {
        for (SysApiEntity api : list) {
            api.setAvtag(true);
            api.setUptim(api.getCrtim());
        }
        repo.saveAll(list);
    }

}
