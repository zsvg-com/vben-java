package vben.common.jpa.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
@Schema(description = "系统参与者")
public class SysActor {

    @Id
    @Column(length = 36)
    @Schema(description = "主键ID")
    private String id;

    @Column(length = 100)
    @Schema(description = "名称")
    private String name;

    @Schema(description = "类型")
    private Integer type;

    public SysActor(String id){
        this.id=id;
    }

    public SysActor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public SysActor(String id, String name, Integer type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public SysActor() {

    }
}
