package vben.setup.sys.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

/**
 * 系统用户
 */
@Data
@Entity
@Table(name = "sys_user")
@Schema(description = "系统用户")
public class SysUserEntity {

    /**
     * 主键ID
     */
    @Id
    @Column(length = 36)
    @Schema(description = "主键ID")
    private String id;

    /**
     * 姓名（昵称）
     */
    @Column(length = 16)
    @Schema(description = "姓名（昵称）")
    private String name;

    /**
     * 用户名（账号）
     */
    @Column(length = 32)
    @Schema(description = "用户名（账号）")
    private String username;

    /**
     * 密码
     */
    @Column(length = 64)
    @Schema(description = "密码")
    private String password;


    /**
     * 邮箱
     */
    @Column(length = 32)
    @Schema(description = "邮箱")
    private String email;


    /**
     * 手机号
     */
    @Column(length = 16)
    @Schema(description = "手机号")
    private String monum;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    private Integer ornum;

    /**
     * 可用标记
     */
    @Schema(description = "可用标记")
    private Boolean avtag;


    /**
     * 部门ID
     */
    @Column(length = 36)
    @Schema(description = "部门ID")
    private String depid;

    /**
     * 层级，建议不超过10级
     */
    @Column(length = 512)
    @Schema(description = "层级")
    private String tier;

    /**
     * 职务
     */
    @Column(length = 64)
    @Schema(description = "职务")
    private String job;


    /**
     * 性别
     */
    @Column(length = 8)
    @Schema(description = "性别")
    private String gender;

    /**
     * 标签
     */
    @Column(length = 32)
    @Schema(description = "标签")
    private String label;


    /**
     * 缓存标记
     */
    @Schema(description = "缓存标记")
    private Boolean catag = false;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date crtim = new Date();

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Date uptim;

    /**
     * 创建人ID
     */
    @Column(length = 32)
    @Schema(description = "创建人ID")
    private String cruid;

    /**
     * 更新人ID
     */
    @Column(length = 32)
    @Schema(description = "更新人ID")
    private String upuid;

    /**
     * 头像url
     */
    @Schema(description = "头像url")
    @Column(length = 128)
    private String avatar;

    /**
     * 登录IP
     */
    @Column(length = 20)
    @Schema(description = "登录IP")
    private String loip;

    /**
     * 登录时间
     */
    @Schema(description = "登录时间")
    private Date lotim;

    /**
     * 用户类别
     */
    @Schema(description = "用户类别")
    private Integer type;

    /**
     * 备注--个性签名
     */
    @Schema(description = "notes")
    private String notes;

    //---------------------im补充--
    /**
     * 直接可加还是需要同意后再加
     */
    @Schema(description = "直接可加还是需要同意后再加")
    private Boolean jotyp;

    /**
     * 地区
     */
    @Column(length = 64)
    @Schema(description = "地区名称")
    private String arnam;

    /**
     * 地区
     */
    @Column(length = 64)
    @Schema(description = "地区编号")
    private String arcod;

    /**
     * 最后离开时间
     */
    @Schema(description = "最后离开时间")
    private Date oftim;

}
