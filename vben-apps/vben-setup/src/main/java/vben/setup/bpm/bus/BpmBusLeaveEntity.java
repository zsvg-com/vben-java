package vben.setup.bpm.bus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "bpm_bus_leave")
public class BpmBusLeaveEntity {

    //<<<----------------------BaseMainEntity字段开始-----------------------
    /**
     * 主键ID
     */
    @Id
    private Long id;

    /**
     * 名称|主题
     */
    @Column(length = 128)
    @Schema(description = "名称|主题")
    private String name;

    /**
     * 可用标记
     */
    @Schema(description = "可用标记")
    private Boolean avtag;

    /**
     * 创建人ID
     */
    @Column(length = 36)
    @Schema(description = "创建人ID")
    private String cruid;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date crtim = new Date();

    /**
     * 更新人ID
     */
    @Column(length = 36)
    @Schema(description = "更新人ID")
    private String upuid;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Date uptim;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String notes;
    //>>>----------------------BaseMainEntity字段结束-----------------------

    /**
     * 请假类型
     */
    @Column(length = 8)
    @Schema(description = "请假类型")
    private String type;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private Date sttim;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private Date entim;

    /**
     * 请假天数
     */
    @Schema(description = "请假天数")
    private Integer days;

    /**
     * 请假原因
     */
    @Schema(description = "请假原因")
    private String reason;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private String state;


}
