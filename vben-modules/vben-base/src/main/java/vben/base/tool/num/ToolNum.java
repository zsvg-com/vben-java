package vben.base.tool.num;

import lombok.Data;

import java.util.Date;

/**
 * 编号
 */
@Data
public class ToolNum {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 标签
     */
    private String label;

    /**
     * 名称
     */
    private String name;

    /**
     * 生成模式
     */
    private String numod;

    /**
     * 编号前缀
     */
    private String nupre;

    /**
     * 是否被修改过或新添加的
     */
    private Boolean nflag;

    /**
     * 下一个编号
     */
    private String nunex;

    /**
     * 编号长度
     */
    private Integer nulen;

    /**
     * 当前日期
     */
    private String cudat;

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

    /**
     * 备注
     */
    private String notes;

}
