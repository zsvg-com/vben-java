package vben.setup.sys.menu;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SysMenuRepo extends JpaRepository<SysMenuEntity,String> {

    List<SysMenuEntity> findByNameContaining(String path);

}
