package vben.setup.bpm.bus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Entity
@Table(name = "bpm_bus_tmpl")
public class BpmBusTmplEntity {

    @Id
    @Schema(description = "主键ID")
    private Long id;

    @Column(length = 126)
    @Schema(description = "名称")
    private String name;

    @Schema(description = "可用标记")
    private Boolean avtag = true;// 1启用，0禁用

    @Column(length = 36)
    private String cruid;

    @Column(updatable = false)
    @Schema(description = "创建时间")
    private Date crtim = new Date();

    @Column(length = 36)
    private String upuid;

    @Schema(description = "更新时间")
    private Date uptim;

    @Schema(description = "备注")
    private String notes;

    @Schema(description = "排序号")
    private Integer ornum;

    @Column(length = 32)
    @Schema(description = "分类ID")
    private Long catid;

    @Transient
    private String bpmXml;

    @Schema(description = "流程定义ID")
    private Long prdid;

    @Schema(description = "表单定义ID")
    private Long fodid;

    @Schema(description = "表单路径")
    private String fpath;

    @Schema(description = "表单类型")
    private Integer ftype;

//    @Transient
    @Column(length = 4000)
    private String frule;
}
