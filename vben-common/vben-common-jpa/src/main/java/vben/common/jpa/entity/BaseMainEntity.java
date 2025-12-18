package vben.common.jpa.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 主数据Entity基类
 */
@MappedSuperclass
@Getter
@Setter
public class BaseMainEntity {

    /**
     * 主键ID
     */
    @Id
    @Schema(description = "主键ID")
    protected Long id;

    /**
     * 名称
     */
    @Column(length = 126)
    @Schema(description = "名称")
    protected String name;

    /**
     * 可用标记
     */
    @Schema(description = "可用标记")
    protected Boolean avtag = true;

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
     * 创建时间
     */
    @Column(updatable = false)
    @Schema(description = "创建时间")
    protected Date crtim = new Date();

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

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    protected Date uptim;

    /**
     * 备注
     */
    @Schema(description = "备注")
    protected String notes;
}
