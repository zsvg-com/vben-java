package vben.setup.sys.org;


import org.springframework.data.jpa.repository.JpaRepository;
import vben.common.jpa.entity.SysOrg;

public interface SysOrgRepo extends JpaRepository<SysOrg,String> {

}
