package vben.base.tool.dict.main;

import lombok.Data;

import java.util.Date;

/**
 * 字典信息
 */
@Data
public class ToolDictMain {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典代码
     */
    private String code;

    /**
     * 分类ID
     */
    private Long catid;

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
