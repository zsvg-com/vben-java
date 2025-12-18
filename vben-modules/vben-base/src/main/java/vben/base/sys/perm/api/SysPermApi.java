package vben.base.sys.perm.api;

import lombok.Data;

import java.util.Date;

/**
 * 权限接口
 */
@Data
public class SysPermApi {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 所属菜单ID
     */
    private Long menid;

    /**
     * 排序号
     */
    private Integer ornum;

    /**
     * 权限字符
     */
    private String perm;

    /**
     * 权限代码
     */
    private Long code;

    /**
     * 权限位
     */
    private Integer pos;

    /**
     * 权限类型
     */
    private String type;

    /**
     * 可用标记
     */
    private Boolean avtag;

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

}
