package vben.bpm.proc.def;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Data
public class BpmProcDef implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
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

    /**
     * 备注
     */
    private String notes;


    /**
     * 流程展示用XML
     */
    private String dixml;

    /**
     * 流程执行用XML
     */
    private String exxml;


//    private String bpmXml;
//
    /**
     *
     */
    private Long busid;

}
