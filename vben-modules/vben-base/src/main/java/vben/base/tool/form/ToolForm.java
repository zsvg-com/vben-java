package vben.base.tool.form;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class ToolForm {

    /**
     * 主键ID
     */
    @Id
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 可用标记
     */
    private Boolean avtag = true;

    /**
     * 创建人ID
     */
    private String cruid;

    /**
     * 创建人姓名
     */
    private String cruna;

    /**
     * 创建时间
     */
    private Date crtim = new Date();

    /**
     * 更新人ID
     */
    private String upuid;

    /**
     * 更新人姓名
     */
    private String upuna;

    /**
     * 更新时间
     */
    private Date uptim;

    /**
     * 备注
     */
    private String notes;

    /**
     * 备注
     */
    private String frule;
}
