package vben.setup.tool.oss;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import vben.common.jpa.entity.SysOrg;

import java.util.Date;

@Entity
@Data
@Schema(description = "OSS存储引用")
@Table(name = "tool_oss_main")
public class ToolOssMainEntity {

    @Id
    @Column(length = 32)
    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "文件名称")
    private String name;

    @Column(length = 32)
    @Schema(description = "类型（后缀）")
    private String type;

    @Column(length = 32)
    @Schema(description = "文件ID")
    private String filid;

    @Column(length = 32)
    @Schema(description = "业务ID")
    private String busid;

    @Column(updatable = false)
    @Schema(description = "创建时间")
    private Date crtim = new Date();

    @ManyToOne
    @JoinColumn(name = "cruid", updatable = false)
    @Schema(description = "创建人")
    protected SysOrg crman;

}
