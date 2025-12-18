package vben.bpm.bus.leave;

import lombok.Data;
import vben.bpm.bus.main.BpmBusMain;

import java.util.Date;

/**
 * 请假申请
 */
@Data
public class BpmBusLeave {

    //<<<----------------------BaseMainEntity字段开始-----------------------
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 名称|主题
     */
    private String name;

    /**
     * 可用标记
     */
    private Boolean avtag;

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
    //>>>----------------------BaseMainEntity字段结束-----------------------

    /**
     * 请假类型
     */
    private String type;

    /**
     * 开始时间
     */
    private Date sttim;

    /**
     * 结束时间
     */
    private Date entim;

    /**
     * 请假天数
     */
    private Integer days;

    /**
     * 请假原因
     */
    private String reason;

    /**
     * 状态
     */
    private String state;

    /**
     * 流程对象
     */
    private BpmBusMain flowBo;

}
