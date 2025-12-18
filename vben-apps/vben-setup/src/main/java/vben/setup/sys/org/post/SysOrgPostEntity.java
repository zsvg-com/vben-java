package vben.setup.sys.org.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import vben.common.jpa.entity.SysOrg;

import java.util.Date;
import java.util.List;

/**
 * 组织架构岗位
 */
@Data
@Entity
@Table(name = "sys_org_post")
@Schema(description = "组织架构-岗位")
public class SysOrgPostEntity {

    /**
     * 主键ID
     */
    @Id
    @Column(length = 36)
    @Schema(description = "主键ID")
    private String id;

    /**
     * 名称
     */
    @Column(length = 64)
    @Schema(description = "名称")
    private String name;

    /**
     * 部门ID
     */
    @Column(length = 36)
    @Schema(description = "部门ID")
    private String depid;

    /**
     * 层级，以“_”隔开
     */
    @Column(length = 512)
    @Schema(description = "层级")
    private String tier;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String notes;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    private Integer ornum;

    /**
     * 可用标记
     */
    @Schema(description = "可用标记")
    private Boolean avtag;

    /**
     * 标签
     */
    @Column(length = 32)
    @Schema(description = "标签")
    private String label;

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
    @Schema(description = "创建人ID")
    private String cruid;

    /**
     * 更新人ID
     */
    @Column(length = 36)
    @Schema(description = "更新人ID")
    private String upuid;

    /**
     * 包含成员
     */
    @ManyToMany
    @JoinTable(name = "sys_org_post_org", joinColumns = {@JoinColumn(name = "pid")},
        inverseJoinColumns = {@JoinColumn(name = "oid")})
    @Schema(description = "员工列表")
    private List<SysOrg> users;

}
