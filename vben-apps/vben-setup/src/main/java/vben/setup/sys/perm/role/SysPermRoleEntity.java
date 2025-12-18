package vben.setup.sys.perm.role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import vben.common.jpa.entity.SysOrg;
import vben.setup.sys.perm.api.SysPermApiEntity;
import vben.setup.sys.perm.menu.SysPermMenuEntity;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "sys_perm_role")
@Schema(description = "权限角色")
public class SysPermRoleEntity {

    /**
     * 主键ID
     */
    @Id
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 角色名称
     */
    @Column(length = 32)
    @Schema(description = "角色名称")
    private String name;

    /**
     * 角色类型
     */
    @Schema(description = "角色类型")
    private Integer type;

    /**
     * 数据权限
     */
    @Schema(description = "数据权限")
    private Integer scope;

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
     * 创建人ID
     */
    @Column(length = 36)
    @Schema(description = "创建人ID")
    private String cruid;

    /**
     * 创建人姓名
     */
    @Transient
    private String cruna;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Date uptim;

    /**
     * 更新人ID
     */
    @Column(length = 36)
    @Schema(description = "更新人ID")
    private String upuid;

    /**
     * 更新人姓名
     */
    @Transient
    private String upuna;

    /**
     * 可用标记
     */
    @Schema(description = "可用标记")
    private Boolean avtag;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    private Integer ornum;

    @ManyToMany
    @JoinTable(name = "sys_perm_role_org", joinColumns = {@JoinColumn(name = "rid")},
        inverseJoinColumns = {@JoinColumn(name = "oid")})
    @Schema(description = "成员列表")
    private List<SysOrg> orgs;

    @ManyToMany
    @JoinTable(name = "sys_perm_role_menu", joinColumns = {@JoinColumn(name = "rid")},
        inverseJoinColumns = {@JoinColumn(name = "mid")})
    @Schema(description = "菜单列表")
    private List<SysPermMenuEntity> menus;

    @ManyToMany
    @JoinTable(name = "sys_perm_role_api", joinColumns = {@JoinColumn(name = "rid")},
        inverseJoinColumns = {@JoinColumn(name = "aid")})
    @Schema(description = "接口列表")
    private List<SysPermApiEntity> apis;
}
