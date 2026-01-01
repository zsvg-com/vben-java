package vben.setup.sys.rece;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

/**
 * 组织架构最近访问记录
 */
@Data
@Entity
@Table(name = "sys_rece")
@Schema(description = "组织架构-最近访问记录")
public class SysReceEntity {

    /**
     * 主键ID
     */
    @Id
    @Column(length = 36)
    @Schema(description = "主键ID")
    private String id;

    /**
     * 用户ID
     */
    @Column(length = 36)
    @Schema(description = "用户ID")
    private String useid;

    /**
     * 最近使用的组织架构ID
     */
    @Column(length = 36)
    @Schema(description = "最近使用的组织架构ID")
    private String oid;

    /**
     * 最近使用时间
     */
    @Schema(description = "最近使用的组织架构ID")
    private Date uptim = new Date();
}
