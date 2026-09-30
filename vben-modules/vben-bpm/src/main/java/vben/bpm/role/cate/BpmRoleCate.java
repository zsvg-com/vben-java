package vben.bpm.role.cate;

import lombok.Data;


/**
 * 角色分类
 */
@Data
public class BpmRoleCate {
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
