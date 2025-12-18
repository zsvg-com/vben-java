package vben.setup.sys.perm.api;


import org.springframework.data.jpa.repository.JpaRepository;

public interface SysPermApiRepo extends JpaRepository<SysPermApiEntity,String> {

    SysPermApiEntity findByPerm(String perm);

}
