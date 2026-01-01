package vben.setup.sys.role;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vben.common.jpa.entity.SysOrg;
import vben.setup.sys.api.SysApiEntity;
import vben.setup.sys.api.SysApiRepo;
import vben.setup.sys.menu.SysMenuRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门初始化
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
        role.setOrgs(List.of(new SysOrg("u2"),new SysOrg("u3"),new SysOrg("u4"),new SysOrg("u5")));
        role.setMenus(menuRepo.findAll());
        role.setApis(apiRepo.findAll());
        list.add(role);

        SysRoleEntity role2 = new SysRoleEntity();
        role2.setId(2L);
        role2.setName("普通用户");
        role2.setNotes("只包含流程使用权限");
        role2.setOrnum(2);
        role2.setOrgs(List.of(new SysOrg("u6"),new SysOrg("u7"),new SysOrg("u8"),new SysOrg("u9")));
        role2.setMenus(menuRepo.findByNameContaining("流程"));
        List<SysApiEntity> apiList = new ArrayList<>();
        apiList.add(apiRepo.findByPerm("bpmbus:main:query"));
        apiList.add(apiRepo.findByPerm("bpmbus:main:add"));
        apiList.add(apiRepo.findByPerm("bpmbus:main:edit"));
//        apiList.add(apiRepo.findByPerm("bpmbus:main:appv"));
        role2.setApis(apiList);
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
        }
        roleRepo.saveAll(list);
    }

}
