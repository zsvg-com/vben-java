package vben.setup.sys.org.group;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import vben.common.jpa.entity.SysOrg;

import java.util.Date;
import java.util.List;

/**
 * 组织架构群组
 */
@Data
@Entity
@Table(name = "sys_org_group")
@Schema(description = "组织架构-群组")
public class SysOrgGroupEntity {

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
     * 备注
     */
    @Schema(description = "备注")
    private String notes;

    /**
     * 分类ID
     */
    @Column(length = 36)
    @Schema(description = "分类ID")
    private String catid;

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
    private Date crtim=new Date();

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Date uptim;

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

    /**
     * 成员列表
     */
    @ManyToMany
    @JoinTable(name = "sys_org_group_org", joinColumns = {@JoinColumn(name = "gid")},
        inverseJoinColumns = {@JoinColumn(name = "oid")})
    @Schema(description = "成员信息")
    private List<SysOrg> members;

}
