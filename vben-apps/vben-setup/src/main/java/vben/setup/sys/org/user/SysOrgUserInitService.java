package vben.setup.sys.org.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.common.jpa.entity.SysOrg;
import vben.setup.sys.org.root.SysOrgRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户初始化
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SysOrgUserInitService {


    public void init() {
        List<SysOrgUserEntity> list = new ArrayList<>();

        SysOrgUserEntity user = new SysOrgUserEntity();
        user.setId("u1");
        user.setName("管理员");
        user.setUsnam("admin");
        user.setDepid("d1000");
        user.setTier("_d1000_u1_");
        user.setMonum("13812345678");
        user.setEmail("admin@qq.com");
        user.setSex("1");
        user.setNotes("管理员不给修改");
        list.add(user);

        SysOrgUserEntity user2 = new SysOrgUserEntity();
        user2.setId("u2");
        user2.setName("小狐狸");
        user2.setUsnam("vben");
        user2.setDepid("d1000");
        user2.setTier("_d1000_u2_");
        user2.setMonum("13912345678");
        user.setEmail("vben@qq.com");
        user2.setSex("2");
        list.add(user2);

        SysOrgUserEntity user3 = new SysOrgUserEntity();
        user3.setId("u3");
        user3.setName("张三");
        user3.setUsnam("zs");
        user3.setDepid("d1000");
        user3.setTier("_d1000_u3_");
        list.add(user3);

        SysOrgUserEntity user4 = new SysOrgUserEntity();
        user4.setId("u4");
        user4.setName("李四");
        user4.setUsnam("ls");
        user4.setDepid("d1100");
        user4.setTier("_d1000_d1100_u4_");
        list.add(user4);

        SysOrgUserEntity user5 = new SysOrgUserEntity();
        user5.setId("u5");
        user5.setName("王五");
        user5.setUsnam("ww");
        user5.setDepid("d1110");
        user5.setTier("_d1000_d1100_d1110_u5_");
        list.add(user5);

        SysOrgUserEntity user6 = new SysOrgUserEntity();
        user6.setId("u6");
        user6.setName("赵六");
        user6.setUsnam("zl");
        user6.setDepid("d1111");
        user6.setTier("_d1000_d1100_d1110_d1111_u6_");
        list.add(user6);

        SysOrgUserEntity user7 = new SysOrgUserEntity();
        user7.setId("u7");
        user7.setName("孙七");
        user7.setUsnam("sq");
        user7.setDepid("d1111");
        user7.setTier("_d1000_d1100_d1110_d1111_u7_");
        list.add(user7);

        SysOrgUserEntity user8 = new SysOrgUserEntity();
        user8.setId("u8");
        user8.setName("周八");
        user8.setUsnam("zb");
        user8.setDepid("d1111");
        user8.setTier("_d1000_d1100_d1110_d1111_u8_");
        list.add(user8);

        SysOrgUserEntity user9 = new SysOrgUserEntity();
        user9.setId("u9");
        user9.setName("吴九");
        user9.setUsnam("wj");
        user9.setDepid("d1111");
        user9.setTier("_d1000_d1100_d1110_d1111_u9_");
        list.add(user9);

        insert(list);
    }

    private final SysOrgUserRepo userRepo;

    private final SysOrgRepo orgRepo;

    private void insert(List<SysOrgUserEntity> list) {
        List<SysOrg> list2=new ArrayList<>();
        for (SysOrgUserEntity user : list) {
            user.setAvtag(true);
            user.setNinam(user.getName());
            user.setPacod("$2a$10$09f8rxsX4tbj1CZla2MSOuiwHwp5QAPUzbp5whnoZEFK4/xplNwZq");
            user.setOrnum(Integer.parseInt(user.getId().substring(1)));
            SysOrg org = new SysOrg();
            org.setId(user.getId());
            org.setName(user.getName());
            org.setType(2);
            list2.add(org);
        }
        userRepo.saveAll(list);
        orgRepo.saveAll(list2);
    }

}
