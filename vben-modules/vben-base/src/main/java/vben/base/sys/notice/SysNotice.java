package vben.base.sys.notice;

import lombok.Data;

import java.util.Date;

/**
 * 系统通知
 */
@Data
public class SysNotice {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 公告标题
     */
    private String name;

    /**
     * 公告内容
     */
    private String cont;

    /**
     * 公告类型（1通知 2公告）
     */
    private Integer type;

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
