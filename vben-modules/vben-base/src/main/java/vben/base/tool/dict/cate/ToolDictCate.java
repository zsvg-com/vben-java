package vben.base.tool.dict.cate;

import lombok.Data;

/**
 * 字典分类
 */
@Data
public class ToolDictCate {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 排序号
     */
    private Integer ornum;

}
