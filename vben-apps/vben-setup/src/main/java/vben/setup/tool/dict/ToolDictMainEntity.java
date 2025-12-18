package vben.setup.tool.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "tool_dict_main")
@Schema(description = "字典信息")
public class ToolDictMainEntity {

    /**
     * 主键ID
     */
    @Id
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 字典名称
     */
    @Column(length = 64)
    @Schema(description = "名称")
    private String name;

    /**
     * 字典代码
     */
    @Column(length = 64)
    @Schema(description = "字典代码")
    private String code;


    /**
     * 分类ID
     */
    @Schema(description = "分类ID")
    private Long catid;

    /**
     * 备注
     */
    @Schema(description = "名称")
    private String notes;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date crtim = new Date();

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Date uptim;

    /**
     * 可用标记
     */
    @Schema(description = "可用标记")
    private Boolean avtag;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    private Integer ornum;

}
