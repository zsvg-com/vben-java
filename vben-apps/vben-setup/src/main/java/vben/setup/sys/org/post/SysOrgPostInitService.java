package vben.setup.sys.org.post;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.common.jpa.entity.SysOrg;
import vben.setup.sys.org.root.SysOrgRepo;

import java.util.List;

/**
 * 岗位初始化
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SysOrgPostInitService {


    public void init() {
        SysOrgPostEntity post1 = new SysOrgPostEntity();
        post1.setId("p2001");
        post1.setName("董事长");
        post1.setUsers(List.of(new SysOrg("u3")));
        post1.setDepid("d1000");
        post1.setTier("_d1000_p2001_");
        insert(post1);

        SysOrgPostEntity post2 = new SysOrgPostEntity();
        post2.setId("p2002");
        post2.setName("北京分公司总经理");
        post2.setUsers(List.of(new SysOrg("u4")));
        post2.setDepid("d1100");
        post2.setTier("_d1000_d1100_p2002_");
        insert(post2);

        SysOrgPostEntity post3 = new SysOrgPostEntity();
        post3.setId("p2003");
        post3.setName("北京分公司销售部长");
        post3.setUsers(List.of(new SysOrg("u5")));
        post3.setDepid("d1110");
        post3.setTier("_d1000_d1100_d1110_p2003_");
        insert(post3);

        SysOrgPostEntity post4 = new SysOrgPostEntity();
        post4.setId("p2004");
        post4.setName("北京分公司销售经理");
        post4.setUsers(List.of(new SysOrg("u6"),new SysOrg("u7")));
        post4.setDepid("d1111");
        post4.setTier("_d1000_d1100_d1110_d1111_p2004_");
        insert(post4);

    }


    private final SysOrgPostRepo postRepo;

    private final SysOrgRepo orgRepo;

    private void insert(SysOrgPostEntity post) {
        post.setAvtag(true);
        post.setOrnum(Integer.parseInt(post.getId().substring(1)));
        postRepo.save(post);
        SysOrg org = new SysOrg();
        org.setId(post.getId());
        org.setName(post.getName());
        org.setType(4);
        orgRepo.save(org);
    }

}
