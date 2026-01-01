package vben.setup.sys.dept;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.common.jpa.entity.SysOrg;
import vben.setup.sys.org.SysOrgRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门初始化
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SysDeptInitService {

    public void init() {
        List<SysDeptEntity> list=new ArrayList<>();

        SysDeptEntity dept = new SysDeptEntity();
        dept.setId("d1000");
        dept.setName("XX科技");
        dept.setType(1);
        dept.setTier("_d1000_");
        list.add(dept);

        SysDeptEntity dept1100 = new SysDeptEntity();
        dept1100.setId("d1100");
        dept1100.setName("北京分公司");
        dept1100.setType(2);
        dept1100.setPid("d1000");
        dept1100.setTier("_d1000_d1100_");
        list.add(dept1100);

        SysDeptEntity dept1110 = new SysDeptEntity();
        dept1110.setId("d1110");
        dept1110.setName("北京分公司销售部");
        dept1110.setType(8);
        dept1110.setPid("d1100");
        dept1110.setTier("_d1000_d1100_d1110_");
        list.add(dept1110);

        SysDeptEntity dept1111 = new SysDeptEntity();
        dept1111.setId("d1111");
        dept1111.setName("北京分公司销售部一组");
        dept1111.setType(8);
        dept1111.setPid("d1110");
        dept1111.setTier("_d1000_d1100_d1110_d1111_");
        list.add(dept1111);

        SysDeptEntity dept1112 = new SysDeptEntity();
        dept1112.setId("d1112");
        dept1112.setName("北京分公司销售部二组");
        dept1112.setType(8);
        dept1112.setPid("d1110");
        dept1112.setTier("_d1000_d1100_d1110_d1112_");
        list.add(dept1112);

        SysDeptEntity dept1120 = new SysDeptEntity();
        dept1120.setId("d1120");
        dept1120.setName("北京分公司人事部");
        dept1120.setType(8);
        dept1120.setPid("d1100");
        dept1120.setTier("_d1000_d1100_d1120_");
        list.add(dept1120);

        SysDeptEntity dept1130 = new SysDeptEntity();
        dept1130.setId("d1130");
        dept1130.setName("北京分公司财务部");
        dept1130.setType(8);
        dept1130.setPid("d1100");
        dept1130.setTier("_d1000_d1100_d1130_");
        list.add(dept1130);

        SysDeptEntity dept1140 = new SysDeptEntity();
        dept1140.setId("d1140");
        dept1140.setName("北京分公司综合部");
        dept1140.setType(8);
        dept1140.setPid("d1100");
        dept1140.setTier("_d1000_d1100_d1410_");
        list.add(dept1140);

        SysDeptEntity dept1200 = new SysDeptEntity();
        dept1200.setId("d1200");
        dept1200.setName("上海分公司");
        dept1200.setType(2);
        dept1200.setPid("d1000");
        dept1200.setTier("_d1000_d1200_");
        list.add(dept1200);

        SysDeptEntity dept1210 = new SysDeptEntity();
        dept1210.setId("d1210");
        dept1210.setName("上海分公司销售部");
        dept1210.setType(8);
        dept1210.setPid("d1200");
        dept1210.setTier("_d1000_d1200_d1210_");
        list.add(dept1210);

        SysDeptEntity dept1220 = new SysDeptEntity();
        dept1220.setId("d1220");
        dept1220.setName("上海分公司人事部");
        dept1220.setType(8);
        dept1220.setPid("d1200");
        dept1220.setTier("_d1000_d1200_d1220_");
        list.add(dept1220);

        SysDeptEntity dept1230 = new SysDeptEntity();
        dept1230.setId("d1230");
        dept1230.setName("上海分公司财务部");
        dept1230.setType(8);
        dept1230.setPid("d1200");
        dept1230.setTier("_d1000_d1200_d1230_");
        list.add(dept1230);

        SysDeptEntity dept1300 = new SysDeptEntity();
        dept1300.setId("d1300");
        dept1300.setName("广州分公司");
        dept1300.setType(2);
        dept1300.setPid("d1000");
        dept1300.setTier("_d1000_d1300_");
        list.add(dept1300);

        SysDeptEntity dept1310 = new SysDeptEntity();
        dept1310.setId("d1310");
        dept1310.setName("广州分公司综合部");
        dept1310.setType(8);
        dept1310.setPid("d1300");
        dept1310.setTier("_d1000_d1300_d1310_");
        list.add(dept1310);

        SysDeptEntity dept1320 = new SysDeptEntity();
        dept1320.setId("d1320");
        dept1320.setName("广州分公司销售部");
        dept1320.setType(8);
        dept1320.setPid("d1300");
        dept1320.setTier("_d1000_d1300_d1320_");
        list.add(dept1320);

        SysDeptEntity dept1330 = new SysDeptEntity();
        dept1330.setId("d1330");
        dept1330.setName("广州分公司人事部");
        dept1330.setType(8);
        dept1330.setPid("d1300");
        dept1330.setTier("_d1000_d1300_d1330_");
        list.add(dept1330);

        insert(list);
    }

    private final SysDeptRepo deptRepo;

    private final SysOrgRepo orgRepo;

    private void insert(List<SysDeptEntity> list) {
        List<SysOrg> list2 = new ArrayList<>();
        for (SysDeptEntity dept : list) {
            dept.setAvtag(true);
            dept.setOrnum(Integer.parseInt(dept.getId().substring(1)));
            SysOrg org = new SysOrg();
            org.setId(dept.getId());
            org.setName(dept.getName());
            org.setType(1);
            list2.add(org);
        }
        deptRepo.saveAll(list);
        orgRepo.saveAll(list2);
    }

}
