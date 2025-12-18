package vben.setup.tool.code.field;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Schema(description = "代码生成-字段")
@Table(name = "tool_code_field")
public class ToolCodeFieldEntity {

    @Id
    @Column(length = 32)
    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "排序号")
    private Integer ornum;

    @Column(length = 32)
    @Schema(description = "字段名称")
    private String name;

    @Column(length = 32)
    @Schema(description = "字段描述")
    private String remark;


    @Column(length = 128)
    @Schema(description = "备注")
    private String notes;

    @Column(length = 32)
    @Schema(description = "字段类型")
    private String type;

    @Column(length = 32)
    @Schema(description = "字段长度")
    private String length;

}
