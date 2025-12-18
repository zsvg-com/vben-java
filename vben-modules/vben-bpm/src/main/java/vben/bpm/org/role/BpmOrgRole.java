package vben.bpm.org.role;

import lombok.Data;


//层级角色
@Data
public class BpmOrgRole {

    private String id;//主键

    private String name;//角色名称

    private Integer ornum;//排序号

    private String notes;//备注

    private String treid;//角色树ID

    public BpmOrgRole() {
    }

    public BpmOrgRole(String id) {
        this.id = id;
    }

    public BpmOrgRole(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public BpmOrgRole(String id, String name, Integer ornum) {
        this.id = id;
        this.name = name;
        this.ornum = ornum;
    }
}
