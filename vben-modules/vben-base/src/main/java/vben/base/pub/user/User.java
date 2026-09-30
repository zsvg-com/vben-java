package vben.base.pub.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 *  用户信息
 */
@Data
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String useid;

    /**
     * 用户账号
     */
    private String usena;

    /**
     * 用户昵称
     */
    private String nicna;

    /**
     * 用户类型（sys_user系统用户）
     */
    private String usety;

    /**
     * 组织ID
     */
    private String orgid;

    /**
     * 组织名称
     */
    private String orgna;

    /**
     * 租户ID
     */
    private String tenid;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String monum;

    /**
     * 用户性别（0男 1女 2未知）
     */
    private String gender;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 密码
     */
    @JsonIgnore
    @JsonProperty
    private String pwd;

    /**
     * 帐号状态（1正常 0停用）
     */
    private Boolean avtag;

    /**
     * 最后登录IP
     */
    private String loip;

    /**
     * 最后登录时间
     */
    private Date lotim;

    /**
     * 备注
     */
    private String notes;

    /**
     * 创建时间
     */
    private Date crtim;

    /**
     * 角色对象
     */

    /**
     * 角色组
     */
    private Long[] roids;

    /**
     * 岗位组
     */
    private Long[] poids;

    /**
     * 数据权限 当前角色ID
     */
    private Long rolid;

}
