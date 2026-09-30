package vben.bpm.role.main;

import lombok.Data;


//流程角色
@Data
public class BpmRole {

    private String id;//主键

    private String name;//角色名称

    private Integer ornum;//排序号

    private String notes;//备注

    private String treid;//角色树ID

    public BpmRole() {
    }

    public BpmRole(String id) {
        this.id = id;
    }

    public BpmRole(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public BpmRole(String id, String name, Integer ornum) {
        this.id = id;
        this.name = name;
        this.ornum = ornum;
    }
}
