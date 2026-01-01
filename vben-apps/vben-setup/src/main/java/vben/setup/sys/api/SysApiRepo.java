package vben.setup.sys.api;


import org.springframework.data.jpa.repository.JpaRepository;

public interface SysApiRepo extends JpaRepository<SysApiEntity,String> {

    SysApiEntity findByPerm(String perm);

}
