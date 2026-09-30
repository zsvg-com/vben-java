package vben.base.sys.tenant;

import lombok.Data;

import java.util.Date;

/**
 * 系统租户
 */
@Data
public class SysTenant {

    /**
     * 租户ID
     */
    private Long id;

    /**
     * 租户名称
     */
    private String name;

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
     * 创建人ID
     */
    private String cruid;

    /**
     * 更新人ID
     */
    private String upuid;

    /**
     * 创建人昵称
     */
    private String cruna;

    /**
     * 更新人昵称
     */
    private String upuna;



}
