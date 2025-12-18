package vben.base.sys.config;

import lombok.Data;

import java.util.Date;

/**
 * 系统参数
 */
@Data
public class SysConfig {

    /**
     * 参数主键
     */
    private Long id;

    /**
     * 参数名称
     */
    private String name;

    /**
     * 参数键名
     */
    private String kenam;

    /**
     * 参数键值
     */
    private String keval;

    /**
     * 内置标记
     */
    private Boolean intag;

    /**
     * 备注
     */
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
