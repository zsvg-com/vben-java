package vben.base.sys.org.root;

import lombok.Data;

/**
 * 组织架构元素
 */
@Data
public class Org {

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

    public Org(String id) {
        this.id = id;
    }

    public Org(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Org(String id, String name, Integer type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Org() {

    }
}
