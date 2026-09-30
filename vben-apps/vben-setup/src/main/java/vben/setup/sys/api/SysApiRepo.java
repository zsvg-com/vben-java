package vben.setup.sys.api;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SysApiRepo extends JpaRepository<SysApiEntity,String> {

    SysApiEntity findByPerm(String perm);


    List<SysApiEntity> findByIdGreaterThan(Long id);

}
