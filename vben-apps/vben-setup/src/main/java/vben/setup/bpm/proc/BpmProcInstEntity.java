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
@Table(name = "bpm_proc_inst")
@Schema(description = "流程实例")
public class BpmProcInstEntity {


    /**
     * 主键
     */
    @Id
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 主题
     */
    @Schema(description = "主题")
    private String name;

    /**
     * 可用标记
     */
    @Schema(description = "可用标记")
    private Boolean avtag = true;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    @Column(length = 36,updatable = false)
    private String cruid;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date crtim = new Date();

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    @Column(length = 36)
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
     * 业务模型ID
     */
    @Column(length = 32)
    @Schema(description = "业务ID")
    private String busid;

    /**
     * 业务模型类型
     */
    @Column(length = 32)
    @Schema(description = "业务类型")
    private String busty;


    /**
     * 流程定义ID
     */
    @Schema(description = "流程定义ID")
    private Long prdid;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private String state;

    public BpmProcInstEntity() {

    }


}
