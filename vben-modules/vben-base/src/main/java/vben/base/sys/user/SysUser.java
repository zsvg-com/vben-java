package vben.base.sys.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 组织架构用户
 */
@Data
@Accessors(chain = true)
public class SysUser {

    /**
     * 主键ID
     */
    private String id;

    /**
     * 姓名（昵称）
     */
    private String name;

    /**
     * 用户名（账号）
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String monum;

    /**
     * 性别
     */
    private String gender;

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

    //---------------------im补充--
    /**
     * 直接可加还是需要同意后再加
     */
    private Boolean jotyp;

    /**
     * 地区
     */
    private String arnam;

    /**
     * 地区
     */
    private String arcod;

    /**
     * 最后离开时间
     */
    private Date oftim;

}
