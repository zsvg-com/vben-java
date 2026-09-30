package vben.base.sys.group;

import lombok.Data;
import vben.base.sys.actor.Actor;

import java.util.Date;
import java.util.List;

/**
 * 系统群组
 */
@Data
public class SysGroup {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String notes;

    /**
     * 分类ID
     */
    private String catid;

    /**
     * 排序号
     */
    private Integer ornum;

    /**
     * 标签
     */
    private String label;

    /**
     * 可用标记
     */
    private Boolean avtag;

    /**
     * 创建时间
     */
    private Date crtim=new Date();

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
    private List<Actor> members;

}
