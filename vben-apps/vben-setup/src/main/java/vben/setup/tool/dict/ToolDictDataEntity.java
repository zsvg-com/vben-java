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
@Table(name = "tool_dict_data")
@Schema(description = "字典数据")
public class ToolDictDataEntity {

    /**
     * 主键ID
     */
    @Id
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 数据标签
     */
    @Column(length = 64)
    @Schema(description = "数据标签")
    private String dalab;

    /**
     * 数据键值
     */
    @Column(length = 32)
    @Schema(description = "数据键值")
    private String daval;

    /**
     * 字典ID
     */
    @Schema(description = "字典ID")
    private Long dicid;

    /**
     * 显示样式
     */
    @Column(length = 32)
    @Schema(description = "显示样式")
    private String shsty;

    /**
     * 默认标记
     */
    @Schema(description = "默认标记")
    private Boolean detag;

    /**
     * 备注
     */
    @Schema(description = "备注")
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
