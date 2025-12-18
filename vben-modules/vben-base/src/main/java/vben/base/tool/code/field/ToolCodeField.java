package vben.base.tool.code.field;

import lombok.Data;


/**
 * 代码生成-字段
 */
@Data
public class ToolCodeField {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 排序号
     */
    private Integer ornum;

    /**
     * 字段名称
     */
    private String name;

    /**
     * 字段描述
     */
    private String remark;

    /**
     * 备注
     */
    private String notes;

    /**
     * 字段类型
     */
    private String type;

    /**
     * 字段长度
     */
    private String length;

    // 表格ID
    private Long tabid;

}
