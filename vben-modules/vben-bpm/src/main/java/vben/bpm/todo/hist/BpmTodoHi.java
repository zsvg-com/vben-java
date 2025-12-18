package vben.bpm.todo.hist;


import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * 待办历史记录
 */
@Data
public class BpmTodoHi {

    /**
     * 主键ID
     */
    @Id
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
     * 业务分类
     */
    private String busca;

    /**
     * 业务ID
     */
    private String busid;

    /**
     * 链接
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
