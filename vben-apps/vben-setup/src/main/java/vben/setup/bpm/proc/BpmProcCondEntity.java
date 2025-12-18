package vben.setup.bpm.proc;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "bpm_proc_cond")
@Schema(description = "流程条件")
public class BpmProcCondEntity {

    /**
     * 主键
     */
    @Id
    @Column(length = 32)
    @Schema(description = "主键ID")
    private String id;


    /**
     * 参数值
     */
    @Schema(description = "参数值")
    private String cond;
}
