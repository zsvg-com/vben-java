package vben.bpm.todo.user;

import lombok.Data;

/**
 * 用户待办记录
 */
@Data
public class BpmTodoUser {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 待办ID
     */
    private String todid;

    /**
     * 用户ID
     */
    private String useid;
}
