package vben.setup.sys.actor;


import org.springframework.data.jpa.repository.JpaRepository;
import vben.common.jpa.entity.SysActor;

public interface SysActorRepo extends JpaRepository<SysActor,String> {

}
