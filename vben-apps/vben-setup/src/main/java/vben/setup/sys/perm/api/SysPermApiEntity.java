package vben.setup.sys.perm.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "sys_perm_api")
@Schema(description = "权限接口")
public class SysPermApiEntity {

    /**
     * 主键ID
     */
    @Id
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 接口名称
     */
    @Column(length = 32)
    @Schema(description = "名称")
    private String name;

    /**
     * 菜单ID
     */
    @Schema(description = "菜单ID")
    private Long menid;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    private Integer ornum;

    /**
     * 权限字符
     */
    @Column(length = 64)
    @Schema(description = "权限字符")
    private String perm;

    /**
     * 权限代码
     */
    @Schema(description = "权限代码")
    private Long code;

    /**
     * 权限位
     */
    @Schema(description = "权限位")
    private Integer pos;

    /**
     * 权限类型
     */
    @Column(length = 8)
    @Schema(description = "权限类型")
    private String type;

    /**
     * 可用标记
     */
    @Schema(description = "可用标记")
    private Boolean avtag;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String notes;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date crtim = new Date();

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Date uptim;

    public SysPermApiEntity() {

    }

    public SysPermApiEntity(Long id) {
        this.id = id;
    }

}
