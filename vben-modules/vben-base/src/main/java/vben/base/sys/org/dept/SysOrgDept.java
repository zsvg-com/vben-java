package vben.base.sys.org.dept;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 组织架构部门
 */
@Data
public class SysOrgDept {

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
     * 子部门
     */
    private List<SysOrgDept> children = new ArrayList<>();

    /**
     * 部门类别
     * 部门在整个组织架构sys_org表中类别为1，这个字段是进行部门细分的，比如分为企业、机构、部门等
     */
    private Integer type;

    /**
     * 层级,以“_”隔开
     */
    private String tier;

    /**
     * 标签
     */
    private String label;

    /**
     * 备注
     */
    private String notes;

    /**
     * 排序号
     */
    private Integer ornum;

    /**
     * 可用标记
     */
    private Boolean avtag;

    /**
     * 创建时间
     */
    private Date crtim = new Date();

    /**
     * 更新时间
     */
    private Date uptim;

    /**
     * 创建人ID
     */
    private String cruid;

    /**
     * 更新人ID
     */
    private String upuid;

    /**
     * 自定义扩展字段1
     */
    private String ex1;

    /**
     * 自定义扩展字段2
     */
    private String ex2;

    /**
     * 自定义扩展字段3
     */
    private String ex3;

    /**
     * 自定义扩展字段4
     */
    private String ex4;
}
