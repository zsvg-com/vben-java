package vben.setup.sys.config;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "sys_config")
@Schema(description = "系统参数")
public class SysConfigEntity {

    /**
     * 参数主键
     */
    @Id
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 参数名称
     */
    @Column(length = 32)
    @Schema(description = "参数名称")
    private String name;

    /**
     * 参数键名
     */
    @Column(length = 32)
    @Schema(description = "参数键名")
    private String kenam;

    /**
     * 参数键值
     */
    @Column(length = 32)
    @Schema(description = "参数键值")
    private String keval;

    /**
     * 内置标记
     */
    private Boolean intag;

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
     * 排序号
     */
    private Integer ornum;

}
