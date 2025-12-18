package vben.bpm.form.def;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Data
public class BpmFormDef implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 版本号
     */
    private Integer venum;

    /**
     * 表单配置json
     */
    private String json;

    /**
     * 创建人ID
     */
    private String cruid;

    /**
     * 创建时间
     */
    private Date crtim = new Date();

    /**
     * 更新人ID
     */
    private String upuid;

    /**
     * 更新时间
     */
    private Date uptim;

    //业务ID
    private Long busid;


}
