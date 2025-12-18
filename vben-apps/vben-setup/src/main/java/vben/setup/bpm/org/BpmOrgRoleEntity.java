package vben.setup.bpm.org;

import jakarta.persistence.*;
import lombok.Data;


//层级角色
@Data
@Entity
@Table(name = "bpm_org_role")
public class BpmOrgRoleEntity {
    @Id
    @Column(length = 36)
    private String id;//主键

    @Column(length = 64)
    private String name;//角色名称

    private Integer ornum;//排序号

    private String notes;//备注

    private String treid;//角色树ID

    public BpmOrgRoleEntity() {
    }

    public BpmOrgRoleEntity(String id) {
        this.id = id;
    }

    public BpmOrgRoleEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public BpmOrgRoleEntity(String id, String name, Integer ornum) {
        this.id = id;
        this.name = name;
        this.ornum = ornum;
    }
}
