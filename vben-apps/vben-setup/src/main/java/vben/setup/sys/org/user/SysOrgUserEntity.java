package vben.setup.sys.org.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

/**
 * 组织架构用户
 */
@Data
@Entity
@Table(name = "sys_org_user")
@Schema(description = "组织架构-用户")
public class SysOrgUserEntity {

    /**
     * 主键ID
     */
    @Id
    @Column(length = 36)
    @Schema(description = "主键ID")
    private String id;

    /**
     * 姓名
     */
    @Column(length = 16)
    @Schema(description = "姓名")
    private String name;

    /**
     * 自定义昵称
     */
    @Column(length = 16)
    @Schema(description = "昵称")
    private String ninam;

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
     * 登录名
     */
    @Column(length = 32)
    @Schema(description = "登录名")
    private String usnam;

    /**
     * 密码
     */
    @Column(length = 64)
    @Schema(description = "密码")
    private String pacod;

    /**
     * 邮箱
     */
    @Column(length = 32)
    @Schema(description = "邮箱")
    private String email;

    /**
     * 性别
     */
    @Column(length = 8)
    @Schema(description = "性别")
    private String sex;

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
     * 标签
     */
    @Column(length = 32)
    @Schema(description = "标签")
    private String label;

    /**
     * 备注
     */
    @Schema(description = "notes")
    private String notes;

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
     * 自定义扩展字段1
     */
    @Column(length = 32)
    @Schema(description = "扩展字段1")
    private String ex1;

    /**
     * 自定义扩展字段2
     */
    @Column(length = 32)
    @Schema(description = "扩展字段2")
    private String ex2;

    /**
     * 自定义扩展字段3
     */
    @Column(length = 32)
    @Schema(description = "扩展字段3")
    private String ex3;

    /**
     * 自定义扩展字段4
     */
    @Column(length = 32)
    @Schema(description = "扩展字段4")
    private String ex4;

}
