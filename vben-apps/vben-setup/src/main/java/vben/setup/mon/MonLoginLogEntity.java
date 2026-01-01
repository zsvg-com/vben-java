package vben.setup.mon;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "mon_login_log")
@Schema(description = "登录日志")
public class MonLoginLogEntity {

    /**
     * 日志主键
     */
    @Id
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 租户编号
     */
    @Column(length = 16)
    @Schema(description = "租户编号")
    private String tenid;

    /**
     * 用户账号
     */
    @Column(length = 16)
    @Schema(description = "用户账号")
    private String username;

    /**
     * 客户端
     */
    @Column(length = 32)
    @Schema(description = "客户端")
    private String clkey;

    /**
     * 设备类型
     */
    @Column(length = 8)
    @Schema(description = "设备类型")
    private String detyp;

    /**
     * 登录状态 1成功 0失败
     */
    @Schema(description = "登录状态")
    private Boolean sutag;

    /**
     * 登录IP地址
     */
    @Column(length = 16)
    @Schema(description = "登录IP地址")
    private String loip;

    /**
     * 登录地点
     */
    @Column(length = 64)
    @Schema(description = "登录地点")
    private String loloc;

    /**
     * 浏览器
     */
    @Column(length = 64)
    @Schema(description = "浏览器")
    private String browser;

    /**
     * 操作系统
     */
    @Column(length = 64)
    @Schema(description = "操作系统")
    private String os;


    /**
     * 提示消息
     */
    @Schema(description = "操作系统")
    private String himsg;


    /**
     * 登录时间
     */
    @Schema(description = "登录时间")
    private Date lotim;

}
