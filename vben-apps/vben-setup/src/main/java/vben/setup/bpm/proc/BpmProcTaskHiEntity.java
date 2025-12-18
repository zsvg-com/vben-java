package vben.setup.bpm.proc;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;


@Data
@Entity
@Table(name = "bpm_proc_task_hi")
@Schema(description = "流程任务历史")
public class BpmProcTaskHiEntity {

    /**
     * 主键
     */
    @Id
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 任务类型
     */
    @Column(length = 32)
    @Schema(description = "任务类型")
    private String type;

    /**
     * 流程实例id
     */
    @Schema(description = "流程实例id")
    private Long proid;

    /**
     * 节点id
     */
    @Schema(description = "节点id")
    private Long nodid;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private Date sttim = new Date();

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private Date entim;

    /**
     * 消息类型
     */
    @Schema(description = "消息类型")
    private String notty;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private String state;

    /**
     * 实处理人ID
     */
    @Schema(description = "实处理人ID")
    @Column(length = 36)
    private String hauid;

    /**
     * 授权处理人ID
     */
    @Schema(description = "授权处理人ID")
    @Column(length = 36)
    private String auuid;

    /**
     * 应处理人ID
     */
    @Schema(description = "应处理人ID")
    @Column(length = 36)
    private String exuid;
}
