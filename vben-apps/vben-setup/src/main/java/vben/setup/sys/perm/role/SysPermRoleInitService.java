package vben.setup.sys.perm.role;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.common.jpa.entity.SysOrg;
import vben.setup.sys.perm.api.SysPermApiEntity;
import vben.setup.sys.perm.api.SysPermApiRepo;
import vben.setup.sys.perm.menu.SysPermMenuRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门初始化
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SysPermRoleInitService {

    public void init() {
        List<SysPermRoleEntity> list = new ArrayList<>();
        SysPermRoleEntity role = new SysPermRoleEntity();
        role.setId(1L);
        role.setName("管理员");
        role.setNotes("拥有所有权限");
        role.setOrnum(1);
        role.setOrgs(List.of(new SysOrg("u2"),new SysOrg("u3"),new SysOrg("u4"),new SysOrg("u5")));
        role.setMenus(menuRepo.findAll());
        role.setApis(apiRepo.findAll());
        list.add(role);

        SysPermRoleEntity role2 = new SysPermRoleEntity();
        role2.setId(2L);
        role2.setName("普通用户");
        role2.setNotes("只包含流程使用权限");
        role2.setOrnum(2);
        role2.setOrgs(List.of(new SysOrg("u6"),new SysOrg("u7"),new SysOrg("u8"),new SysOrg("u9")));
        role2.setMenus(menuRepo.findByNameContaining("流程"));
        List<SysPermApiEntity> apiList = new ArrayList<>();
        apiList.add(apiRepo.findByPerm("bpmbus:main:query"));
        apiList.add(apiRepo.findByPerm("bpmbus:main:add"));
        apiList.add(apiRepo.findByPerm("bpmbus:main:edit"));
//        apiList.add(apiRepo.findByPerm("bpmbus:main:appv"));
        role2.setApis(apiList);
        list.add(role2);
        insert(list);
    }

    private final SysPermMenuRepo menuRepo;

    private final SysPermApiRepo apiRepo;

    private final SysPermRoleRepo roleRepo;

    private void insert(List<SysPermRoleEntity> list) {
        for (SysPermRoleEntity role : list) {
            role.setAvtag(true);
            role.setUptim(role.getCrtim());
        }
        roleRepo.saveAll(list);
    }

}
