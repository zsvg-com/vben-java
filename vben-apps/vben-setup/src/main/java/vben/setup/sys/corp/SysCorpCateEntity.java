package vben.setup.sys.corp;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


/**
 * 公司分类
 */
@Data
@Entity
@Table(name = "sys_corp_cate")
@Schema(description = "公司分类")
public class SysCorpCateEntity {
    /**
     * 主键ID
     */
    @Id
    @Column(length = 32)
    @Schema(description = "主键ID")
    private String id;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 父ID
     */
    @Column(length = 32)
    @Schema(description = "父ID")
    private String pid;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    private Integer ornum;


}
