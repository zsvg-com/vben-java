package vben.bpm.role.node;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//流程角色节点
@Data
public class BpmRoleNode {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 子节点
     */
    private List<BpmRoleNode> children = new ArrayList<>(); //行项目

    /**
     * 父ID
     */
    private String pid;

    /**
     * 组织树id
     */
    private String treid;

    /**
     * 层级
     */
    private String tier;

    /**
     * 备注
     */
    private String notes;

    /**
     * 排序号
     */
    private Integer ornum;

    /**
     * 创建时间
     */
    private Date crtim = new Date();

    /**
     * 更新时间
     */
    private Date uptim;


    /**
     * 成员ID
     */
    private String memid;

    /**
     * 成员名称
     */
    private String memna;

    public BpmRoleNode() {
    }

    public BpmRoleNode(String id) {
        this.id = id;
    }
}
