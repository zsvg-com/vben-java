package vben.base.sys.post;

import lombok.Data;
import vben.base.sys.actor.Actor;

import java.util.Date;
import java.util.List;

/**
 * 系统岗位
 */
@Data
public class SysPost {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 组织ID
     */
    private String orgid;

    /**
     * 层级，以“_”隔开
     */
    private String tier;

    /**
     * 备注
     */
    private String notes;

    /**
     * 排序号
     */
    private Integer ornum;

    /**
     * 可用标记
     */
    private Boolean avtag;

    /**
     * 标签
     */
    private String label;

    /**
     * 创建时间
     */
    private Date crtim = new Date();

    /**
     * 更新时间
     */
    private Date uptim;

    /**
     * 创建人ID
     */
    private String cruid;

    /**
     * 更新人ID
     */
    private String upuid;

    /**
     * 包含成员
     */
    private List<Actor> users;

}
