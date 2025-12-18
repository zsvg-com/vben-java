package vben.base.tool.dict.data;

import lombok.Data;

import java.util.Date;

/**
 * 字典数据
 */
@Data
public class ToolDictData {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 数据标签
     */
    private String dalab;

    /**
     * 数据键值
     */
    private String daval;

    /**
     * 字典ID
     */
    private Long dicid;

    /**
     * 显示样式
     */
    private String shsty;

    /**
     * 默认标记
     */
    private Boolean detag;

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
