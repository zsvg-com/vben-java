package vben.base.sys.org.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 组织架构用户
 */
@Data
@Accessors(chain = true)
public class SysOrgUser {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 昵称
     */
    private String ninam;

    /**
     * 部门ID
     */
    private String depid;

    /**
     * 部门名称
     */
    private String depna;

    /**
     * 层级，以“_”隔开
     */
    private String tier;

    /**
     * 职务
     */
    private String job;

    /**
     * 登录名
     */
    private String usnam;

    /**
     * 密码
     */
    private String pacod;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private String sex;

    /**
     * 手机号
     */
    private String monum;

    /**
     * 排序号
     */
    private Integer ornum;

    /**
     * 可用标记
     */
    private Boolean avtag;

    /**
     * 标签
     */
    private String label;

    /**
     * 备注
     */
    private String notes;

    /**
     * 缓存标记
     */
    private Boolean catag = false;

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
     * 头像URL
     */
    private String avatar;

    /**
     * 登录IP
     */
    private String loip;

    /**
     * 登录时间
     */
    private Date lotim;

    /**
     * 用户类别
     */
    private Integer type;

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
