package vben.setup.sys.notice;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

/**
 * 通知公告 sys_notice
 *
 */
@Data
@Entity
@Table(name = "sys_notice")
@Schema(description = "通知公告")
public class SysNoticeEntity {

    /**
     * 主键ID
     */
    @Id
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 公告标题
     */
    @Column(length = 64)
    @Schema(description = "公告标题")
    private String name;

    /**
     * 公告内容
     */
    @Column(length = 2000)
    @Schema(description = "公告内容")
    private String content;

    /**
     * 公告类型（1通知 2公告）
     */
    @Schema(description = "公告类型（1通知 2公告）")
    private Integer type;

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
