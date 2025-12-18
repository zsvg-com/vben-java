package vben.bpm.bus.main;

import lombok.Data;
import vben.bpm.root.domain.Zproc;
import vben.bpm.root.domain.bo.BpmStartBo;

import java.util.Date;

/**
 * 流程记录
 */
@Data
public class BpmBusMain {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 流程主题
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
     * 流程模板ID
     */
    private Long tmpid;

    /**
     * 流程状态
     */
    private String state;

    /**
     * 表单数据
     */
    private String fdata;

    /**
     * 流程定义ID
     */
    private Long prdid;

    /**
     * 表单定义ID
     */
    private Long fodid;

    /**
     * 流程对象
     */
    private Zproc zproc;

    /**
     * 流程启动对象
     */
    private BpmStartBo bpmStartBo;
}
