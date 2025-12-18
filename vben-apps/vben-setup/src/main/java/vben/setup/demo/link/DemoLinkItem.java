package vben.setup.demo.link;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * 关联子表
 */
@Data
@Entity
@Schema(description = "关联子表")
public class DemoLinkItem {

    /**
     * 主键ID
     */
    @Id
    @Schema(description = "主键ID")
    @Column(length = 32)
    private String id;

    /**
     * 主表ID
     */
    @Schema(description = "主表ID")
    private Long maiid;

    /**
     * 子项目名称
     */
    @Column(length = 32)
    @Schema(description = "子项目名称")
    private String name;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    private Integer ornum;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String notes;


}
