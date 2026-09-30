package vben.jpa.demo.perm.way1;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import vben.common.jpa.entity.BaseMainEntity;
import vben.common.jpa.entity.SysActor;

/**
 * 数据权限案例1
 */
@Entity
@Getter
@Setter
@Schema(description = "数据权限案例1")
public class DemoPerm1 extends BaseMainEntity {

//    /**
//     * 归属用户ID
//     */
//    @Schema(description = "归属用户ID")
//    private String bluid;

    /**
     * 归属用户ID
     */
    @ManyToOne
    @JoinColumn(name = "bluid")
    @Schema(description = "归属用户")
    private SysActor blman;

//    /**
//     * 归属组织ID
//     */
//    @Schema(description = "归属组织ID")
//    private String bloid;

    @ManyToOne
    @JoinColumn(name = "bloid")
    @Schema(description = "归属组织")
    private SysActor blorg;

}
