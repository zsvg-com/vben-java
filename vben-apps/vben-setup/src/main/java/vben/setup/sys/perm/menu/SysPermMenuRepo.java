package vben.setup.sys.perm.menu;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SysPermMenuRepo extends JpaRepository<SysPermMenuEntity,String> {

    List<SysPermMenuEntity> findByNameContaining(String path);

}
