package vben.setup.sys.perm.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "sys_perm_menu")
@Schema(description = "权限菜单")
public class SysPermMenuEntity {

    /**
     * 主键ID
     */
    @Id
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 名称
     */
    @Column(length = 32)
    @Schema(description = "名称")
    private String name;

    /**
     * 父ID
     */
    @Schema(description = "父ID")
    private Long pid;

    /**
     * 是否可用
     */
    @Schema(description = "可用标记 1启用，0禁用")
    private Boolean avtag;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    private Integer ornum;

    /**
     * 类型
     */
    @Column(length = 8)
    @Schema(description = "类型")
    private String type;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String notes;

    /**
     * 图标
     */
    @Column(length = 64)
    @Schema(description = "图标")
    private String icon;

    /**
     * 路由路径
     */
    @Column(length = 64)
    @Schema(description = "路由路径")
    private String path;

    /**
     * 路由参数
     */
    @Column(length = 64)
    @Schema(description = "路由参数")
    private String param;

    /**
     * 组件路径
     */
    @Column(length = 64)
    @Schema(description = "组件路径")
    private String comp;

    /**
     * 显示标记
     */
    @Schema(description = "显示标记")
    private Boolean shtag;

    /**
     * 缓存标记
     */
    @Schema(description = "缓存标记")
    private Boolean catag;

    /**
     * 外链标记
     */
    @Schema(description = "外链标记")
    private Boolean outag;

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

    /**
     * 创建人ID
     */
    @Column(length = 36)
    private String cruid;

    /**
     * 更新人ID
     */
    @Column(length = 36)
    private String upuid;

}
