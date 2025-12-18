package vben.setup.tool.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tool_dict_cate")
@Schema(description = "字典分类")
public class ToolDictCateEntity {

    /**
     * 主键ID
     */
    @Id
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 分类名称
     */
    @Column(length = 32)
    @Schema(description = "名称")
    private String name;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    private Integer ornum;

}
