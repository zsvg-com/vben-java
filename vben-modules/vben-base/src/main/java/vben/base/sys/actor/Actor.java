package vben.base.sys.actor;

import lombok.Data;

/**
 * 系统参与者
 */
@Data
public class Actor {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private Integer type;//1为部门,2为用户,4为岗位,8为群组,16为流程角色

    public Actor(String id) {
        this.id = id;
    }

    public Actor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Actor(String id, String name, Integer type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Actor() {

    }
}
