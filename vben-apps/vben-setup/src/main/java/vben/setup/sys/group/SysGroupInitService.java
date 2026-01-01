package vben.setup.sys.group;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.common.jpa.entity.SysOrg;
import vben.setup.sys.org.SysOrgRepo;

import java.util.List;

/**
 * 群组初始化
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SysGroupInitService {

    public void init() {
        SysGroupEntity group= new SysGroupEntity();
        group.setId("g3001");
        group.setName("北京分公司管理组");
        group.setMembers(List.of(new SysOrg("u4"),new SysOrg("u5"),new SysOrg("p2004"),new SysOrg("d1140")));
        insert(group);

        SysGroupEntity group2= new SysGroupEntity();
        group2.setId("g3002");
        group2.setName("北京分公司销售员");
        group2.setMembers(List.of(new SysOrg("u8"),new SysOrg("u9")));
        insert(group2);
    }

    private final SysGroupRepo groupRepo;

    private final SysOrgRepo orgRepo;

    private void insert(SysGroupEntity group) {
        group.setAvtag(true);
        group.setOrnum(Integer.parseInt(group.getId().substring(1)));
        groupRepo.save(group);
        SysOrg org = new SysOrg();
        org.setId(group.getId());
        org.setName(group.getName());
        org.setType(8);
        orgRepo.save(org);
    }

}
