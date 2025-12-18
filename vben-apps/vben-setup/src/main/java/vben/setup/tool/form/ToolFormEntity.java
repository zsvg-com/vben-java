package vben.setup.tool.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vben.common.jpa.entity.BaseMainEntity;


@Entity
@Getter
@Setter
@Schema(description = "在线表单")
@Table(name = "tool_form")
public class ToolFormEntity extends BaseMainEntity {

    @Lob
    @Schema(description = "表单规则")
    private String frule;
}
