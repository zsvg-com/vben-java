package vben.setup.sys.org.dept;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.common.jpa.entity.SysOrg;
import vben.setup.sys.org.root.SysOrgRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门初始化
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SysOrgDeptInitService {

    public void init() {
        List<SysOrgDeptEntity> list=new ArrayList<>();

        SysOrgDeptEntity dept = new SysOrgDeptEntity();
        dept.setId("d1000");
        dept.setName("XX科技");
        dept.setType(1);
        dept.setTier("_d1000_");
        list.add(dept);

        SysOrgDeptEntity dept1100 = new SysOrgDeptEntity();
        dept1100.setId("d1100");
        dept1100.setName("北京分公司");
        dept1100.setType(2);
        dept1100.setPid("d1000");
        dept1100.setTier("_d1000_d1100_");
        list.add(dept1100);

        SysOrgDeptEntity dept1110 = new SysOrgDeptEntity();
        dept1110.setId("d1110");
        dept1110.setName("北京分公司销售部");
        dept1110.setType(8);
        dept1110.setPid("d1100");
        dept1110.setTier("_d1000_d1100_d1110_");
        list.add(dept1110);

        SysOrgDeptEntity dept1111 = new SysOrgDeptEntity();
        dept1111.setId("d1111");
        dept1111.setName("北京分公司销售部一组");
        dept1111.setType(8);
        dept1111.setPid("d1110");
        dept1111.setTier("_d1000_d1100_d1110_d1111_");
        list.add(dept1111);

        SysOrgDeptEntity dept1112 = new SysOrgDeptEntity();
        dept1112.setId("d1112");
        dept1112.setName("北京分公司销售部二组");
        dept1112.setType(8);
        dept1112.setPid("d1110");
        dept1112.setTier("_d1000_d1100_d1110_d1112_");
        list.add(dept1112);

        SysOrgDeptEntity dept1120 = new SysOrgDeptEntity();
        dept1120.setId("d1120");
        dept1120.setName("北京分公司人事部");
        dept1120.setType(8);
        dept1120.setPid("d1100");
        dept1120.setTier("_d1000_d1100_d1120_");
        list.add(dept1120);

        SysOrgDeptEntity dept1130 = new SysOrgDeptEntity();
        dept1130.setId("d1130");
        dept1130.setName("北京分公司财务部");
        dept1130.setType(8);
        dept1130.setPid("d1100");
        dept1130.setTier("_d1000_d1100_d1130_");
        list.add(dept1130);

        SysOrgDeptEntity dept1140 = new SysOrgDeptEntity();
        dept1140.setId("d1140");
        dept1140.setName("北京分公司综合部");
        dept1140.setType(8);
        dept1140.setPid("d1100");
        dept1140.setTier("_d1000_d1100_d1410_");
        list.add(dept1140);

        SysOrgDeptEntity dept1200 = new SysOrgDeptEntity();
        dept1200.setId("d1200");
        dept1200.setName("上海分公司");
        dept1200.setType(2);
        dept1200.setPid("d1000");
        dept1200.setTier("_d1000_d1200_");
        list.add(dept1200);

        SysOrgDeptEntity dept1210 = new SysOrgDeptEntity();
        dept1210.setId("d1210");
        dept1210.setName("上海分公司销售部");
        dept1210.setType(8);
        dept1210.setPid("d1200");
        dept1210.setTier("_d1000_d1200_d1210_");
        list.add(dept1210);

        SysOrgDeptEntity dept1220 = new SysOrgDeptEntity();
        dept1220.setId("d1220");
        dept1220.setName("上海分公司人事部");
        dept1220.setType(8);
        dept1220.setPid("d1200");
        dept1220.setTier("_d1000_d1200_d1220_");
        list.add(dept1220);

        SysOrgDeptEntity dept1230 = new SysOrgDeptEntity();
        dept1230.setId("d1230");
        dept1230.setName("上海分公司财务部");
        dept1230.setType(8);
        dept1230.setPid("d1200");
        dept1230.setTier("_d1000_d1200_d1230_");
        list.add(dept1230);

        SysOrgDeptEntity dept1300 = new SysOrgDeptEntity();
        dept1300.setId("d1300");
        dept1300.setName("广州分公司");
        dept1300.setType(2);
        dept1300.setPid("d1000");
        dept1300.setTier("_d1000_d1300_");
        list.add(dept1300);

        SysOrgDeptEntity dept1310 = new SysOrgDeptEntity();
        dept1310.setId("d1310");
        dept1310.setName("广州分公司综合部");
        dept1310.setType(8);
        dept1310.setPid("d1300");
        dept1310.setTier("_d1000_d1300_d1310_");
        list.add(dept1310);

        SysOrgDeptEntity dept1320 = new SysOrgDeptEntity();
        dept1320.setId("d1320");
        dept1320.setName("广州分公司销售部");
        dept1320.setType(8);
        dept1320.setPid("d1300");
        dept1320.setTier("_d1000_d1300_d1320_");
        list.add(dept1320);

        SysOrgDeptEntity dept1330 = new SysOrgDeptEntity();
        dept1330.setId("d1330");
        dept1330.setName("广州分公司人事部");
        dept1330.setType(8);
        dept1330.setPid("d1300");
        dept1330.setTier("_d1000_d1300_d1330_");
        list.add(dept1330);

        insert(list);
    }

    private final SysOrgDeptRepo deptRepo;

    private final SysOrgRepo orgRepo;

    private void insert(List<SysOrgDeptEntity> list) {
        List<SysOrg> list2 = new ArrayList<>();
        for (SysOrgDeptEntity dept : list) {
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
