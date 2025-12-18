package vben.bpm.bus.tmpl;

import lombok.Data;

import java.util.Date;

/**
 * 流程模板
 */
@Data
public class BpmBusTmpl {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 可用标记
     */
    private Boolean avtag = true;

    /**
     * 创建人ID
     */
    private String cruid;

    /**
     * 创建时间
     */
    private Date crtim = new Date();

    /**
     * 更新人ID
     */
    private String upuid;

    /**
     * 更新时间
     */
    private Date uptim;

    /**
     * 备注
     */
    private String notes;

    /**
     * 排序号
     */
    private Integer ornum;

    /**
     * 分类ID
     */
    private Long catid;

    /**
     * 流程定义XML
     */
    private String bpmXml;

    /**
     * 流程定义ID
     */
    private Long prdid;

    /**
     * 表单定义ID
     */
    private Long fodid;

    /**
     * 表单类型
     */
    private Integer ftype;

    /**
     * 表单路径
     */
    private String fpath;

    /**
     * 表单规则
     */
    private String frule;

}
