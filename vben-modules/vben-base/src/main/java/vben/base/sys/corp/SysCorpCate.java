package vben.base.sys.corp;

import lombok.Data;


/**
 * 公司分类
 */
@Data
public class SysCorpCate {
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
