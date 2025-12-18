package vben.bpm.todo.main;


import lombok.Data;

import java.util.Date;

/**
 * 流程待办
 */
@Data
public class BpmTodoMain {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 主题
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 紧急度
     */
    private String grade;

    /**
     * 业务类型
     */
    private String busca;

    /**
     * 业务ID
     */
    private String busid;

    /**
     * 待办链接
     */
    private String link;

    /**
     * 备注
     */
    private String notes;

    /**
     * 创建时间
     */
    private Date crtim = new Date();

    /**
     * 创建人ID
     */
    private String cruid;

}
