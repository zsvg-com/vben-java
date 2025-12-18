package vben.bpm.proc.task;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Data
public class BpmProcTask implements Serializable {

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
     * 消息类型
     */
    private String notty;

    /**
     * 状态
     */
    private String state;

    /**
     * 排序号
     */
    private Integer ornum;

    /**
     * 是否激活
     */
    private Boolean actag;

    /**
     * 实处理人
     */
    private String hauid;

    /**
     * 授权处理人
     */
    private String auuid;

    /**
     * 应处理人
     */
    private String exuid;
}
