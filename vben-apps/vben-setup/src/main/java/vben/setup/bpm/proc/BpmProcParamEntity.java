package vben.setup.bpm.proc;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "bpm_proc_param")
@Schema(description = "流程参数")
public class BpmProcParamEntity {

    /**
     * 主键
     */
    @Id
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 参数Key
     */
    @Column(length = 32)
    @Schema(description = "参数Key")
    private String pakey;

    /**
     * 参数值
     */
    @Schema(description = "参数值")
    private String paval;

    /**
     * OFF类型
     */
    @Column(length = 32)
    @Schema(description = "OFF类型")
    private String offty;

    /**
     * OFFID
     */
    @Schema(description = "OFF类型")
    private Long offid;

    /**
     * 流程ID
     */
    @Schema(description = "流程ID")
    private Long proid;
}
