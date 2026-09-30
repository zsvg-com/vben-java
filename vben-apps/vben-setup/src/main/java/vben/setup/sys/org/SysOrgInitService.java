package vben.setup.sys.org;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.common.jpa.entity.SysActor;
import vben.setup.sys.actor.SysActorRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * 组织初始化
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SysOrgInitService {

    public void init() {
        List<SysOrgEntity> list=new ArrayList<>();

        SysOrgEntity org = new SysOrgEntity();
        org.setId("o1000");
        org.setName("XX科技");
        org.setType(1);
        org.setTier("_o1000_");
        list.add(org);

        SysOrgEntity org1100 = new SysOrgEntity();
        org1100.setId("o1100");
        org1100.setName("北京分公司");
        org1100.setType(2);
        org1100.setPid("o1000");
        org1100.setTier("_o1000_o1100_");
        list.add(org1100);

        SysOrgEntity org1110 = new SysOrgEntity();
        org1110.setId("o1110");
        org1110.setName("北京分公司销售部");
        org1110.setType(8);
        org1110.setPid("o1100");
        org1110.setTier("_o1000_o1100_o1110_");
        list.add(org1110);

        SysOrgEntity org1111 = new SysOrgEntity();
        org1111.setId("o1111");
        org1111.setName("北京分公司销售部一组");
        org1111.setType(8);
        org1111.setPid("o1110");
        org1111.setTier("_o1000_o1100_o1110_o1111_");
        list.add(org1111);

        SysOrgEntity org1112 = new SysOrgEntity();
        org1112.setId("o1112");
        org1112.setName("北京分公司销售部二组");
        org1112.setType(8);
        org1112.setPid("o1110");
        org1112.setTier("_o1000_o1100_o1110_o1112_");
        list.add(org1112);

        SysOrgEntity org1120 = new SysOrgEntity();
        org1120.setId("o1120");
        org1120.setName("北京分公司人事部");
        org1120.setType(8);
        org1120.setPid("o1100");
        org1120.setTier("_o1000_o1100_o1120_");
        list.add(org1120);

        SysOrgEntity org1130 = new SysOrgEntity();
        org1130.setId("o1130");
        org1130.setName("北京分公司财务部");
        org1130.setType(8);
        org1130.setPid("o1100");
        org1130.setTier("_o1000_o1100_o1130_");
        list.add(org1130);

        SysOrgEntity org1140 = new SysOrgEntity();
        org1140.setId("o1140");
        org1140.setName("北京分公司综合部");
        org1140.setType(8);
        org1140.setPid("o1100");
        org1140.setTier("_o1000_o1100_o1410_");
        list.add(org1140);

        SysOrgEntity org1200 = new SysOrgEntity();
        org1200.setId("o1200");
        org1200.setName("上海分公司");
        org1200.setType(2);
        org1200.setPid("o1000");
        org1200.setTier("_o1000_o1200_");
        list.add(org1200);

        SysOrgEntity org1210 = new SysOrgEntity();
        org1210.setId("o1210");
        org1210.setName("上海分公司销售部");
        org1210.setType(8);
        org1210.setPid("o1200");
        org1210.setTier("_o1000_o1200_o1210_");
        list.add(org1210);

        SysOrgEntity org1220 = new SysOrgEntity();
        org1220.setId("o1220");
        org1220.setName("上海分公司人事部");
        org1220.setType(8);
        org1220.setPid("o1200");
        org1220.setTier("_o1000_o1200_o1220_");
        list.add(org1220);

        SysOrgEntity org1230 = new SysOrgEntity();
        org1230.setId("o1230");
        org1230.setName("上海分公司财务部");
        org1230.setType(8);
        org1230.setPid("o1200");
        org1230.setTier("_o1000_o1200_o1230_");
        list.add(org1230);

        SysOrgEntity org1300 = new SysOrgEntity();
        org1300.setId("o1300");
        org1300.setName("广州分公司");
        org1300.setType(2);
        org1300.setPid("o1000");
        org1300.setTier("_o1000_o1300_");
        list.add(org1300);

        SysOrgEntity org1310 = new SysOrgEntity();
        org1310.setId("o1310");
        org1310.setName("广州分公司综合部");
        org1310.setType(8);
        org1310.setPid("o1300");
        org1310.setTier("_o1000_o1300_o1310_");
        list.add(org1310);

        SysOrgEntity org1320 = new SysOrgEntity();
        org1320.setId("o1320");
        org1320.setName("广州分公司销售部");
        org1320.setType(8);
        org1320.setPid("o1300");
        org1320.setTier("_o1000_o1300_o1320_");
        list.add(org1320);

        SysOrgEntity org1330 = new SysOrgEntity();
        org1330.setId("o1330");
        org1330.setName("广州分公司人事部");
        org1330.setType(8);
        org1330.setPid("o1300");
        org1330.setTier("_o1000_o1300_o1330_");
        list.add(org1330);

        insert(list);
    }

    private final SysOrgRepo orgRepo;

    private final SysActorRepo actorRepo;

    private void insert(List<SysOrgEntity> list) {
        List<SysActor> list2 = new ArrayList<>();
        for (SysOrgEntity org : list) {
            org.setAvtag(true);
            org.setOrnum(Integer.parseInt(org.getId().substring(1)));
            SysActor actor = new SysActor();
            actor.setId(org.getId());
            actor.setName(org.getName());
            actor.setType(1);
            list2.add(actor);
        }
        orgRepo.saveAll(list);
        actorRepo.saveAll(list2);
    }

}
