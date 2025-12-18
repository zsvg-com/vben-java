package vben.bpm.todo.done;

import lombok.Data;

import java.util.Date;

/**
 * 流程待办完结
 */
@Data
public class BpmTodoDone {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 待办id
     */
    private String todid;

    /**
     * 用户id
     */
    private String useid;

    /**
     * 完成时间
     */
    private Date entim = new Date();
}
