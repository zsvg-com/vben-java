package vben.common.jpa.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
@Schema(description = "组织架构投影")
public class SysOrg {

    @Id
    @Column(length = 36)
    @Schema(description = "主键ID")
    private String id;

    @Column(length = 100)
    @Schema(description = "名称")
    private String name;

    //1为机构,2为部门,4为岗位,8为用户,16为群组,32为角色
    @Schema(description = "类型")
    private Integer type;

    public SysOrg(String id){
        this.id=id;
    }

    public SysOrg(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public SysOrg(String id, String name, Integer type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public SysOrg() {

    }
}
