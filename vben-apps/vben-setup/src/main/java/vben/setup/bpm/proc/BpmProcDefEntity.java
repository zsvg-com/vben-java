package vben.setup.bpm.proc;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Entity
@Table(name = "bpm_proc_def")
@Schema(description = "流程定义")
public class BpmProcDefEntity {


    /**
     * 主键
     */
    @Id
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 名称
     */
    @Schema(description = "name")
    private String name;

    /**
     * 可用标记
     */
    @Schema(description = "可用标记")
    private Boolean avtag = true;

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


    /**
     * 流程展示用XML
     */
    @Lob
    @Schema(description = "流程展示用XML")
    private String dixml;

    /**
     * 流程执行用XML
     */
    @Lob
    @Schema(description = "流程执行用XML")
    private String exxml;

    /**
     * 业务ID
     */
    @Schema(description = "业务ID")
    private Long busid;

}
