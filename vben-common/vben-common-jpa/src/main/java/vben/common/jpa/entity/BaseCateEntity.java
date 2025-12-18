package vben.common.jpa.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 分类Entity基类，可实现无限多级的树结构
 */
@MappedSuperclass
@Getter
@Setter
public class BaseCateEntity {

    /**
     * 主键ID
     */
    @Id
    @Schema(description = "主键ID")
    protected Long id;

    /**
     * 名称
     */
    @Schema(description = "名称")
    protected String name;

    /**
     * 父分类ID
     */
    @Schema(description = "父分类ID")
    protected Long pid;

    /**
     * 层级信息
     */
    @Column(length = 512)
    @Schema(description = "层级信息")
    protected String tier;

    /**
     * 子元素
     */
    @Transient
    protected List<BaseCateEntity> children = new ArrayList<>();

    /**
     * 标签
     */
    @Column(length = 32)
    @Schema(description = "标签")
    protected String label;

    /**
     * 备注
     */
    @Schema(description = "备注")
    protected String notes;

    /**
     * 可用标记
     */
    @Schema(description = "可用标记 1启用，0禁用")
    protected Boolean avtag;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    protected Integer ornum;

    /**
     * 创建时间
     */
    @Column(updatable = false)
    @Schema(description = "创建时间")
    protected Date crtim = new Date();

    /**
     * 创建人ID
     */
    @Column(length = 36)
    @Schema(description = "创建人ID")
    protected String cruid;

    /**
     * 创建人姓名
     */
    @Transient
    protected String cruna;


    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    protected Date uptim;

    /**
     * 更新人ID
     */
    @Column(length = 36)
    @Schema(description = "更新人ID")
    protected String upuid;

    /**
     * 更新人姓名
     */
    @Transient
    protected String upuna;
}
