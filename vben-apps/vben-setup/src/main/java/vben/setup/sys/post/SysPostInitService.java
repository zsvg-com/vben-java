package vben.setup.sys.post;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.common.jpa.entity.SysActor;
import vben.setup.sys.actor.SysActorRepo;

import java.util.List;

/**
 * 岗位初始化
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SysPostInitService {


    public void init() {
        SysPostEntity post1 = new SysPostEntity();
        post1.setId("p2001");
        post1.setName("董事长");
        post1.setUsers(List.of(new SysActor("u3")));
        post1.setOrgid("o1000");
        post1.setTier("_o1000_p2001_");
        insert(post1);

        SysPostEntity post2 = new SysPostEntity();
        post2.setId("p2002");
        post2.setName("北京分公司总经理");
        post2.setUsers(List.of(new SysActor("u4")));
        post2.setOrgid("o1100");
        post2.setTier("_o1000_o1100_p2002_");
        insert(post2);

        SysPostEntity post3 = new SysPostEntity();
        post3.setId("p2003");
        post3.setName("北京分公司销售部长");
        post3.setUsers(List.of(new SysActor("u5")));
        post3.setOrgid("o1110");
        post3.setTier("_o1000_o1100_o1110_p2003_");
        insert(post3);

        SysPostEntity post4 = new SysPostEntity();
        post4.setId("p2004");
        post4.setName("北京分公司销售经理");
        post4.setUsers(List.of(new SysActor("u6"),new SysActor("u7")));
        post4.setOrgid("o1111");
        post4.setTier("_o1000_o1100_o1110_o1111_p2004_");
        insert(post4);

    }


    private final SysPostRepo postRepo;

    private final SysActorRepo actorRepo;

    private void insert(SysPostEntity post) {
        post.setAvtag(true);
        post.setOrnum(Integer.parseInt(post.getId().substring(1)));
        postRepo.save(post);
        SysActor org = new SysActor();
        org.setId(post.getId());
        org.setName(post.getName());
        org.setType(4);
        actorRepo.save(org);
    }

}
