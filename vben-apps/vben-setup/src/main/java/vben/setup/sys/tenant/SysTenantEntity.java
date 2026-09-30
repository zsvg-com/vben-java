package vben.setup.sys.tenant;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "sys_tenant")
@Schema(description = "系统租户")
public class SysTenantEntity {

    /**
     * 主键ID
     */
    @Id
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 租户名称
     */
    @Column(length = 32)
    @Schema(description = "租户名称")
    private String name;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String notes;

    /**
     * 创建时间
     */
    private Date crtim = new Date();

    /**
     * 更新时间
     */
    private Date uptim;

    /**
     * 可用标记
     */
    private Boolean avtag;

    /**
     * 创建人ID
     */
    @Column(length = 32)
    @Schema(description = "创建人ID")
    private String cruid;

    /**
     * 更新人ID
     */
    @Column(length = 32)
    @Schema(description = "更新人ID")
    private String upuid;

}
