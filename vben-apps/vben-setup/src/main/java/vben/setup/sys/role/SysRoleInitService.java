package vben.setup.sys.role;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.common.jpa.entity.SysActor;
import vben.setup.sys.api.SysApiEntity;
import vben.setup.sys.api.SysApiRepo;
import vben.setup.sys.menu.SysMenuRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统角色初始化
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SysRoleInitService {

    public void init() {
        List<SysRoleEntity> list = new ArrayList<>();
        SysRoleEntity role = new SysRoleEntity();
        role.setId(1L);
        role.setName("管理员");
        role.setNotes("拥有所有权限");
        role.setOrnum(1);
        role.setLabel("admin");
        role.setScope(32);
        role.setType(1);
        role.setActors(List.of(new SysActor("u2"),new SysActor("u3"),new SysActor("u4"),new SysActor("u5")));
        role.setMenus(menuRepo.findAll());
        role.setApis(apiRepo.findAll());
        list.add(role);

        SysRoleEntity role2 = new SysRoleEntity();
        role2.setId(2L);
        role2.setName("普通用户");
        role2.setNotes("只包含流程使用权限");
        role2.setOrnum(2);
        role2.setLabel("test");
        role2.setScope(1);
        role2.setType(1);
        role2.setActors(List.of(new SysActor("u6"),new SysActor("u7"),new SysActor("u8"),new SysActor("u9")));
        role2.setMenus(menuRepo.findByNameContaining("流程"));
        role2.getMenus().addAll(menuRepo.findByIdGreaterThan(7999L));
        List<SysApiEntity> apiList = new ArrayList<>();
        apiList.add(apiRepo.findByPerm("bpm:bus:query"));
        apiList.add(apiRepo.findByPerm("bpm:bus:add"));
        apiList.add(apiRepo.findByPerm("bpm:bus:edit"));
        role2.setApis(apiList);
        role2.getApis().addAll(apiRepo.findByIdGreaterThan(800000L));
        list.add(role2);
        insert(list);
    }

    private final SysMenuRepo menuRepo;

    private final SysApiRepo apiRepo;

    private final SysRoleRepo roleRepo;

    private void insert(List<SysRoleEntity> list) {
        for (SysRoleEntity role : list) {
            role.setAvtag(true);
            role.setUptim(role.getCrtim());
            role.setCruid("u1");
            role.setUpuid("u1");
        }
        roleRepo.saveAll(list);
    }

}
