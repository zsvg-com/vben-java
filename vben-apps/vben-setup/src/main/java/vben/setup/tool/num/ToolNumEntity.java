package vben.setup.tool.num;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "tool_num")
@Schema(description = "编号信息")
public class ToolNumEntity {
    @Id
    @Column(length = 8)
    @Schema(description = "主键ID")
    private String id;

    @Column(length = 100)
    @Schema(description = "标签")
    private String label;

    @Column(length = 32)
    @Schema(description = "名称")
    private String name;

    @Column(length = 32)
    @Schema(description = "生成模式")
    private String numod;

    @Column(length = 32)
    @Schema(description = "编号前缀")
    private String nupre;

    @Schema(description = "是否被修改过或新添加的")
    private Boolean nflag;

    @Column(length = 8)
    @Schema(description = "下一个编号")
    private String nunex;

    @Schema(description = "编号长度")
    private Integer nulen;

    @Schema(description = "当前日期")
    @Column(length = 8)
    private String cudat;

    @Schema(description = "创建时间")
    @Column(updatable = false)
    private Date crtim = new Date();

    @Schema(description = "更新时间")
    private Date uptim;

    @Schema(description = "可用标记")
    private Boolean avtag;

    @Schema(description = "排序号")
    private Integer ornum;

    @Schema(description = "备注")
    private String notes;
}
