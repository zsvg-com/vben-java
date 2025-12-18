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
@Table(name = "mon_oper_log")
@Schema(description = "操作日志")
public class MonOperLogEntity {

    /**
     * 主键ID
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
     * 操作模块
     */
    @Column(length = 32)
    @Schema(description = "操作模块")
    private String opmod;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    @Schema(description = "业务类型")
    private Integer butyp;

    /**
     * 请求方法
     */
    @Column(length = 64)
    @Schema(description = "请求方法")
    private String remet;

    /**
     * 请求方式
     */
    @Column(length = 8)
    @Schema(description = "请求方式")
    private String reway;

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    @Schema(description = "操作类别")
    private Integer optyp;

    /**
     * 操作人员
     */
    @Column(length = 16)
    @Schema(description = "操作人员")
    private String opuna;

    /**
     * 部门名称
     */
    @Column(length = 64)
    @Schema(description = "操作人员")
    private String opdna;

    /**
     * 请求url
     */
    @Column(length = 64)
    @Schema(description = "请求url")
    private String reurl;

    /**
     * 操作IP地址
     */
    @Column(length = 32)
    @Schema(description = "操作IP地址")
    private String opip;

    /**
     * 操作地点
     */
    @Column(length = 64)
    @Schema(description = "操作地点")
    private String oploc;

    /**
     * 请求参数
     */
    @Column(length = 4000)
    @Schema(description = "请求参数")
    private String repar;

    /**
     * 返回参数
     */
    @Column(length = 4000)
    @Schema(description = "返回参数")
    private String bapar;

    /**
     * 操作状态（1成功 0失败）
     */
    @Schema(description = "操作状态")
    private Boolean sutag;

    /**
     * 错误消息
     */
    @Column(length = 4000)
    @Schema(description = "错误消息")
    private String ermsg;

    /**
     * 操作时间
     */
    @Schema(description = "操作时间")
    private Date optim;

    /**
     * 消耗时间
     */
    @Schema(description = "消耗时间")
    private Long cotim;

}
