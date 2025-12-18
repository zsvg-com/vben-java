package vben.base.mon.job.log;

import lombok.Data;

/**
 * 定时任务日志
 */
@Data
public class MonJobLog {

    /**
     *主键ID
     */
    private String id;

    /**
     *任务名称
     */
    private String name;

    /**
     *开始时间
     */
    private String sttim;

    /**
     *结束时间
     */
    private String entim;

    /**
     *运行结果
     */
    private String ret;

    /**
     *日志信息
     */
    private String msg;
}
