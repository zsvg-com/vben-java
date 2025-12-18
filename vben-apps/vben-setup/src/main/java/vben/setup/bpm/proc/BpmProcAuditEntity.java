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
@Table(name = "bpm_proc_audit")
@Schema(description = "流程审批")
public class BpmProcAuditEntity {

    /**
     * 主键
     */
    @Id
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 当前节点ID:N1,N2
     */
    @Column(length = 32)
    @Schema(description = "当前节点ID")
    private String facno;

    /**
     * 当前节点名称
     */
    @Column(length = 64)
    @Schema(description = "当前节点ID")
    private String facna;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    protected Date crtim = new Date();

    /**
     * 流程id
     */
    @Schema(description = "流程id")
    private Long proid;

    /**
     * 节点id
     */
    @Schema(description = "节点id")
    private Long nodid;

    /**
     * 任务id
     */
    @Schema(description = "任务id")
    private Long tasid;

    /**
     * 实处理人ID
     */
    @Column(length = 36)
    @Schema(description = "实处理人ID")
    private String hauid;



//    private String auuid;//授权处理人

//    private String exuid;//应处理人

    /**
     * 操作的key：pass，refuse
     */
    @Column(length = 32)
    @Schema(description = "操作key")
    private String opkey;

    /**
     * 操作的名称: 通过，驳回，转办等
     */
    @Column(length = 32)
    @Schema(description = "操作名称")
    private String opinf;

    /**
     * 审核留言：具体写了什么审核内容
     */
    @Schema(description = "审核留言")
    private String opnot;

    /**
     * 审核附件IDS
     */
    @Column(length = 128)
    @Schema(description = "审核附件IDS")
    private String atids;

}
