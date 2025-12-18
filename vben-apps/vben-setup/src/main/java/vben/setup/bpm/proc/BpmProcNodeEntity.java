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
@Table(name = "bpm_proc_node")
@Schema(description = "流程节点")
public class BpmProcNodeEntity {

    /**
     * 主键
     */
    @Id
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 当前节点编号:N1,N2
     */
    @Column(length = 32)
    @Schema(description = "当前节点编号")
    private String facno;

    /**
     * 当前节点名称
     */
    @Column(length = 64)
    @Schema(description = "当前节点名称")
    private String facna;

    /**
     * 当前节点类型
     */
    @Column(length = 32)
    @Schema(description = "当前节点类型")
    private String facty;

    /**
     * 流转方式
     */
    @Column(length = 32)
    @Schema(description = "流转方式")
    private String flway;//1 顺签 2 或签 3 会签

    /**
     * 流程实例id
     */
    @Schema(description = "流转方式")
    private Long proid;

    /**
     * 状态
     */
    @Column(length = 32)
    @Schema(description = "状态")
    private String state;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private Date sttim = new Date();

}
