package vben.setup.tool.oss;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Schema(description = "OSS存储文件")
@Table(name = "tool_oss_file",indexes = {@Index(columnList = "md5")})
public class ToolOssFileEntity {

    @Id
    @Column(length = 32)
    @Schema(description = "主键ID")
    private String id;

    @Column(length = 32)
    @Schema(description = "文件md5")
    private String md5;

    @Schema(description = "文件大小")
    private Long fsize;

    @Schema(description = "存储地址")
    private String path;

    @Column(length = 32)
    @Schema(description = "服务商")
    private String service;

    @Column(updatable = false)
    @Schema(description = "创建时间")
    private Date crtim = new Date();

}
