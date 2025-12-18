package vben.setup.tool.dict;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 部门初始化
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ToolDictInitService {

    public void init() {
        List<ToolDictMainEntity> list1 = new ArrayList<>();
        List<ToolDictDataEntity> list2 = new ArrayList<>();


        ToolDictMainEntity m1 = new ToolDictMainEntity();
        m1.setName("操作类型");
        m1.setCode("sys_oper_type");
        m1.setId(1L);
        m1.setOrnum(1);
        list1.add(m1);

        ToolDictDataEntity d101 = new ToolDictDataEntity();
        d101.setDalab("新增");
        d101.setDaval("1");
        d101.setId(101L);
        d101.setOrnum(1);
        d101.setDicid(1L);
        d101.setShsty("primary");
        list2.add(d101);

        ToolDictDataEntity d102 = new ToolDictDataEntity();
        d102.setDalab("修改");
        d102.setDaval("2");
        d102.setId(102L);
        d102.setOrnum(2);
        d102.setDicid(1L);
        d102.setShsty("primary");
        list2.add(d102);

        ToolDictDataEntity d103 = new ToolDictDataEntity();
        d103.setDalab("删除");
        d103.setDaval("3");
        d103.setId(103L);
        d103.setOrnum(3);
        d103.setDicid(1L);
        d103.setShsty("danger");
        list2.add(d103);

        ToolDictDataEntity d104 = new ToolDictDataEntity();
        d104.setDalab("授权");
        d104.setDaval("4");
        d104.setId(104L);
        d104.setOrnum(4);
        d104.setDicid(1L);
        d104.setShsty("primary");
        list2.add(d104);

        ToolDictDataEntity d105 = new ToolDictDataEntity();
        d105.setDalab("导出");
        d105.setDaval("5");
        d105.setId(105L);
        d105.setOrnum(5);
        d105.setDicid(1L);
        d105.setShsty("warning");
        list2.add(d105);

        ToolDictDataEntity d106 = new ToolDictDataEntity();
        d106.setDalab("导入");
        d106.setDaval("6");
        d106.setId(106L);
        d106.setOrnum(6);
        d106.setDicid(1L);
        d106.setShsty("warning");
        list2.add(d106);

        ToolDictDataEntity d107 = new ToolDictDataEntity();
        d107.setDalab("强退");
        d107.setDaval("7");
        d107.setId(107L);
        d107.setOrnum(7);
        d107.setDicid(1L);
        d107.setShsty("danger");
        list2.add(d107);

        ToolDictDataEntity d108 = new ToolDictDataEntity();
        d108.setDalab("生成代码");
        d108.setDaval("8");
        d108.setId(108L);
        d108.setOrnum(8);
        d108.setDicid(1L);
        d108.setShsty("warning");
        list2.add(d108);

        ToolDictDataEntity d109 = new ToolDictDataEntity();
        d109.setDalab("清空数据");
        d109.setDaval("9");
        d109.setId(109L);
        d109.setOrnum(9);
        d109.setDicid(1L);
        d109.setShsty("danger");
        list2.add(d109);

        ToolDictDataEntity d199 = new ToolDictDataEntity();
        d199.setDalab("其他操作");
        d199.setDaval("0");
        d199.setId(199L);
        d199.setOrnum(99);
        d199.setDicid(1L);
        d199.setShsty("warning");
        list2.add(d199);

        ToolDictMainEntity m2 = new ToolDictMainEntity();
        m2.setName("授权类型");
        m2.setCode("sys_grant_type");
        m2.setId(2L);
        m2.setOrnum(2);
        list1.add(m2);

        ToolDictDataEntity d201 = new ToolDictDataEntity();
        d201.setDalab("密码认证");
        d201.setDaval("password");
        d201.setId(201L);
        d201.setOrnum(1);
        d201.setDicid(2L);
        d201.setShsty("default");
        list2.add(d201);

        ToolDictDataEntity d202 = new ToolDictDataEntity();
        d202.setDalab("短信认证");
        d202.setDaval("sms");
        d202.setId(202L);
        d202.setOrnum(2);
        d202.setDicid(2L);
        d202.setShsty("default");
        list2.add(d202);

        ToolDictDataEntity d203 = new ToolDictDataEntity();
        d203.setDalab("邮箱认证");
        d203.setDaval("email");
        d203.setId(203L);
        d203.setOrnum(3);
        d203.setDicid(2L);
        d203.setShsty("default");
        list2.add(d203);

        ToolDictDataEntity d204 = new ToolDictDataEntity();
        d204.setDalab("小程序认证");
        d204.setDaval("xcx");
        d204.setId(204L);
        d204.setOrnum(4);
        d204.setDicid(2L);
        d204.setShsty("default");
        list2.add(d204);

        ToolDictDataEntity d205 = new ToolDictDataEntity();
        d205.setDalab("三方登录认证");
        d205.setDaval("social");
        d205.setId(205L);
        d205.setOrnum(5);
        d205.setDicid(2L);
        d205.setShsty("default");
        list2.add(d205);

        ToolDictMainEntity m3 = new ToolDictMainEntity();
        m3.setName("设备类型");
        m3.setCode("sys_device_type");
        m3.setId(3L);
        m3.setOrnum(3);
        list1.add(m3);

        ToolDictDataEntity d301 = new ToolDictDataEntity();
        d301.setDalab("PC");
        d301.setDaval("PC");
        d301.setId(301L);
        d301.setOrnum(1);
        d301.setDicid(3L);
        d301.setShsty("default");
        list2.add(d301);

        ToolDictDataEntity d302 = new ToolDictDataEntity();
        d302.setDalab("安卓");
        d302.setDaval("android");
        d302.setId(302L);
        d302.setOrnum(2);
        d302.setDicid(3L);
        d302.setShsty("default");
        list2.add(d302);

        ToolDictDataEntity d303 = new ToolDictDataEntity();
        d303.setDalab("苹果");
        d303.setDaval("IOS");
        d303.setId(303L);
        d303.setOrnum(3);
        d303.setDicid(3L);
        d303.setShsty("default");
        list2.add(d303);

        ToolDictDataEntity d304 = new ToolDictDataEntity();
        d304.setDalab("小程序");
        d304.setDaval("XCX");
        d304.setId(304L);
        d304.setOrnum(4);
        d304.setDicid(3L);
        d304.setShsty("default");
        list2.add(d304);

        insert(list1,list2);

    }


    private final ToolDictDataRepo dataRepo;

    private final ToolDictMainRepo mainRepo;

    private void insert(List<ToolDictMainEntity> list1,List<ToolDictDataEntity> list2) {
        for (ToolDictDataEntity data : list2) {
            data.setCrtim(new Date());
            data.setUptim(data.getCrtim());
            data.setAvtag(true);
        }
        mainRepo.saveAll(list1);
        dataRepo.saveAll(list2);
    }

}
