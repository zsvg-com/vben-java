package vben.base.mon.job.main;

import lombok.Data;

import java.util.Date;

/**
 * 定时任务
 */
@Data
public class MonJobMain
{
    /**
     *主键ID
     */
    private Long id;

    /**
     *任务名称
     */
    private String name;

    /**
     *任务代码
     */
    private String code;

    /**
     * 任务方法
     */
    private String jid;

    /**
     * 任务类
     */
    private String jgroup;

    /**
     *请求类型
     */
    private Integer retyp;

    /**
     *请求URL
     */
    private String reurl;

    /**
     *可用标记
     */
    private Boolean avtag;

    /**
     *排序号
     */
    private Integer ornum;

    /**
     *创建时间
     */
    private Date crtim = new Date();

    /**
     *执行表达式
     */
    private String cron;

    /**
     *备注
     */
    private String notes;

}
