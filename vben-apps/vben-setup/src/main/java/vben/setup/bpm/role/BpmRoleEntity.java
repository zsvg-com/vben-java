package vben.setup.bpm.role;

import jakarta.persistence.*;
import lombok.Data;


//流程角色
@Data
@Entity
@Table(name = "bpm_role")
public class BpmRoleEntity {
    @Id
    @Column(length = 36)
    private String id;//主键

    @Column(length = 64)
    private String name;//角色名称

    private Integer ornum;//排序号

    private String notes;//备注

    private String treid;//角色树ID

    public BpmRoleEntity() {
    }

    public BpmRoleEntity(String id) {
        this.id = id;
    }

    public BpmRoleEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public BpmRoleEntity(String id, String name, Integer ornum) {
        this.id = id;
        this.name = name;
        this.ornum = ornum;
    }
}
