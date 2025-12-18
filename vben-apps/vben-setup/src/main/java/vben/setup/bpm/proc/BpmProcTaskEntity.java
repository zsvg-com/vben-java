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
@Table(name = "bpm_proc_task")
@Schema(description = "流程任务")
public class BpmProcTaskEntity {

    /**
     * 主键
     */
    @Id
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 任务类型
     */
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
     * 消息类型
     */
    @Column(length = 32)
    @Schema(description = "消息类型")
    private String notty;

    /**
     * 状态
     */
    @Column(length = 8)
    @Schema(description = "状态")
    private String state;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    private Integer ornum;

    /**
     * 是否激活
     */
    @Schema(description = "是否激活")
    private Boolean actag;

    /**
     * 实处理人
     */
    @Schema(description = "实处理人")
    @Column(length = 36)
    private String hauid;

    /**
     * 授权处理人
     */
    @Schema(description = "授权处理人")
    @Column(length = 36)
    private String auuid;

    /**
     * 应处理人
     */
    @Schema(description = "应处理人")
    @Column(length = 36)
    private String exuid;
}
