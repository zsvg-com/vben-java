package vben.bpm.proc.task;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Data
public class BpmProcTaskHi implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 任务类型
     */
    private String type;

    /**
     * 流程实例id
     */
    private Long proid;

    /**
     * 节点id
     */
    private Long nodid;

    /**
     * 开始时间
     */
    private Date sttim = new Date();

    /**
     * 结束时间
     */
    private Date entim;

    /**
     * 消息类型
     */
    private String notty;

    /**
     * 状态
     */
    private String state;

    /**
     * 实处理人ID
     */
    private String hauid;

    /**
     * 授权处理人ID
     */
    private String auuid;

    /**
     * 应处理人ID
     */
    private String exuid;
}
