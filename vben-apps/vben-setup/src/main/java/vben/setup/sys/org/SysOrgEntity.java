package vben.setup.sys.org;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

/**
 * 系统组织（公司，机构，部门）
 */
@Data
@Entity
@Table(name = "sys_org")
@Schema(description = "系统组织（公司，机构，部门）")
public class SysOrgEntity {

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
     * 父ID
     */
    @Column(length = 36)
    @Schema(description = "父ID")
    private String pid;


    /**
     * 组织类型
     * 系统组织（公司，机构，部门）在系统参与者sys_actor表中类别为1，这个字段是进行组织细分的，比如分为集团、企业、机构、部门等
     */
    @Schema(description = "组织类型")
    private Integer type;

    /**
     * 层级，建议不超过10级
     */
    @Column(length = 512)
    @Schema(description = "层级")
    private String tier;

    /**
     * 标签
     */
    @Column(length = 32)
    @Schema(description = "标签")
    private String label;

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
