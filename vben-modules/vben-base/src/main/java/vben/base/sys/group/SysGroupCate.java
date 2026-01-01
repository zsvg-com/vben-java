package vben.base.sys.group;

import lombok.Data;


/**
 * 群组分类
 */
@Data
public class SysGroupCate {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 父ID
     */
    private String pid;

    /**
     * 排序号
     */
    private Integer ornum;

}
