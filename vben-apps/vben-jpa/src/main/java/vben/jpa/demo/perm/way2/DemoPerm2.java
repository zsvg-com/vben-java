package vben.jpa.demo.perm.way2;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import vben.common.jpa.entity.BaseMainEntity;
import vben.common.jpa.entity.SysActor;

import java.util.List;

/**
 * 数据权限案例2
 */
@Entity
@Getter
@Setter
@Schema(description = "数据权限案例2")
public class DemoPerm2 extends BaseMainEntity {

    /**
     * 可查看者
     */
    @ManyToMany
    @JoinTable(name = "demo_perm2vimen", joinColumns = {@JoinColumn(name = "mid")},
        inverseJoinColumns = {@JoinColumn(name = "aid")})
    @Schema(description = "可查看者")
    private List<SysActor> vimen;

    /**
     * 可编辑者
     */
    @ManyToMany
    @JoinTable(name = "demo_perm2edmen", joinColumns = {@JoinColumn(name = "mid")},
        inverseJoinColumns = {@JoinColumn(name = "aid")})
    @Schema(description = "可查看者")
    private List<SysActor> edmen;

}
